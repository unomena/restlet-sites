/*
 * Copyright 2005-2013 Restlet. All rights reserved.
 */

package com.restlet.frontend.web.resources.framework;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.restlet.ext.atom.Entry;
import org.restlet.ext.atom.Feed;
import org.restlet.representation.Representation;
import org.restlet.representation.Variant;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.restlet.frontend.objects.BlogEntry;
import com.restlet.frontend.util.Helper;
import com.restlet.frontend.web.applications.RestletCom;

/**
 * Resource that builds a view of the restlet's blog feed aimed for the Restlet
 * Web site.
 */
public class FeedReleasesResource extends ServerResource {

    private DateFormat format = new SimpleDateFormat("dd MMM yyyy", Locale.US);

    private List<Entry> entries;

    @Override
    protected void doInit() throws ResourceException {
        RestletCom app = (RestletCom) getApplication();
        if (app.getFeedReleases() == null || app.getFeedReleases().isEmpty()) {
            app.refresh();
        } else {
            entries = app.getFeedReleases();
        }

        setExisting(entries != null);
    }

    @Get("atom")
    public Representation represent() throws ResourceException {
        Feed result = new Feed();
        result.getEntries().addAll(entries);
        return result;
    }

    @Get("json")
    public ArrayList<BlogEntry> toJson() {
        ArrayList<BlogEntry> result = new ArrayList<BlogEntry>();

        for (Entry entry : entries) {
            BlogEntry be = Helper.toBlogEntry(entry, format);
            if (be != null) {
                result.add(be);
            }
        }

        return result;
    }

    @Override
    public Representation toRepresentation(Object source, Variant target)
            throws IOException {
        Representation rep = super.toRepresentation(source, target);
        if (getStatus().isSuccess() && rep != null && rep.isAvailable()) {
            Calendar cal = new GregorianCalendar();
            cal.add(Calendar.HOUR, 12);
            rep.setExpirationDate(cal.getTime());
        }
        return rep;
    }
}
