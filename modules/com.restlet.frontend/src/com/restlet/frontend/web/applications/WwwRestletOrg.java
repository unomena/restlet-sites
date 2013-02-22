/*
 * Copyright 2005-2013 Restlet. All rights reserved.
 */

package com.restlet.frontend.web.applications;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.CookieSetting;
import org.restlet.data.LocalReference;
import org.restlet.data.MediaType;
import org.restlet.data.Reference;
import org.restlet.engine.application.Encoder;
import org.restlet.ext.atom.Entry;
import org.restlet.ext.atom.Feed;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.Directory;
import org.restlet.routing.Filter;
import org.restlet.routing.Redirector;
import org.restlet.routing.Router;
import org.restlet.routing.Template;
import org.restlet.routing.TemplateRoute;
import org.restlet.security.ChallengeAuthenticator;
import org.restlet.security.MapVerifier;

import com.restlet.frontend.objects.framework.DistributionsList;
import com.restlet.frontend.objects.framework.Edition;
import com.restlet.frontend.objects.framework.EditionsList;
import com.restlet.frontend.objects.framework.Qualifier;
import com.restlet.frontend.objects.framework.QualifiersList;
import com.restlet.frontend.objects.framework.Version;
import com.restlet.frontend.objects.framework.VersionsList;
import com.restlet.frontend.web.resources.framework.DistributionsResource;
import com.restlet.frontend.web.resources.framework.EditionsResource;
import com.restlet.frontend.web.resources.framework.FeedGeneralResource;
import com.restlet.frontend.web.resources.framework.FeedReleasesResource;
import com.restlet.frontend.web.resources.framework.FeedSummaryResource;
import com.restlet.frontend.web.resources.framework.QualifiersResource;
import com.restlet.frontend.web.resources.framework.VersionsResource;
import com.restlet.frontend.web.resources.framework.impl.RestletOrgRefreshResource;
import com.restlet.frontend.web.services.CacheFilter;
import com.restlet.frontend.web.services.RefreshStatusService;

import freemarker.template.Configuration;

/**
 * Application for the http://www.restlet.org site.
 * 
 * @author Jerome Louvel
 */
