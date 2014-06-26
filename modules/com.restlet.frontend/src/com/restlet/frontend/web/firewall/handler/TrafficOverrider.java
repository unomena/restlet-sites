package com.restlet.frontend.web.firewall.handler;

import org.restlet.Request;
import org.restlet.Response;

import com.restlet.frontend.web.firewall.old.counter.CounterFeedback;

public interface TrafficOverrider {

    public void overrideTraffic(Request request, Response response,
            String identifier, CounterFeedback counterFeedback);

}
