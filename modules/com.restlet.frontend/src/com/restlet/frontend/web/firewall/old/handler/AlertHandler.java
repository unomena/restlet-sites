package com.restlet.frontend.web.firewall.old.handler;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Status;
import org.restlet.routing.Filter;

import com.restlet.frontend.web.firewall.old.counter.CounterFeedback;
import com.restlet.frontend.web.firewall.old.type.HandlerType;
import com.restlet.frontend.web.firewall.old.user.FirewallUser;

public class AlertHandler extends ThresholdHandler {

    public AlertHandler(int limit, HandlerType handlerType) {
        this.limit = limit;
        this.handlerType = handlerType;
    }

    @Override
    protected int permited(Request request, Response response,
            FirewallUser user, CounterFeedback counterFeedback) {
        System.out.println("not alert");
        return Filter.CONTINUE;
    }

    @Override
    protected int notPermited(Request request, Response response,
            FirewallUser user, CounterFeedback counterFeedback) {
        System.out.println("alert");
        response.setStatus(Status.valueOf(429),
                "Too many requests for " + user.getIdentifier()
                + ": rate limitation.");
        return Filter.SKIP;
    }

}
