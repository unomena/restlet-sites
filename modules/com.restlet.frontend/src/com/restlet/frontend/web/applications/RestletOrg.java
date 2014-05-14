/*
 * Copyright 2005-2013 Restlet. All rights reserved.
 */

package com.restlet.frontend.web.applications;

import java.io.IOException;

import org.restlet.Restlet;

/**
 * Application for the http://restlet.org site.
 * 
 * @author Jerome Louvel
 */
public class RestletOrg extends BaseApplication {

    public RestletOrg(String propertiesFileReference) throws IOException {
        super(propertiesFileReference);
    }

    @Override
    public Restlet createInboundRoot() {
        return null;
    }

    @Override
    public String getName() {
        return "Application for restlet.org";
    }

    public void refresh() {
    }

}
