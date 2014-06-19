package com.restlet.frontend.web.firewall.counter;

import java.util.ArrayList;
import java.util.List;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.routing.Filter;

import com.google.common.cache.LoadingCache;
import com.restlet.frontend.web.firewall.handler.AlertHandler;
import com.restlet.frontend.web.firewall.handler.RateLimitationHandler;
import com.restlet.frontend.web.firewall.handler.ThresholdHandler;
import com.restlet.frontend.web.firewall.type.HandlerType;
import com.restlet.frontend.web.firewall.user.FirewallUser;

public abstract class TrafficCounter {

    protected LoadingCache<String, ? extends UserTrafficCounter> cache;

    private List<ThresholdHandler> handlers;

    public int beforeHandle(Request request, Response response) {

        FirewallUser user = FirewallUser.getUser(request);

        UserTrafficCounter counter = cache.getUnchecked(user.getIdentifier());
        CounterFeedback counterFeedback = counter.increase();

        for (ThresholdHandler handler : handlers) {
            if (user.getType().equals(handler.getHandlerType())) {
                int handlerResponse = handler.beforeHandle(request, response,
                        user, counterFeedback);
                if (handlerResponse != Filter.CONTINUE) {
                    return handlerResponse;
                }
            }
        }
        return Filter.CONTINUE;
    };

    public void afterHandle(Request request, Response response) {

        if (this instanceof SimultaneousCounter) {

            FirewallUser user = FirewallUser.getUser(request);
            UserTrafficCounter counter = cache.getUnchecked(user.getIdentifier());

            counter.decrease();

        }

    }

    public void addHandler(ThresholdHandler handler) {

        if (handlers == null) {
            handlers = new ArrayList<ThresholdHandler>();
        }

        insertHandler(handler, 0, handlers.size());
    }

    private void insertHandler(ThresholdHandler handler, int posMin, int posMax) {
        if (posMax - posMin == 0) {
            handlers.add(posMin, handler);
        } else {
            int posAvg = (posMax - posMin) / 2;
            if (handler.getLimit() > handlers.get(posAvg).getLimit()) {
                insertHandler(handler, posMin, posAvg);
            } else {
                insertHandler(handler, posAvg, posMax);
            }
        }
    }

    /**
     * ThresholdHandler creators
     */

    public ThresholdHandler addRateLimitationHandler(int limit,
            HandlerType handlerType) {
        ThresholdHandler th = new RateLimitationHandler(limit, handlerType);
        this.addHandler(th);
        return th;
    }

    public ThresholdHandler addAlertHandler(int limit, HandlerType handlerType) {
        ThresholdHandler th = new AlertHandler(limit, handlerType);
        this.addHandler(th);
        return th;
    }

    /**
     * Factories
     */

    public static TrafficCounter createPeriodDefiner(int period) {
        return new PeriodCounter(period);
    }

    public static TrafficCounter createSimultaneousDefiner() {
        return new SimultaneousCounter();
    }

}
