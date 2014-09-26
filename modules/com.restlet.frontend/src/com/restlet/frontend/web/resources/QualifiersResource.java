package com.restlet.frontend.web.resources;

import org.restlet.resource.Get;

import com.restlet.frontend.objects.framework.QualifiersList;

public interface QualifiersResource {
    @Get
    public QualifiersList list();
}
