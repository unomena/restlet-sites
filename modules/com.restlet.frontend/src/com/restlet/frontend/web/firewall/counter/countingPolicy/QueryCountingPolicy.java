package com.restlet.frontend.web.firewall.counter.countingPolicy;

import org.restlet.Request;

public class QueryCountingPolicy implements CountingPolicy {

    @Override
    public String determineCounterValue(Request request) {
        return request.getResourceRef().getQueryAsForm()
                .getFirstValue("firewall");
    }

    @Override
    public String determineCounterGroup(String counterValue) {
        // TODO Auto-generated method stub
        return null;
    }

}
