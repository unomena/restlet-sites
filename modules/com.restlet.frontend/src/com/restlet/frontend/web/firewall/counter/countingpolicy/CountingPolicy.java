package com.restlet.frontend.web.firewall.counter.countingpolicy;

import org.restlet.Request;

public interface CountingPolicy {

    public String determineCounterValue(Request request);

    public int onDetermineCounterValueFail();

}
