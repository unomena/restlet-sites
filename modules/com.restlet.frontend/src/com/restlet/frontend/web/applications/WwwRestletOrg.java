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
        result.attachDefault(new CacheFilter(getContext(), directory));

        // Redirections.
        stableDocumentationRoute = result.redirectTemporary(
                "/documentation/stable", "/documentation");
        testingDocumentationRoute = result.redirectTemporary(
                "/documentation/testing", "/documentation");
        result.redirectTemporary("/documentation/unstable",
                "/documentation/snapshot");

        TemplateRoute route = result.redirectPermanent(
                "/downloads/snapshot{rr}",
                "/downloads/archives/2.0/restlet-jse-2.0snapshot{rr}");
        route.getTemplate().getVariables()
                .put("rr", new Variable(Variable.TYPE_ALL));

        result.attach("/downloads/stable", new Redirector(getContext(),
                "/downloads") {
            @Override
            public void handle(Request request, Response response) {
                super.handle(request, response);
                response.getCookieSettings().add(
                        new CookieSetting(-1, "qualifier", "stable", "/",
                                request.getResourceRef().getHostDomain()));
            }
        });
        result.attach("/downloads/testing", new Redirector(getContext(),
                "/downloads") {
            @Override
            public void handle(Request request, Response response) {
                super.handle(request, response);
                response.getCookieSettings().add(
                        new CookieSetting(-1, "qualifier", "testing", "/",
                                request.getResourceRef().getHostDomain()));
            }
        });
        result.attach("/downloads/unstable", new Redirector(getContext(),
                "/downloads") {
            @Override
            public void handle(Request request, Response response) {
                super.handle(request, response);
                response.getCookieSettings().add(
                        new CookieSetting(-1, "qualifier", "unstable", "/",
                                request.getResourceRef().getHostDomain()));
            }
        });

        // Serve files in the the "downloads" directory ("index" and "archives")
        directory = new Directory(getContext(), this.wwwUri + "/downloads") {
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
        route = result.attach("/downloads/{variable}", directory);
        route.getTemplate().getVariables()
                .put("variable", new Variable(Variable.TYPE_ALPHA));

        result.redirectPermanent("/downloads/archives/", "/downloads/archives");
        result.redirectPermanent("/downloads/archives/{variable}",
                "/downloads/{variable}{rr}");

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
        result.attach("/downloads/{variable}", directory);

        // Serve other files from the root path
        // directory = new Directory(getContext(), this.wwwUri);
        // router.attach("", directory);

        // Maintain some old links
        route = result.redirectPermanent("/a", "/");
        route.getTemplate().setMatchingMode(Template.MODE_EQUALS);
        result.redirectPermanent("/docs", "/documentation/1.0{rr}");
        result.redirectPermanent("/docs/core", "/documentation/1.0/nre{rr}");
        result.redirectPermanent("/downloads/restlet-0.18b.zip",
                "/downloads/1.0/");
        result.redirectPermanent("/downloads/restlet{version}",
                "/downloads/1.0/");
        result.redirectPermanent("/discuss", "/community/discuss");
        result.redirectPermanent("/community/lists", "/community/discuss");
        result.redirectPermanent("/faq", "/documentation/1.0/faq");

        result.redirectPermanent("/glossary",
                "http://wiki.restlet.org/docs_2.0/180-restlet.html");
        result.redirectPermanent("/introduction", "/about/introduction");
        result.redirectPermanent("/roadmap", "/about/roadmap");
        result.redirectPermanent("/tutorial", "/documentation/2.0/tutorial");
        result.redirectPermanent("/examples", "/documentation/2.0/examples");
        result.redirectPermanent("/documentation/1.1/connectors",
                "http://wiki.restlet.org/docs_1.1/37-restlet.html");
        result.redirectPermanent("/documentation/2.0/connectors",
                "http://wiki.restlet.org/docs_2.0/37-restlet.html");
        result.redirectPermanent("/documentation/1.2", "/documentation/2.0{rr}");

        result.redirectPermanent("/documentation/2.0/api",
                "/documentation/2.0/jse/api{rr}");
        result.redirectPermanent("/documentation/2.0/engine",
                "/documentation/2.0/jse/engine{rr}");
        result.redirectPermanent("/documentation/2.0/ext",
                "/documentation/2.0/jse/ext{rr}");

        result.redirectPermanent("/documentation/snapshot/api",
                "/documentation/snapshot/jse/api{rr}");
        result.redirectPermanent("/documentation/snapshot/engine",
                "/documentation/snapshot/jse/engine{rr}");
        result.redirectPermanent("/documentation/snapshot/ext",
                "/documentation/snapshot/jse/ext{rr}");

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

        return result;
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
                                getContext(), "/documentation/"
                                        + qualifier.getVersion()
                                                .substring(0, 3),
                                Redirector.MODE_CLIENT_TEMPORARY));
                    } else if ("testing".equals(qualifier.getId())) {
                        testingDocumentationRoute.setNext(new Redirector(
                                getContext(), "/documentation/"
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
