package com.restlet.frontend.web.resources;

import org.restlet.resource.Get;

import com.restlet.frontend.objects.framework.DistributionsList;

public interface DistributionsResource {
    @Get
    public DistributionsList list();
}
