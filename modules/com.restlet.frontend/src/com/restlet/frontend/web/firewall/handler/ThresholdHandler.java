package com.restlet.frontend.web.firewall.handler;

import org.restlet.Request;
import org.restlet.Response;

import com.restlet.frontend.web.firewall.counter.CounterFeedback;
import com.restlet.frontend.web.firewall.type.HandlerType;
import com.restlet.frontend.web.firewall.type.UserType;
import com.restlet.frontend.web.firewall.user.FirewallUser;

public abstract class ThresholdHandler {

    protected int limit;

    protected HandlerType handlerType;

    public int beforeHandle(Request request, Response response,
            FirewallUser user, CounterFeedback counterFeedback) {
        if (counterFeedback.getConsumed() < limit) {
            return permited(request, response, user, counterFeedback);
        } else {
            return notPermited(request, response, user, counterFeedback);
        }
    }

    protected abstract int permited(Request request, Response response,
            FirewallUser user, CounterFeedback counterFeedback);

    protected abstract int notPermited(Request request, Response response,
            FirewallUser user, CounterFeedback counterFeedback);

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public HandlerType getHandlerType() {
        return handlerType;
    }

    public void setHandlerType(HandlerType handlerType) {
        this.handlerType = handlerType;
    }
    
    /**
     * Factories
     */
    
    public static ThresholdHandler createRateLimitationHandler(int limit,
            HandlerType handlerType) {
        return new RateLimitationHandler(limit, handlerType);
    }

    public static ThresholdHandler createAlertHandler(int limit,
            HandlerType handlerType) {
        return new AlertHandler(limit, handlerType);
    }
}
