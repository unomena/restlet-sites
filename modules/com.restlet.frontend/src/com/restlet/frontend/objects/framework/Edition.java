/*
 * Copyright 2005-2013 Restlet. All rights reserved.
 */

package com.restlet.frontend.objects.framework;

import java.io.Serializable;

/**
 * Represents an edition of Restlet Framework. This could be a platform or a
 * technical environment (Java SE, GAE, GWT, etc).
 * 
 * @author Thierry Boileau
 * 
 */
public class Edition implements Serializable {

    /** */
    private static final long serialVersionUID = 1L;

    private String id;

    private String longname;

    private String middlename;

    private String shortname;

    public String getId() {
        return id;
    }

    public String getLongname() {
        return longname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public String getShortname() {
        return shortname;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLongname(String longname) {
        this.longname = longname;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

}
