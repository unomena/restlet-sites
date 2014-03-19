/*
 * Copyright 2005-2013 Restlet. All rights reserved.
 */

package com.restlet.frontend.objects.framework;

import java.io.Serializable;

/**
 * Represents a version.
 * @author Thierry Boileau
 *
 */
public class Version implements Serializable {

    /** */
    private static final long serialVersionUID = 1L;

    private EditionsList editions;

    private String fullVersion;

    private String fullVersionCompact;

    private String id;

    private String majorVersion;
    
    private String mavenVersion;

    private String minorVersion;

    private String published;

    private String qualifier;

    public EditionsList getEditions() {
        if (editions == null) {
            editions = new EditionsList();
        }
        return editions;
    }

    public String getFullVersion() {
        return fullVersion;
    }

    public String getFullVersionCompact() {
        return fullVersionCompact;
    }

    public String getId() {
        return id;
    }

    public String getMajorVersion() {
        return majorVersion;
    }

    public String getMavenVersion() {
        return mavenVersion;
    }

    public String getMinorVersion() {
        return minorVersion;
    }

    public String getPublished() {
        return published;
    }

    public String getQualifier() {
        return qualifier;
    }

    public void setEditions(EditionsList editions) {
        this.editions = editions;
    }

    public void setFullVersion(String fullVersion) {
        this.fullVersion = fullVersion;
    }

    public void setFullVersionCompact(String fullVersionCompact) {
        this.fullVersionCompact = fullVersionCompact;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMajorVersion(String majorVersion) {
        this.majorVersion = majorVersion;
    }

    public void setMavenVersion(String mavenVersion) {
        this.mavenVersion = mavenVersion;
    }

    public void setMinorVersion(String minorVersion) {
        this.minorVersion = minorVersion;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

}
