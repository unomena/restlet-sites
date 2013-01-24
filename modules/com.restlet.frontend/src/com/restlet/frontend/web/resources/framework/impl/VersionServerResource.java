package com.restlet.frontend.web.resources.framework.impl;

import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.restlet.frontend.objects.framework.Version;
import com.restlet.frontend.web.applications.WwwRestletOrg;

public class VersionServerResource extends ServerResource {

    private Version version;

    @Override
    protected void doInit() throws ResourceException {
        String str = getAttribute("version");
        if (str != null
                && ((WwwRestletOrg) getApplication()).getVersions() != null) {
            for (Version v : ((WwwRestletOrg) getApplication()).getVersions()) {
                if (str.equals(v.getId())) {
                    version = v;
                    break;
                }
            }
        }

        setExisting(version != null);
    }

    @Get
    public Version represent() {
        return version;
    }

}
