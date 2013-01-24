/*
 * Copyright 2005-2013 Restlet. All rights reserved.
 */

package com.restlet.frontend.objects.framework;

import java.io.Serializable;

/**
 * Qualifies a version : stable, testing unstable, according to Debian
 * semantics.
 * 
 * @see http://www.debian.org/releases/
 * @author Thierry Boileau
 * 
 */
public class Qualifier implements Serializable {

    /** */
    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    private String version;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}
