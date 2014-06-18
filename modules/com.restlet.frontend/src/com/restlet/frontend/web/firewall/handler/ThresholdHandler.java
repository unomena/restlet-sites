package com.restlet.frontend.web.firewall.handler;

import org.restlet.Request;
import org.restlet.Response;

import com.restlet.frontend.web.firewall.user.FirewallUser;
import com.restlet.frontend.web.firewall.user.UserType;

public abstract class ThresholdHandler {

    protected int limit;

    protected UserType userType;

    public int beforeHandle(Request request, Response response, int consumed,
            FirewallUser user) {
        if (consumed < limit) {
            return permited(request, response, user);
        } else {
            return notPermited(request, response, user);
        }
    }

    protected abstract int permited(Request request, Response response,
            FirewallUser user);

    protected abstract int notPermited(Request request, Response response,
            FirewallUser user);

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
    
    /**
     * Factories
     */
    
    public static ThresholdHandler createRateLimitationHandler(int limit,
            UserType userType) {
        return new RateLimitationHandler(limit, userType);
    }

    public static ThresholdHandler createAlertHandler(int limit,
            UserType userType) {
        return new AlertHandler(limit, userType);
    }
}
