/*
 * Copyright 2005-2013 Restlet. All rights reserved.
 */

package com.restlet.frontend.web.resources.framework.impl;

import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.restlet.frontend.web.applications.RestletCom;

/**
 * Resource that refreshes the pages for the Restlet Web site.
 * 
 * @author Thierry Boileau
 * @author Jerome Louvel
 */
public class RestletOrgRefreshResource extends ServerResource {

    @Post
    public String refresh() {
        ((RestletCom) getApplication()).refresh();
        return "refresh done";
    }

}
