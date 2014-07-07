package com.restlet.frontend.web.firewall.counter.countingPolicy;

import org.restlet.Request;

public interface CountingPolicy {

    public String determineCounterValue(Request request);

    public String determineCounterGroup(String counterValue);

}
