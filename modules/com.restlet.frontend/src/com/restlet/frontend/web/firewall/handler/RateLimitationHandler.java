package com.restlet.frontend.web.firewall.handler;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Status;
import org.restlet.routing.Filter;

import com.restlet.frontend.web.firewall.user.FirewallUser;
import com.restlet.frontend.web.firewall.user.UserType;

public class RateLimitationHandler extends ThresholdHandler {

    public RateLimitationHandler(int limit, UserType userType) {
        this.limit = limit;
        this.userType = userType;
    }

    @Override
    protected int permited(Request request, Response response, FirewallUser user) {
        System.out.println("not limited");
        return Filter.CONTINUE;
    }

    @Override
    protected int notPermited(Request request, Response response,
            FirewallUser user) {
        System.out.println("limited");
        response.setStatus(Status.valueOf(429),
                "Too many requests for " + user.getIdentifier()
                + ": rate limitation.");
        return Filter.SKIP;
    }

}
