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
import com.restlet.frontend.web.applications.RestletOrg;

/**
 * Resource that builds a view of the Noelios's blog feed aimed for the Restlet
 * Web site.
 */
public class FeedSummaryResource extends ServerResource {

    private DateFormat format = new SimpleDateFormat("dd MMM yyyy", Locale.US);

    private List<Entry> entries;

    @Override
    protected void doInit() throws ResourceException {
        RestletOrg app = (RestletOrg) getApplication();
        if (app.getFeedSummary() == null || app.getFeedSummary().isEmpty()) {
            app.refresh();
        } else {
            entries = app.getFeedSummary();
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

        int limit = 5;
        for (int i = 0; limit > 0 && i < entries.size(); i++) {
            BlogEntry be = Helper.toBlogEntry(entries.get(i), format);
            if (be != null) {
                limit--;
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
