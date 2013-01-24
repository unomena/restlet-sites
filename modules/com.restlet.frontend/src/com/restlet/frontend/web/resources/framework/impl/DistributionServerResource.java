package com.restlet.frontend.web.resources.framework.impl;

import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.restlet.frontend.objects.framework.Distribution;
import com.restlet.frontend.objects.framework.DistributionsList;
import com.restlet.frontend.web.applications.WwwRestletOrg;

public class DistributionServerResource extends ServerResource {

    private Distribution distribution;

    @Override
    protected void doInit() throws ResourceException {
        DistributionsList fullList = ((WwwRestletOrg) getApplication())
                .getDistributions();
        String v = getAttribute("version");
        String e = getAttribute("edition");
        String d = getAttribute("distribution");

        if (v != null && e != null) {
            for (Distribution distribution : fullList) {
                if (v.equals(distribution.getVersion())
                        && e.equals(distribution.getEdition())
                        && d.equals(distribution.getId())) {
                    this.distribution = distribution;
                }
            }
        }

        setExisting(distribution != null);
    }

    @Get
    public Distribution represent() {
        return distribution;
    }
}
