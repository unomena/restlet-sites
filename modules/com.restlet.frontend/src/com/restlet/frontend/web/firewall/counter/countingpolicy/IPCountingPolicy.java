package com.restlet.frontend.web.firewall.counter.countingpolicy;

import org.restlet.Request;

public class IPCountingPolicy implements CountingPolicy {

    @Override
    public String determineCounterValue(Request request) {
        // TODO : and if getAddress null ?
        return request.getClientInfo().getAddress();
    }

    @Override
    public int onDetermineCounterValueFail() {
        // TODO Auto-generated method stub
        return 0;
    }

}
