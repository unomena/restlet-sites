/*
 * Copyright 2005-2013 Restlet. All rights reserved.
 */

package com.restlet.frontend.web.applications;

import java.io.IOException;
import java.util.Properties;

import org.restlet.Application;
import org.restlet.data.CharacterSet;
import org.restlet.data.MediaType;
import org.restlet.data.Protocol;

import com.restlet.frontend.web.WebComponent;

/**
 * Base application that describes shared behaviour.
 * 
 * @author Jerome Louvel
 */
public class BaseApplication extends Application {

    /** Application properties. */
    private Properties properties;

    /**
     * Constructor.
     * 
     * @param propertiesFileReference
     *            The Reference to the application's properties file.
     * @throws IOException
     */
    public BaseApplication(String propertiesFileReference) throws IOException {
        super();
        this.properties = WebComponent.getProperties(propertiesFileReference);
        this.getRangeService().setEnabled(false);
        this.getMetadataService().setDefaultCharacterSet(CharacterSet.UTF_8);
        this.getMetadataService().setDefaultLanguage(null);
        this.getMetadataService().addExtension("html", MediaType.TEXT_HTML,
                true);
        this.getConnectorService().getClientProtocols().add(Protocol.FILE);
    }

    /**
     * Returns the value of the given property, or null. Empty values are set to
     * null.
     * 
     * @param property
     *            The property.
     */
    public String getProperty(String property) {
        String str = this.properties.getProperty(property);
        if (str != null && !str.isEmpty()) {
            return str;
        }
        return null;
    }

}
