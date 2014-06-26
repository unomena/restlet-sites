package com.restlet.frontend.web.firewall.handler;

import org.restlet.Request;
import org.restlet.Response;

import com.restlet.frontend.web.firewall.old.counter.CounterFeedback;

public class AlertHandler extends ThresholdHandler {

    public AlertHandler(int limit) {
        super(limit);
        // TODO Auto-generated constructor stub
    }

    @Override
    public int thresholdActivated(Request request, Response response,
            String identifier, CounterFeedback counterFeedback) {
        // TODO Auto-generated method stub
        return 0;
    }

}
