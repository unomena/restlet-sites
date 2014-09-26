package com.restlet.frontend.web.resources;

import org.restlet.resource.Get;

import com.restlet.frontend.objects.framework.VersionsList;

public interface VersionsResource {
    @Get
    public VersionsList list();
}
