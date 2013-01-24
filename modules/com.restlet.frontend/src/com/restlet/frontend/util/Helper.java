/*
 * Copyright 2005-2013 Restlet. All rights reserved.
 */

package com.restlet.frontend.util;

import java.text.DateFormat;

import org.restlet.data.MediaType;
import org.restlet.ext.atom.Entry;
import org.restlet.ext.atom.Link;

import com.restlet.frontend.objects.BlogEntry;

public class Helper {

    public static BlogEntry toBlogEntry(Entry entry, DateFormat format) {
        BlogEntry be = null;
        for (int j = 0; be == null && j < entry.getLinks().size(); j++) {
            Link link = entry.getLinks().get(j);
            if (MediaType.TEXT_HTML.equals(link.getType())) {
                try {
                    be = new BlogEntry();
                    be.setTitle(entry.getTitle().getContent());
                    be.setPublished(format.format(entry.getPublished()));
                    be.setUri(link.getHref().toString());

                } catch (Throwable e) {
                    // nothing
                }
            }
        }
        return be;
    }

}