public class WwwRestletOrg extends BaseApplication implements
        RefreshApplication {

    /**
     * {@link TemplateRoute} that scores URIs according to a regex pattern. Once
     * chosen, the URI is transmitted to the next Restlet unchanged, whereas a
     * classic {@link TemplateRoute} adjusts the base reference according to the
     * matched par of the URI.
     * 
     * @author Thierry Boileau
     * 
     */
    private static class StartsWithRoute extends TemplateRoute {
        /** The pattern to use for formatting or parsing. */
        private volatile String pattern;

        /** The internal Regex pattern. */
        private volatile Pattern regexPattern;

        /**
         * Constructor.
         * 
         * @param router
         *            the router.
         * @param next
         *            the Restlet to transmit the Request to.
         * @param pattern
         *            The regex pattern to match.
         */
        public StartsWithRoute(Router router, Restlet next, String pattern) {
            super(next);
            setRouter(router);
            this.pattern = pattern;
            this.regexPattern = Pattern.compile(this.pattern.toString());
        }

        @Override
        public float score(Request request, Response response) {
            float result = -1f;
            String remainingPart = request.getResourceRef().getRemainingPart(
                    false, isMatchingQuery());
            if (remainingPart != null) {
                Matcher matcher = regexPattern.matcher(remainingPart);
                if (matcher.lookingAt()) {
                    result = matcher.end();
                }
            }
            return result;
        }
    }

    /** The list of defined branches. */
    private List<String> branches;

    /** The data file URI. */
    private String dataUri;

    /** List of current distributions. */
    private DistributionsList distributions;

    /** The download router. */
    private Router downloadRouter;

    /** List of current editions. */
    private EditionsList editions;

    /** List of current Restlet feeds. */
    private List<Entry> feedGeneral;

    /** URI of the general feed. */
    private final String feedGeneralAtomUri;

    /** List of current Restlet feeds. */
    private List<Entry> feedReleases;

    /** URI of the releases feed. */
    private final String feedReleasesAtomUri;

    /** List of current Restlet feeds. */
    private List<Entry> feedSummary;

    /** Freemarker configuration object */
    private Configuration fmc;

    /** Login for protected pages. */
    private String login;

    /** Password for protected pages. */
    private String password;

    /** List of current editions. */
    private QualifiersList qualifiers;

    private Map<String, String> toBranch;

    /** List of current versions. */
    private VersionsList versions;

    /** The Web files root directory URI. */
    private String wwwUri;

    /**
     * Constructor.
     * 
     * @param propertiesFileReference
     *            The Reference to the application's properties file.
     * @throws IOException
     */
    public WwwRestletOrg(String propertiesFileReference) throws IOException {
        super(propertiesFileReference);

        this.setStatusService(new RefreshStatusService(true, this));

        this.dataUri = getProperties().getProperty("data.uri");
        this.wwwUri = getProperties().getProperty("www.uri");
        this.login = getProperties().getProperty("admin.login");
        this.password = getProperties().getProperty("admin.password");

        this.feedGeneralAtomUri = getProperties().getProperty(
                "feed.restlet.general.atom");
        this.feedReleasesAtomUri = getProperties().getProperty(
                "feed.restlet.releases.atom");

        // Turn off extension tunnelling because of redirections.
        this.getTunnelService().setExtensionsTunnel(false);

        // Override the default mediatype for XSD
        getMetadataService().addExtension("xsd", MediaType.APPLICATION_XML,
                true);

        this.fmc = new Configuration();
        try {
            this.fmc.setDirectoryForTemplateLoading(new File(
                    new LocalReference(this.wwwUri).getFile(), "tmpl"));
        } catch (IOException e) {
            getLogger()
                    .warning(
                            "Cannot set Freemarker templates directory: "
                                    + this.wwwUri);
        }

        qualifiers = new QualifiersList();
        versions = new VersionsList();
        distributions = new DistributionsList();
        editions = new EditionsList();
        branches = new ArrayList<String>();
        toBranch = new ConcurrentHashMap<String, String>();
    }

    @Override
    public Restlet createInboundRoot() {
        // Create a root router
        Router result = new Router(getContext());

        // Set up redirections.
        setRedirections(result);

        // Serve documentation
        Directory directory = new Directory(getContext(), this.wwwUri);
        directory.setNegotiatingContent(true);
        directory.setDeeplyAccessible(true);
        result.attachDefault(new CacheFilter(getContext(), directory));

        // Serve javadocs using a specific route
        Directory javadocsDir = new Directory(getContext(), this.dataUri
                + "/javadocs") {
            @Override
            public void handle(Request request, Response response) {
                // Translate the base reference.
                String branch = (String) request.getAttributes().get("branch");
                String edition = (String) request.getAttributes()
                        .get("edition");
                String group = (String) request.getAttributes().get("group");
                String relPart = "/" + branch + "/" + edition + "/" + group
                        + "/";
                Reference baseRef = request.getResourceRef().getBaseRef();
                String strBaseRef = baseRef.getIdentifier();
                baseRef.setIdentifier(strBaseRef.substring(0,
                        strBaseRef.length() - relPart.length()));
                setCookie(response, "branch", branch);
                super.handle(request, response);
            }
        };
        javadocsDir.setNegotiatingContent(true);
        javadocsDir.setDeeplyAccessible(true);
        result.attach("/learn/javadocs/{branch}/{edition}/{group}/",
                javadocsDir);

        // "download" routing
        downloadRouter = new Router(getContext());
        setDownloadRouter();
        result.attach("/download", downloadRouter);
        result.attach("/feeds/summary", FeedSummaryResource.class);
        result.attach("/feeds/general", FeedGeneralResource.class);
        result.attach("/feeds/releases", FeedReleasesResource.class);

        // Guarding access to sensitive services
        ChallengeAuthenticator guard = new ChallengeAuthenticator(getContext(),
                ChallengeScheme.HTTP_BASIC, "Admin section");
        MapVerifier verifier = new MapVerifier();
        verifier.getLocalSecrets().put(this.login, this.password.toCharArray());
        guard.setVerifier(verifier);
        Router adminRouter = new Router(getContext());
        adminRouter.attach("/refresh", RestletOrgRefreshResource.class);
        guard.setNext(adminRouter);
        result.attach("/admin", guard);

        Encoder encoder = new Encoder(getContext(), false, true,
                getEncoderService());
        encoder.setNext(result);
        return encoder;
    }

    public String getDataUri() {
        return this.dataUri;
    }

    public List<Entry> getFeedGeneral() {
        return feedGeneral;
    }

    public List<Entry> getFeedReleases() {
        return feedReleases;
    }

    public List<Entry> getFeedSummary() {
        return feedSummary;
    }

    public Configuration getFmc() {
        return this.fmc;
    }

    @Override
    public String getName() {
        return "Application for www.restlet.org";
    }

    public String getWwwUri() {
        return this.wwwUri;
    }

    /**
     * Helps to define redirections assuming that the router defines route by
     * using the {@link Template.MODE_STARTS_WITH} mode.
     * 
     * @param router
     *            The router where to define the redirection.
     * @param from
     *            The source template.
     * @param to
     *            The target template.
     * @return The defined route.
     */
    private TemplateRoute redirect(Router router, String from, String to) {
        TemplateRoute route = router.redirectPermanent(from, to);
        if (to.contains("{rr}")) {
            route.setMatchingMode(Template.MODE_STARTS_WITH);
        }
        return route;
    }

    /**
     * Helps to define redirections assuming that the router defines route by
     * using the {@link Template.MODE_STARTS_WITH} mode.
     * 
     * @param router
     *            The router where to define the redirection.
     * @param from
     *            The source template.
     * @param to
     *            The target template.
     * @return The defined route.
     */
    private TemplateRoute redirectBranch(Router router, String from, String to,
            final String qualifier) {
        // temporary redirection
        TemplateRoute route = router.attach(from, new Redirector(getContext(),
                to, Redirector.MODE_CLIENT_TEMPORARY) {
            @Override
            protected Reference getTargetRef(Request request, Response response) {
                String b = (qualifier != null) ? toBranch.get(qualifier) : null;
                if (b == null) {
                    // induce it from the request's cookies
                    b = request.getCookies().getFirstValue("branch", "");
                }
                if (b == null || "".equals(b) || !branches.contains(b)) {
                    b = toBranch.get("stable");
                }
                request.getAttributes().put("branch", b);
                return super.getTargetRef(request, response);
            }
        });
        if (to.contains("{rr}")) {
            route.setMatchingMode(Template.MODE_STARTS_WITH);
        }
        wrapCookie(route, "qualifierToBranch", qualifier);

        return route;
    }

    /**
     * Refreshes the list of distributions, versions, etc.
     */
    public void refresh() {
        try {
            // Read the available editions, versions, distributions
            ClientResource cr;

            cr = new ClientResource(this.wwwUri + "/data/editions.json");
            cr.accept(MediaType.APPLICATION_JSON);
            synchronized (editions) {
                editions.clear();
                editions.addAll(cr.wrap(EditionsResource.class).list());
            }

            cr = new ClientResource(this.wwwUri + "/data/versions.json");
            cr.accept(MediaType.APPLICATION_JSON);
            synchronized (versions) {
                versions.clear();
                versions.addAll(cr.wrap(VersionsResource.class).list());
            }

            cr = new ClientResource(this.wwwUri + "/data/distributions.json");
            cr.accept(MediaType.APPLICATION_JSON);
            synchronized (distributions) {
                distributions.clear();
                distributions.addAll(cr.wrap(DistributionsResource.class)
                        .list());
            }

            cr = new ClientResource(this.wwwUri + "/data/qualifiers.json");
            cr.accept(MediaType.APPLICATION_JSON);
            synchronized (qualifiers) {
                qualifiers.clear();
                qualifiers.addAll(cr.wrap(QualifiersResource.class).list());
            }
            for (Version version : versions) {
                for (Edition ve : version.getEditions()) {
                    for (Edition e : editions) {
                        if (e.getId().equals(ve.getId())) {
                            ve.setLongname(e.getLongname());
                            ve.setShortname(e.getShortname());
                            break;
                        }
                    }
                }
            }
            synchronized (branches) {
                branches.clear();
                for (Version version : versions) {
                    branches.add(version.getMinorVersion());
                }
                for (Qualifier qualifier : qualifiers) {
                    String branch = qualifier.getVersion().substring(0, 3);
                    toBranch.put(qualifier.getId(), branch);
                }
            }
            setDownloadRouter();

            // Get the feed
            cr = new ClientResource(this.feedGeneralAtomUri);
            Representation rep = cr.get(MediaType.APPLICATION_ATOM);
            Feed noeliosFeed = null;
            if (rep != null && rep.isAvailable()) {
                try {
                    noeliosFeed = new Feed(rep);
                } catch (IOException e) {
                    getLogger().warning(
                            "Cannot parse the general feed." + e.getMessage());
                }
            }

            cr = new ClientResource(this.feedReleasesAtomUri);
            rep = cr.get(MediaType.APPLICATION_ATOM);
            Feed restletReleasesFeed = null;
            if (rep != null && rep.isAvailable()) {
                try {
                    restletReleasesFeed = new Feed(rep);
                } catch (IOException e) {
                    getLogger().warning(
                            "Cannot parse the releases feed." + e.getMessage());
                }
            }

            // Aggregate the two feeds : avoid doublons, and take only one entry
            // from release feed.
            if (noeliosFeed != null && restletReleasesFeed != null) {
                ArrayList<Entry> digestEntries = new ArrayList<Entry>();
                boolean rrEmpty = restletReleasesFeed.getEntries().isEmpty();
                String rrFirstId = rrEmpty ? null : restletReleasesFeed
                        .getEntries().get(0).getId();
                for (Entry nEntry : noeliosFeed.getEntries()) {
                    boolean found = false;
                    if (!rrEmpty && !nEntry.getId().equals(rrFirstId)) {
                        for (Entry rrEntry : restletReleasesFeed.getEntries()) {
                            if (nEntry.getId().equals(rrEntry.getId())) {
                                found = true;
                                break;
                            }
                        }
                    }
                    if (!found) {
                        digestEntries.add(nEntry);
                    }
                }
                setFeedSummary(digestEntries);
                setFeedGeneral(noeliosFeed.getEntries());
                setFeedReleases(restletReleasesFeed.getEntries());
            }
        } catch (Exception e) {
            e.printStackTrace();
            getLogger().warning("Cannot load distributions and load feeds.");
        }
    }

    /**
     * Shortcut method that adds a {@link CookieSetting} to the response.
     * 
     * @param response
     *            The response to complete.
     * @param name
     *            The name of the coookie.
     * @param value
     *            The value of the cookie.
     */
    private void setCookie(Response response, String name, String value) {
        response.getCookieSettings().add(
                new CookieSetting(0, name, value, "/", null));
    }

    /**
     * Refreshes the download router according to the list of current versions,
     * branches, etc.
     */
    private void setDownloadRouter() {
        downloadRouter.getRoutes().clear();
        // redirect stable, testing, unstable uris to the "current" page.
        for (Qualifier qualifier : qualifiers) {
            wrapCookie(
                    redirect(downloadRouter, "/" + qualifier.getId(),
                            "/download/current"), "qualifier",
                    qualifier.getId());
        }
        // Serve Web pages
        downloadRouter.getRoutes().add(
                new StartsWithRoute(downloadRouter, new Directory(getContext(),
                        this.wwwUri + "/download"), "\\/[a-zA-Z]+"));
        // Redirect "branches" uris (ie "/download/2.x"), to the "past" url.
        for (String branch : branches) {
            wrapCookie(
                    redirect(downloadRouter, "/" + branch, "/download/past"),
                    "branch", branch);
        }
        // Serve archives
        downloadRouter.attachDefault(new Directory(getContext(), this.dataUri
                + "/archive/restlet"));
    }

    public void setFeedGeneral(List<Entry> feedGeneral) {
        this.feedGeneral = feedGeneral;
    }

    public void setFeedReleases(List<Entry> feedReleases) {
        this.feedReleases = feedReleases;
    }

    public void setFeedSummary(List<Entry> feedRestlet) {
        this.feedSummary = feedRestlet;
    }

    /**
     * Sets up the redirections.
     * 
     * @param router
     *            The router to complete.
     */
    private void setRedirections(Router router) {
        redirect(router, "/downloads/snapshot", "/download/unstable");
        // Redirections.
        redirect(router, "/download", "/download/");
        redirect(router, "/download/", "/download/current");

        // Maintain some old links
        redirect(router, "/a", "/");
        redirect(router, "/docs", "/learn");
        redirect(router, "/docs/core", "/learn");
        redirect(router, "/downloads/restlet-0.18b.zip", "/download/");
        redirect(router, "/downloads/restlet{version}", "/download/");
        redirect(router, "/discuss", "/participate");
        redirect(router, "/community/lists", "/participate");
        redirect(router, "/faq", "/learn/faq");
        redirect(router, "/glossary",
                "http://wiki.restlet.org/docs_2.0/180-restlet.html");
        redirect(router, "/introduction", "/discover");
        redirect(router, "/roadmap", "/learn/roadmap");
        redirect(router, "/tutorial", "/learn");
        redirect(router, "/examples", "/learn/examples");
        redirect(router, "/documentation/1.1/connectors",
                "http://wiki.restlet.org/docs_1.1/37-restlet.html");
        redirect(router, "/documentation/2.0/connectors",
                "http://wiki.restlet.org/docs_2.0/37-restlet.html");
        redirect(router, "/documentation/1.2", "/learn/2.0{rr}");
        redirect(router, "/documentation/2.0/api", "/learn/2.0/jse/api{rr}");
        redirect(router, "/documentation/2.0/engine",
                "/learn/2.0/jse/engine{rr}");
        redirect(router, "/documentation/2.0/ext", "/learn/2.0/jse/ext{rr}");
        redirect(router, "/documentation/snapshot/api",
                "/learn/snapshot/jse/api{rr}");
        redirect(router, "/documentation/snapshot/engine",
                "/learn/snapshot/jse/engine{rr}");
        redirect(router, "/documentation/snapshot/ext",
                "/learn/snapshot/jse/ext{rr}");
        redirect(router, "/downloads/archives/", "/download");
        redirect(router, "/downloads/archives/{variable}",
                "/download/{variable}{rr}");

        redirect(router, "/about", "/discover{rr}");
        redirect(router, "/documentation", "/learn{rr}");
        redirect(router, "/downloads", "/download{rr}");
        redirect(router, "/contribute", "/participate{rr}");

        redirect(router, "/discover", "/discover/");
        redirect(router, "/discover/", "/discover/features");
        redirect(router, "/learn", "/learn/");
        redirect(router, "/learn/", "/learn/tutorial");
        redirect(router, "/participate", "/participate/");

        redirectBranch(router, "/learn/guide/stable", "/learn/guide/{branch}",
                "stable");
        redirectBranch(router, "/learn/guide/testing", "/learn/guide/{branch}",
                "testing");
        redirectBranch(router, "/learn/guide", "/learn/guide/{branch}", null);
        redirectBranch(router, "/learn/javadocs", "/learn/javadocs/{branch}",
                null);
    }

    @Override
    public synchronized void start() throws Exception {
        super.start();
        refresh();
    }

    /**
     * Wraps the given route in order to set cookie.
     * 
     * @param route
     *            the route to wrap.
     * @param cookie
     *            The name of the cookie.
     * @param value
     *            The value of the cookie.
     * @return
     */
    private TemplateRoute wrapCookie(TemplateRoute route, final String cookie,
            final String value) {
        if ("branch".equals(cookie)) {
            Filter filter = new Filter(getContext(), route.getNext()) {
                @Override
                protected void afterHandle(Request request, Response response) {
                    if (value != null) {
                        setCookie(response, cookie, value);
                    } else {
                        setCookie(response, cookie, (String) request
                                .getAttributes().get("branch"));
                    }
                }
            };
            route.setNext(filter);
        } else if ("qualifierToBranch".equals(cookie)) {
            Filter filter = new Filter(getContext(), route.getNext()) {
                @Override
                protected void afterHandle(Request request, Response response) {
                    String b = (value != null) ? toBranch.get(value) : null;
                    if (b == null) {
                        // induce it from the request's cookies
                        b = request.getCookies().getFirstValue("branch", "");
                    }
                    if (b == null || "".equals(b) || !branches.contains(b)) {
                        b = toBranch.get("stable");
                    }
                    setCookie(response, cookie, b);
                }
            };
            route.setNext(filter);
        } else {
            Filter filter = new Filter(getContext(), route.getNext()) {
                @Override
                protected void afterHandle(Request request, Response response) {
                    super.afterHandle(request, response);
                    setCookie(response, cookie, value);
                }
            };
            route.setNext(filter);
        }
        return route;
    }
}
