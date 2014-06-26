package com.restlet.frontend.web.firewall.counter;

import com.restlet.frontend.web.firewall.counter.countingpolicy.CountingPolicy;
import com.restlet.frontend.web.firewall.old.counter.CounterFeedback;

public class ConcurrentTrafficCounter extends TrafficCounter {

    public ConcurrentTrafficCounter(CountingPolicy countingPolicy) {
        super(countingPolicy);
    }

    @Override
    protected CounterFeedback increaseCounter(String counterValue) {
        // TODO Auto-generated method stub
        return null;
    }

}
