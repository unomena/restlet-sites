/*
 * Copyright 2005-2013 Restlet. All rights reserved.
 */

package com.restlet.frontend.web.resources.company;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.restlet.data.MediaType;
import org.restlet.ext.atom.Entry;
import org.restlet.ext.atom.Feed;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

/**
 * Resource that builds a view of the Restlet's blog feed aimed for the restlet
 * Web site.
 */
public class RestletComFeedResource extends ServerResource {

    @Override
    protected void doInit() throws ResourceException {
        setConditional(false);
    }

    @Get("atom")
    public Representation toAtom() throws ResourceException {
        Representation result = null;

        ClientResource cr = new ClientResource((String) getContext()
                .getAttributes().get("feed-restlet-general"));
        Representation rep = cr.get(MediaType.APPLICATION_ATOM);
        Feed restletFeed = null;
        if (rep != null && rep.isAvailable()) {
            try {
                restletFeed = new Feed(rep);
            } catch (IOException e) {
                getLogger().warning(
                        "Cannot parse the Restlet feed." + e.getMessage());
            }
        }

        cr = new ClientResource((String) getContext().getAttributes().get(
                "feed-restlet-releases"));
        rep = cr.get(MediaType.APPLICATION_ATOM);
        Feed restletReleasesFeed = null;
        if (rep != null && rep.isAvailable()) {
            try {
                restletReleasesFeed = new Feed(rep);
            } catch (IOException e) {
                getLogger().warning(
                        "Cannot parse the restlet feed." + e.getMessage());
            }
        }

        // Aggregate the two feeds
        if (restletFeed != null && restletReleasesFeed != null) {
            boolean rrEmpty = restletReleasesFeed.getEntries().isEmpty();
            String rrFirstId = rrEmpty ? null : restletReleasesFeed
                    .getEntries().get(0).getId();
            List<Entry> list = new ArrayList<Entry>();
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
                    list.add(nEntry);
                }
            }
            restletFeed.getEntries().clear();
            restletFeed.getEntries().addAll(list);
            result = restletFeed;
        }

        return result;
    }
}
