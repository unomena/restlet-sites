package com.restlet.frontend.web.resources.framework.impl;

import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.restlet.frontend.objects.framework.Distribution;
import com.restlet.frontend.objects.framework.DistributionsList;
import com.restlet.frontend.web.applications.WwwRestletOrg;
import com.restlet.frontend.web.resources.framework.DistributionsResource;

public class DistributionsServerResource extends ServerResource implements
        DistributionsResource {

    private DistributionsList distributions;

    @Override
    protected void doInit() throws ResourceException {
        DistributionsList fullList = ((WwwRestletOrg) getApplication())
                .getDistributions();
        String version = getAttribute("version");
        String edition = getAttribute("edition");

        if (version != null && edition != null) {
            distributions = new DistributionsList();
            for (Distribution distribution : fullList) {
                if (version.equals(distribution.getVersion())
                        && edition.equals(distribution.getEdition())) {
                    distributions.add(distribution);
                }
            }
        }

        setExisting(distributions != null);
    }

    @Override
    public DistributionsList list() {
        return distributions;
    }

}
