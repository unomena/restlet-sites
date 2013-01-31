/*
 * Copyright 2005-2013 Restlet. All rights reserved.
 */

package com.restlet.frontend.web.applications;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.CookieSetting;
import org.restlet.data.LocalReference;
import org.restlet.data.MediaType;
import org.restlet.engine.application.Encoder;
import org.restlet.ext.atom.Entry;
import org.restlet.ext.atom.Feed;
import org.restlet.representation.Representation;
import org.restlet.representation.Variant;
import org.restlet.resource.ClientResource;
import org.restlet.resource.Directory;
import org.restlet.routing.Redirector;
import org.restlet.routing.Router;
import org.restlet.routing.Template;
import org.restlet.routing.TemplateRoute;
import org.restlet.routing.Variable;
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

    /** The data file URI. */
    private String dataUri;

    /** List of current distributions. */
    private DistributionsList distributions;

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

    /** Version stable. */
    private TemplateRoute stableDocumentationRoute;

    /** Version stable. */
    private TemplateRoute testingDocumentationRoute;

    /** List of current versions. */
    private VersionsList versions;

    /** The Web file URI. */
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

        List<Variant> errorVariants = new ArrayList<Variant>();
        errorVariants.add(new Variant(MediaType.TEXT_HTML));
        this.setStatusService(new RefreshStatusService(true, this,
                errorVariants));

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
    }

    @Override
    public Restlet createInboundRoot() {
        // Create a root router
        Router result = new Router(getContext());

        // Serve documentation without content negotiation
        Directory directory = new Directory(getContext(), this.wwwUri);
        directory.setNegotiatingContent(false);
        directory.setDeeplyAccessible(true);
        if (Boolean.parseBoolean(getProperties().getProperty("nocache"))) {
            result.attachDefault(directory);
        } else {
            result.attachDefault(new CacheFilter(getContext(), directory));
        }

        // Redirections.
        stableDocumentationRoute = result.redirectTemporary("/learn/stable",
                "/learn");
        testingDocumentationRoute = result.redirectTemporary("/learn/testing",
                "/learn");
        result.redirectTemporary("/learn/unstable", "/learn/snapshot");

        redirect(result, "/downloads/snapshot", "/download");
        result.attach("/download/stable", new Redirector(getContext(),
                "/download") {
            @Override
            public void handle(Request request, Response response) {
                super.handle(request, response);
                response.getCookieSettings().add(
                        new CookieSetting(-1, "qualifier", "stable", "/",
                                request.getResourceRef().getHostDomain()));
            }
        });
        result.attach("/download/testing", new Redirector(getContext(),
                "/download") {
            @Override
            public void handle(Request request, Response response) {
                super.handle(request, response);
                response.getCookieSettings().add(
                        new CookieSetting(-1, "qualifier", "testing", "/",
                                request.getResourceRef().getHostDomain()));
            }
        });
        result.attach("/download/unstable", new Redirector(getContext(),
                "/download") {
            @Override
            public void handle(Request request, Response response) {
                super.handle(request, response);
                response.getCookieSettings().add(
                        new CookieSetting(-1, "qualifier", "unstable", "/",
                                request.getResourceRef().getHostDomain()));
            }
        });
        // Serve files in the the "downloads" directory ("index" and "archives")
        directory = new Directory(getContext(), this.wwwUri + "/download") {
            @Override
            public void handle(Request request, Response response) {
                String variable = (String) request.getAttributes().get(
                        "variable");
                if (variable != null) {
                    String baseRef = request.getResourceRef().getBaseRef()
                            .getIdentifier();
                    request.getResourceRef()
                            .getBaseRef()
                            .setIdentifier(
                                    baseRef.substring(0, baseRef.length()
                                            - variable.length() - 1));
                }
                super.handle(request, response);
            }
        };
        TemplateRoute route = result.attach("/download/{variable}", directory);
        route.getTemplate().getVariables()
                .put("variable", new Variable(Variable.TYPE_ALPHA));

        // Serve download files from a specific directory
        directory = new Directory(getContext(), this.dataUri
                + "/archive/restlet") {
            @Override
            public void handle(Request request, Response response) {
                String variable = (String) request.getAttributes().get(
                        "variable");
                if (variable != null) {
                    String baseRef = request.getResourceRef().getBaseRef()
                            .getIdentifier();
                    request.getResourceRef()
                            .getBaseRef()
                            .setIdentifier(
                                    baseRef.substring(0, baseRef.length()
                                            - variable.length() - 1));
                }
                super.handle(request, response);
            }
        };
        directory.setListingAllowed(true);
        result.attach("/download/{variable}", directory);

        // Maintain some old links
        redirect(result, "/a", "/");
        redirect(result, "/docs", "/learn");
        redirect(result, "/docs/core", "/learn");
        redirect(result, "/downloads/restlet-0.18b.zip", "/download/");
        redirect(result, "/downloads/restlet{version}", "/download/");
        redirect(result, "/discuss", "/participate");
        redirect(result, "/community/lists", "/participate");
        redirect(result, "/faq", "/learn/faq");
        redirect(result, "/glossary",
                "http://wiki.restlet.org/docs_2.0/180-restlet.html");
        redirect(result, "/introduction", "/discover");
        redirect(result, "/roadmap", "/learn/roadmap");
        redirect(result, "/tutorial", "/learn");
        redirect(result, "/examples", "/learn/examples");
        redirect(result, "/documentation/1.1/connectors",
                "http://wiki.restlet.org/docs_1.1/37-restlet.html");
        redirect(result, "/documentation/2.0/connectors",
                "http://wiki.restlet.org/docs_2.0/37-restlet.html");
        redirect(result, "/documentation/1.2", "/learn/2.0{rr}");
        redirect(result, "/documentation/2.0/api", "/learn/2.0/jse/api{rr}");
        redirect(result, "/documentation/2.0/engine",
                "/learn/2.0/jse/engine{rr}");
        redirect(result, "/documentation/2.0/ext", "/learn/2.0/jse/ext{rr}");
        redirect(result, "/documentation/snapshot/api",
                "/learn/snapshot/jse/api{rr}");
        redirect(result, "/documentation/snapshot/engine",
                "/learn/snapshot/jse/engine{rr}");
        redirect(result, "/documentation/snapshot/ext",
                "/learn/snapshot/jse/ext{rr}");
        redirect(result, "/downloads/archives/", "/download");
        redirect(result, "/downloads/archives/{variable}",
                "/download/{variable}{rr}");

        redirect(result, "/about", "/discover{rr}");
        redirect(result, "/documentation", "/learn{rr}");
        redirect(result, "/downloads", "/download{rr}");
        redirect(result, "/contribute", "/participate{rr}");

        redirect(result, "/learn/guide", "/learn/stable/guide{rr}");
        redirect(result, "/learn/javadocs", "/learn/stable/javadocs{rr}");
        redirect(result, "/learn/roadmap", "/learn/stable/roadmap{rr}");
        
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

    public DistributionsList getDistributions() {
        return distributions;
    }

    public EditionsList getEditions() {
        return editions;
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

    public QualifiersList getQualifiers() {
        return qualifiers;
    }

    public VersionsList getVersions() {
        return versions;
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
     */
    private void redirect(Router router, String from, String to) {
        if (to.contains("{rr}")) {
            TemplateRoute route = router.redirectPermanent(from, to);
            route.setMatchingMode(Template.MODE_STARTS_WITH);
        } else {
            router.redirectPermanent(from, to);
        }
    }

    public void refresh() {
        try {
            // Read the available editions, versions, distributions
            ClientResource cr;

            cr = new ClientResource(this.wwwUri + "/data/editions.json");
            cr.accept(MediaType.APPLICATION_JSON);
            setEditions(cr.wrap(EditionsResource.class).list());

            cr = new ClientResource(this.wwwUri + "/data/versions.json");
            cr.accept(MediaType.APPLICATION_JSON);
            setVersions(cr.wrap(VersionsResource.class).list());

            cr = new ClientResource(this.wwwUri + "/data/distributions.json");
            cr.accept(MediaType.APPLICATION_JSON);
            setDistributions(cr.wrap(DistributionsResource.class).list());

            cr = new ClientResource(this.wwwUri + "/data/qualifiers.json");
            cr.accept(MediaType.APPLICATION_JSON);
            setQualifiers(cr.wrap(QualifiersResource.class).list());

            if (getQualifiers() != null) {
                for (Qualifier qualifier : getQualifiers()) {
                    if ("stable".equals(qualifier.getId())) {
                        stableDocumentationRoute.setNext(new Redirector(
                                getContext(), "/learn/"
                                        + qualifier.getVersion()
                                                .substring(0, 3),
                                Redirector.MODE_CLIENT_TEMPORARY));
                    } else if ("testing".equals(qualifier.getId())) {
                        testingDocumentationRoute.setNext(new Redirector(
                                getContext(), "/learn/"
                                        + qualifier.getVersion()
                                                .substring(0, 3),
                                Redirector.MODE_CLIENT_TEMPORARY));

                    }
                }
            }

            for (Version version : versions) {
                for (Edition ve : version.getEditions()) {
                    for (Edition e : getEditions()) {
                        if (e.getId().equals(ve.getId())) {
                            ve.setLongname(e.getLongname());
                            ve.setShortname(e.getShortname());
                            break;
                        }
                    }
                }
            }

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

    public void setDistributions(DistributionsList distributions) {
        this.distributions = distributions;
    }

    public void setEditions(EditionsList editions) {
        this.editions = editions;
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

    public void setQualifiers(QualifiersList qualifiers) {
        this.qualifiers = qualifiers;
    }

    public void setVersions(VersionsList versions) {
        this.versions = versions;
    }

    @Override
    public synchronized void start() throws Exception {
        super.start();
        refresh();
    }
}
