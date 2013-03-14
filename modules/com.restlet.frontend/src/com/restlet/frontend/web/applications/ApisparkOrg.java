/*
 * Copyright 2005-2013 Restlet. All rights reserved.
 */

package com.restlet.frontend.web.applications;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.restlet.Restlet;
import org.restlet.data.CharacterSet;
import org.restlet.data.LocalReference;
import org.restlet.data.MediaType;
import org.restlet.engine.application.Encoder;
import org.restlet.ext.atom.Entry;
import org.restlet.ext.atom.Feed;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.Directory;
import org.restlet.routing.Router;
import org.restlet.routing.Template;
import org.restlet.routing.TemplateRoute;

import com.restlet.frontend.web.resources.apispark.FeedSummaryResource;
import com.restlet.frontend.web.services.CacheFilter;
import com.restlet.frontend.web.services.RefreshStatusService;

import freemarker.template.Configuration;

/**
 * Application for the http://restlet.com site.
 * 
 * @author Jerome Louvel
 */
public class ApisparkOrg extends BaseApplication implements RefreshApplication {

    /** List of current Restlet feeds. */
    private List<Entry> feedGeneral;

    /** URI of the general Restlet feed. */
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

    /** The Web file URI. */
    private final String wwwUri;

    /**
     * Constructor.
     * 
     * @param propertiesFileReference
     *            The Reference to the application's properties file.
     * @throws IOException
     */
    public ApisparkOrg(String propertiesFileReference) throws IOException {
        super(propertiesFileReference);

        this.setStatusService(new RefreshStatusService(true, this));

        this.wwwUri = getProperties().getProperty("www.uri");

        this.feedSummaryUri = getProperties().getProperty(
                "feed.restlet.summary");
        this.feedGeneralAtomUri = getProperties().getProperty(
                "feed.restlet.general.atom");
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

        redirect(router, "/tutorials/", "/tutorials");

        router.attach("/feeds/summary", FeedSummaryResource.class);

        // Serve other files from the apispark.org directory
        Directory directory = new Directory(getContext(), this.wwwUri);
        directory.setNegotiatingContent(true);
        directory.setDeeplyAccessible(true);
        router.attachDefault(new CacheFilter(getContext(), directory));

        Encoder encoder = new Encoder(getContext(), false, true,
                getEncoderService());
        encoder.setNext(router);
        return encoder;
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
        return "Application for apispark.org";
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

    public void refresh() {
        try {
            // Get the feed
            ClientResource cr = new ClientResource(this.feedGeneralAtomUri);
            Representation rep = cr.get(MediaType.APPLICATION_ATOM);
            Feed restletFeed = null;
            if (rep != null && rep.isAvailable()) {
                try {
                    restletFeed = new Feed(rep);
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
            if (restletFeed != null && restletReleasesFeed != null) {
                ArrayList<Entry> digestEntries = new ArrayList<Entry>();
                boolean rrEmpty = restletReleasesFeed.getEntries().isEmpty();
                String rrFirstId = rrEmpty ? null : restletReleasesFeed
                        .getEntries().get(0).getId();
                for (Entry nEntry : restletFeed.getEntries()) {
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
                setFeedGeneral(restletFeed.getEntries());
                setFeedReleases(restletReleasesFeed.getEntries());
            }
        } catch (Exception e) {
            e.printStackTrace();
            getLogger().warning("Cannot load feeds.");
        }
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
        getContext().getAttributes().put("feed-restlet-general",
                this.feedGeneralAtomUri);
        getContext().getAttributes().put("feed-restlet-releases",
                this.feedReleasesAtomUri);

        refresh();

        super.start();
    }

}
