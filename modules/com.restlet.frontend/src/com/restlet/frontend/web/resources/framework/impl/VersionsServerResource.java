package com.restlet.frontend.web.resources.framework.impl;

import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.restlet.frontend.objects.framework.VersionsList;
import com.restlet.frontend.web.applications.WwwRestletOrg;
import com.restlet.frontend.web.resources.framework.VersionsResource;

public class VersionsServerResource extends ServerResource implements
        VersionsResource {

    @Override
    protected void doInit() throws ResourceException {
        setExisting(((WwwRestletOrg) getApplication()).getVersions() != null);
    }

    @Override
    public VersionsList list() {
        return ((WwwRestletOrg) getApplication()).getVersions();
    }

}
