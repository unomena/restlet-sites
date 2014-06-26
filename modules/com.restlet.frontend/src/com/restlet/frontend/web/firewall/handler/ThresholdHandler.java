package com.restlet.frontend.web.firewall.handler;

import org.restlet.Request;
import org.restlet.Response;

import com.restlet.frontend.web.firewall.old.counter.CounterFeedback;

public abstract class ThresholdHandler {

    public ThresholdHandler(int limit) {
        this.limit = limit;
    }

    private int limit;

    public int getLimit() {
        return this.limit;
    }
    
    public abstract int thresholdActivated(Request request, Response response,
            String identifier, CounterFeedback counterFeedback);

}
