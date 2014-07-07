package com.restlet.frontend.web.firewall.handler;

import java.util.HashMap;
import java.util.Map;

import org.restlet.Request;
import org.restlet.Response;

import com.restlet.frontend.web.firewall.counter.CounterFeedback;

public abstract class ThresholdHandler {

    public ThresholdHandler(int limit) {
        this.defaultLimit = limit;
        limits = new HashMap<String, Integer>();
    }

    private Map<String, Integer> limits;

    private int defaultLimit;

    public int getLimit() {
        return this.defaultLimit;
    }

    public int getLimit(String group) {
        if (group == null) {
            return getLimit();
        }
        return this.limits.get(group);
    }
    
    public void addGroup(String group, int limit) {
        this.limits.put(group, limit);
    }

    public abstract int thresholdActivated(Request request, Response response,
            CounterFeedback counterFeedback);

}
