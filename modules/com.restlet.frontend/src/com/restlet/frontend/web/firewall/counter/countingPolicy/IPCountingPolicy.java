package com.restlet.frontend.web.firewall.counter.countingPolicy;

import org.restlet.Request;

public class IPCountingPolicy implements CountingPolicy {

    @Override
    public String determineCounterValue(Request request) {
        // TODO : and if getAddress null ?
        return request.getClientInfo().getAddress();
    }

}
