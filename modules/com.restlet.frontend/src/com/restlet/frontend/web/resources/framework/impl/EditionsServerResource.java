package com.restlet.frontend.web.resources.framework.impl;

import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.restlet.frontend.objects.framework.EditionsList;
import com.restlet.frontend.objects.framework.Version;
import com.restlet.frontend.web.applications.WwwRestletOrg;
import com.restlet.frontend.web.resources.framework.EditionsResource;

public class EditionsServerResource extends ServerResource implements
        EditionsResource {

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
        setExisting(version != null && version.getEditions() != null);
    }

    @Override
    public EditionsList list() {
        return version.getEditions();
    }

}
