package com.restlet.frontend.web.firewall.handler;

import org.restlet.Request;
import org.restlet.Response;

import com.restlet.frontend.web.firewall.counter.CounterFeedback;


public class AlertHandler extends ThresholdHandler {

    public AlertHandler(int limit) {
        super(limit);
    }

    @Override
    public int thresholdActivated(Request request, Response response,
            CounterFeedback counterFeedback) {
        // TODO Auto-generated method stub
        return 0;
    }

}
