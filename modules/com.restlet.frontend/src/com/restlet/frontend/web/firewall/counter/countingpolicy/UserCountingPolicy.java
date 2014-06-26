package com.restlet.frontend.web.firewall.counter.countingpolicy;

import org.restlet.Request;

import com.restlet.frontend.web.firewall.FirewallFilter;

public class UserCountingPolicy implements CountingPolicy {

    @Override
    public String determineCounterValue(Request request) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int onDetermineCounterValueFail() {
        return FirewallFilter.COUNTER_VALIDATE;
    }

}
