/*
 * Copyright 2005-2013 Restlet. All rights reserved.
 */

package com.restlet.frontend.web.resources;

import java.util.Map;

import org.restlet.data.MediaType;
import org.restlet.ext.freemarker.TemplateRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.restlet.frontend.web.applications.RefreshApplication;

import freemarker.template.Configuration;

/**
 * Base resource class that supports common behaviours or attributes shared by
 * all resources.
 */
public class BaseResource extends ServerResource {

    @Override
    protected void doInit() throws ResourceException {
        setConditional(false);
    }

    /**
     * Returns the Freemarker's configuration object used for the generation of
     * all HTML representations.
     * 
     * @return the Freemarker's configuration object.
     */
    private Configuration getFmcConfiguration() {
        return ((RefreshApplication) getApplication()).getFmc();
    }

    /**
     * Returns a templated representation dedicated to HTML content.
     * 
     * @param templateName
     *            the name of the template.
     * @param dataModel
     *            the collection of data processed by the template engine.
     * @return the representation.
     */
    protected Representation getHTMLTemplateRepresentation(String templateName,
            Map<String, Object> dataModel) {
        // The template representation is based on Freemarker.
        return new TemplateRepresentation(templateName, getFmcConfiguration(),
                dataModel, MediaType.TEXT_HTML);
    }

}
