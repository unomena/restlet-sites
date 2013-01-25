/*
 * Copyright 2005-2013 Restlet. All rights reserved.
 */

package com.restlet.frontend.web.applications;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.restlet.Restlet;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.CharacterSet;
import org.restlet.data.Language;
import org.restlet.data.LocalReference;
import org.restlet.data.MediaType;
import org.restlet.ext.atom.Entry;
import org.restlet.ext.atom.Feed;
import org.restlet.representation.Representation;
import org.restlet.representation.Variant;
import org.restlet.resource.ClientResource;
import org.restlet.resource.Directory;
import org.restlet.routing.Router;
import org.restlet.routing.Template;
import org.restlet.routing.TemplateRoute;
import org.restlet.security.ChallengeAuthenticator;
import org.restlet.security.MapVerifier;

import com.restlet.frontend.web.resources.company.FeedGeneralResource;
import com.restlet.frontend.web.resources.company.FeedReleasesResource;
import com.restlet.frontend.web.resources.company.FeedSummaryResource;
import com.restlet.frontend.web.resources.company.RestletComRefreshResource;
import com.restlet.frontend.web.services.CacheFilter;
import com.restlet.frontend.web.services.RefreshStatusService;

import freemarker.template.Configuration;

/**
 * Application for the http://www.restlet.com site.
 * 
 * @author Jerome Louvel
 */
public class WwwRestletCom extends BaseApplication implements
        RefreshApplication {
    /** The data file URI. */
    private final String dataUri;

    /** List of current Restlet feeds. */
    private List<Entry> feedGeneral;

    /** URI of the general Noelios feed. */
    private final String feedGeneralAtomUri;

    /** List of current Restlet feeds. */
    private List<Entry> feedReleases;

    /** URI of the releases Restlet feed. */
    private final String feedReleasesAtomUri;

    /** List of current Restlet feeds. */
    private List<Entry> feedSummary;

    /** URI of the summary feed. */
    private final String feedSummaryUri;

    /** Freemarker configuration object. */
    private final Configuration fmc;

    /** Login for protected pages. */
    private final String login;

    /** Password for protected pages. */
    private final String password;

    /** The Web file URI. */
    private final String wwwUri;

    /**
     * Constructor.
     * 
     * @param propertiesFileReference
     *            The Reference to the application's properties file.
     * @throws IOException
     */
    public WwwRestletCom(String propertiesFileReference) throws IOException {
        super(propertiesFileReference);

        List<Variant> errorVariants = new ArrayList<Variant>();
        Variant errorVariant = new Variant(MediaType.TEXT_HTML);
        errorVariant.getLanguages().add(Language.ENGLISH);
        errorVariants.add(errorVariant);
        errorVariant = new Variant(MediaType.TEXT_HTML);
        errorVariant.getLanguages().add(Language.FRENCH);
        errorVariants.add(errorVariant);
        this.setStatusService(new RefreshStatusService(true, this,
                errorVariants));

        this.dataUri = getProperties().getProperty("data.uri");
        this.wwwUri = getProperties().getProperty("www.uri");
        this.login = getProperties().getProperty("admin.login");
        this.password = getProperties().getProperty("admin.password");

        this.feedSummaryUri = getProperties().getProperty(
                "feed.noelios.summary");
        this.feedGeneralAtomUri = getProperties().getProperty(
                "feed.noelios.general.atom");
        this.feedReleasesAtomUri = getProperties().getProperty(
                "feed.restlet.releases.atom");

        this.fmc = new Configuration();
        this.fmc.setDefaultEncoding(CharacterSet.UTF_8.getName());
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
        // Create the root router
        Router router = new Router(getContext());

        // Guarding access to sensitive services
        ChallengeAuthenticator guard = new ChallengeAuthenticator(getContext(),
                ChallengeScheme.HTTP_BASIC, "Admin section");
        MapVerifier verifier = new MapVerifier();
        verifier.getLocalSecrets().put(this.login, this.password.toCharArray());
        guard.setVerifier(verifier);

        Router adminRouter = new Router(getContext());
        Directory reportsDir = new Directory(getContext(), this.dataUri
                + "/log/reports");
        adminRouter.attach("/reports", reportsDir);
        adminRouter.attach("/refresh", RestletComRefreshResource.class);

        Directory adminDir = new Directory(getContext(), this.wwwUri + "/admin");
        adminDir.setNegotiatingContent(true);
        adminDir.setDeeplyAccessible(true);
        adminRouter.attach("/", adminDir);

        guard.setNext(adminRouter);
        router.attach("/admin", guard);

        router.attach("/feeds/summary", FeedSummaryResource.class);
        router.attach("/feeds/general", FeedGeneralResource.class);
        router.attach("/feeds/releases", FeedReleasesResource.class);

        TemplateRoute route = router.redirectPermanent("/products/restlet",
                "/products/restlet-framework");
        route.getTemplate().setMatchingMode(Template.MODE_EQUALS);

        // Serve other files from the Restlet.com directory
        Directory directory = new Directory(getContext(), this.wwwUri);
        directory.setNegotiatingContent(true);
        directory.setDeeplyAccessible(true);
        router.attachDefault(new CacheFilter(getContext(), directory));

        return router;
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
        return "Application for www.restlet.com";
    }

    public String getWwwUri() {
        return this.wwwUri;
    }

    public void setFeedGeneral(List<Entry> feedGeneral) {
        this.feedGeneral = feedGeneral;
    }

    public void setFeedReleases(List<Entry> feedReleases) {
        this.feedReleases = feedReleases;
    }

    public void setFeedSummary(List<Entry> feedSummary) {
        this.feedSummary = feedSummary;
    }

    @Override
    public synchronized void start() throws Exception {
        // Update the context
        getContext().getAttributes().put("feed", this.feedSummaryUri);
        getContext().getAttributes().put("feed-noelios-general",
                this.feedGeneralAtomUri);
        getContext().getAttributes().put("feed-restlet-releases",
                this.feedReleasesAtomUri);

        refresh();

        super.start();
    }

    public void refresh() {
        try {
            // Get the feed
            ClientResource cr = new ClientResource(this.feedGeneralAtomUri);
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
            getLogger().warning("Cannot load feeds.");
        }
    }

}
