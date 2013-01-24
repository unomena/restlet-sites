/*
 * Copyright 2005-2013 Restlet. All rights reserved.
 */

package com.restlet.frontend.web.applications;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Redirector;

/**
 * Application redirecting to a target URI.
 * 
 * @author Jerome Louvel
 */
public class RedirectApplication extends Application {
    /** The target URI template. */
    private String targetUri;

    /** Indicates the redirection mode. */
    private int mode;

    /**
     * Constructor.
     * 
     * @param targetUri
     *            The target URI template.
     * @param mode
     *            Indicates the type of redirection (see Redirector.MODE_*
     *            constants).
     */
    public RedirectApplication(String targetUri, int mode) {
        // Turn off extension tunnelling because of redirections.
        this.getTunnelService().setExtensionsTunnel(false);

        this.targetUri = targetUri;
        this.mode = mode;
    }

    @Override
    public String getName() {
        return "Redirection application";
    }

    @Override
    public Restlet createInboundRoot() {
        return new Redirector(getContext(), this.targetUri, this.mode);
    }
}
