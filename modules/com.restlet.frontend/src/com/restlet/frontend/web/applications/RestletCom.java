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

import com.restlet.frontend.web.resources.company.FeedGeneralResource;
import com.restlet.frontend.web.resources.company.FeedReleasesResource;
import com.restlet.frontend.web.resources.company.FeedSummaryResource;
import com.restlet.frontend.web.services.CacheFilter;
import com.restlet.frontend.web.services.RefreshStatusService;

import freemarker.template.Configuration;

/**
 * Application for the http://restlet.com site.
 * 
 * @author Jerome Louvel
 */
public class RestletCom extends BaseApplication implements RefreshApplication {

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
    public RestletCom(String propertiesFileReference) throws IOException {
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
        return "Application for restlet.com";
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
        getContext().getAttributes().put("feed-restlet-general",
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

}
