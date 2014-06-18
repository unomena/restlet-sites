package com.restlet.frontend.web.firewall.definer;

import java.util.ArrayList;
import java.util.List;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.routing.Filter;

import com.google.common.cache.LoadingCache;
import com.restlet.frontend.web.firewall.counter.TrafficCounter;
import com.restlet.frontend.web.firewall.handler.ThresholdHandler;
import com.restlet.frontend.web.firewall.user.FirewallUser;

public abstract class TimeDefiner {

    protected LoadingCache<String, ? extends TrafficCounter> cache;

    private List<ThresholdHandler> handlers;

    public int beforeHandle(Request request, Response response) {

        FirewallUser user = FirewallUser.getUser(request);

        TrafficCounter counter = cache.getUnchecked(user.getIdentifier());
        int consumed = counter.increase();

        for (ThresholdHandler handler : handlers) {
            if (user.getType().equals(handler.getUserType())) {
                int handlerResponse = handler.beforeHandle(request, response,
                        consumed, user);
                if (handlerResponse != Filter.CONTINUE) {
                    return handlerResponse;
                }
            }
        }
        return Filter.CONTINUE;
    };

    public void afterHandle(Request request, Response response) {

        if (this instanceof SimultaneousDefiner) {

            FirewallUser user = FirewallUser.getUser(request);
            TrafficCounter counter = cache.getUnchecked(user.getIdentifier());

            counter.decrease();

        }

    }

    public void addHandler(ThresholdHandler handler) {

        if (handlers == null) {
            handlers = new ArrayList<ThresholdHandler>();
        }

        insertHandler(handler, 0, handlers.size());
    }

    public void insertHandler(ThresholdHandler handler, int posMin, int posMax) {
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
     * Factories
     */

    public static TimeDefiner createPeriodDefiner(int period) {
        return new PeriodDefiner(period);
    }

    public static TimeDefiner createSimultaneousDefiner() {
        return new SimultaneousDefiner();
    }

}
