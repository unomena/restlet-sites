package com.restlet.frontend.web.firewall.counter.countingPolicy;

import org.restlet.Request;

public abstract class WebApiCountingPolicy implements CountingPolicy {

    @Override
    public String determineCounterValue(Request request) {
        return request.getResourceRef().getHostDomain().split("\\.")[0];
    }

}
