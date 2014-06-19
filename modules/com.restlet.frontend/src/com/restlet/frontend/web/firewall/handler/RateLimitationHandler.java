package com.restlet.frontend.web.firewall.handler;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Status;
import org.restlet.routing.Filter;

import com.restlet.frontend.web.firewall.counter.CounterFeedback;
import com.restlet.frontend.web.firewall.type.HandlerType;
import com.restlet.frontend.web.firewall.user.FirewallUser;

public class RateLimitationHandler extends ThresholdHandler {

    public RateLimitationHandler(int limit, HandlerType handlerType) {
        this.limit = limit;
        this.handlerType = handlerType;
    }

    @Override
    protected int permited(Request request, Response response,
            FirewallUser user, CounterFeedback counterFeedback) {
        System.out.println("not limited");
        return Filter.CONTINUE;
    }

    @Override
    protected int notPermited(Request request, Response response,
            FirewallUser user, CounterFeedback counterFeedback) {
        System.out.println("limited");
        response.setStatus(Status.valueOf(429),
                "Too many requests for " + user.getIdentifier()
                + ": rate limitation.");
        return Filter.SKIP;
    }

}
