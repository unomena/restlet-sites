package com.restlet.frontend.web.firewall.counter.countingPolicy;

public class WebApiInMemoryCountingPolicy extends WebApiCountingPolicy {

    @Override
    public String determineCounterGroup(String counterValue) {
        return null;
    }

}
