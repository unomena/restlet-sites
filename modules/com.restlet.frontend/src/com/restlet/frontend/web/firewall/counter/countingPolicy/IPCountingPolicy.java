package com.restlet.frontend.web.firewall.counter.countingPolicy;

import org.restlet.Request;

public class IPCountingPolicy implements CountingPolicy {

    @Override
    public String determineCounterValue(Request request) {
        return request.getResourceRef().getQueryAsForm()
                .getFirstValue("firewall");
    }

    @Override
    public String determineCounterGroup(String counterValue) {
        return null;
    }

}
