/*
 * Copyright 2005-2013 Restlet. All rights reserved.
 */

package com.restlet.frontend.web.applications;

import freemarker.template.Configuration;

/**
 * Application that allows resources to refresh web pages with Freemarker.
 * 
 * @author Jerome Louvel
 */
public interface RefreshApplication {

    /**
     * Returns the Freemarker configuration object.
     * 
     * @return The Freemarker configuration object.
     */
    public Configuration getFmc();

    /**
     * Returns the URI of the Web pages directory.
     * 
     * @return The URI of the Web pages directory.
     */
    public String getWwwUri();

}
