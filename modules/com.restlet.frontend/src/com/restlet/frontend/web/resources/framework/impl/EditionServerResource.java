package com.restlet.frontend.web.resources.framework.impl;

import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.restlet.frontend.objects.framework.Edition;
import com.restlet.frontend.objects.framework.Version;
import com.restlet.frontend.web.applications.WwwRestletOrg;

public class EditionServerResource extends ServerResource {

    private Edition edition;

    @Override
    protected void doInit() throws ResourceException {
        Version version = null;
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
        if (version != null) {
            str = getAttribute("edition");
            for (Edition e : version.getEditions()) {
                if (str.equals(e.getId())) {
                    edition = e;
                    break;
                }
            }
        }
        setExisting(edition != null);
    }

    @Get
    public Edition represent() {
        return edition;
    }

}
