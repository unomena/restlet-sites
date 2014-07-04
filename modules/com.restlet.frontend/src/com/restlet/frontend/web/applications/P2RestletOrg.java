/*
 * Copyright 2005-2013 Restlet. All rights reserved.
 */

package com.restlet.frontend.web.applications;

import java.io.IOException;

import org.restlet.Restlet;
import org.restlet.resource.Directory;
import org.restlet.routing.Router;

/**
 * Application for the http://p2.restlet.org repository.
 * 
 * @author Thierry Boileau
 */
public class P2RestletOrg extends BaseApplication {
    /** The data file URI. */
    private String dataUri;

    /**
     * Constructor.
     * 
     * @param propertiesFileReference
     *            The Reference to the application's properties file.
     * @throws IOException
     */
    public P2RestletOrg(String propertiesFileReference) throws IOException {
        super(propertiesFileReference);

        this.dataUri = getProperty("data.uri");
    }

    @Override
    public Restlet createInboundRoot() {
        // Create a root router
        Router result = new Router(getContext());

        // Serve repository
        Directory directory = new Directory(getContext(), this.dataUri + "/p2");
        directory.setIndexName(null);
        directory.setNegotiatingContent(true);
        directory.setModifiable(false);
        directory.setDeeplyAccessible(true);
        directory.setListingAllowed(true);
        result.attachDefault(directory);

        return result;
    }

    @Override
    public String getName() {
        return "Application for p2.restlet.org";
    }
}
