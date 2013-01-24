/*
 * Copyright 2005-2013 Restlet. All rights reserved.
 */

package com.restlet.frontend.objects.framework;

import java.io.Serializable;

/**
 * Represents a distribution of Restlet Framework taht is to say the way a
 * specific edition of the framework is provided to the client (could be a ZIP
 * file, a Windows installer, Maven, P2, etc).
 * 
 * @author Thierry Boileau
 * 
 */
public class Distribution implements Serializable {

    /** */
    private static final long serialVersionUID = 1L;

    private String edition;

    private String fileName;

    private String fileSize;

    private String fileType;

    private String id;

    private String mavenGroupId;

    private String name;

    private String p2Url;

    private String type;

    private String version;

    public String getEdition() {
        return edition;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public String getId() {
        return id;
    }

    public String getMavenGroupId() {
        return mavenGroupId;
    }

    public String getName() {
        return name;
    }

    public String getP2Url() {
        return p2Url;
    }

    public String getType() {
        return type;
    }

    public String getVersion() {
        return version;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMavenGroupId(String mavenGroupId) {
        this.mavenGroupId = mavenGroupId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setP2Url(String p2Url) {
        this.p2Url = p2Url;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}
