package com.restlet.frontend.web.firewall.counter;

import java.util.ArrayList;
import java.util.List;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.routing.Filter;

import com.restlet.frontend.web.firewall.counter.countingPolicy.CountingPolicy;
import com.restlet.frontend.web.firewall.handler.RateLimitationHandler;
import com.restlet.frontend.web.firewall.handler.ThresholdHandler;
import com.restlet.frontend.web.firewall.handler.TrafficOverrider;
import com.restlet.frontend.web.firewall.old.counter.CounterFeedback;

public abstract class TrafficCounter {

    private CountingPolicy countingPolicy;

    private List<ThresholdHandler> handlers;

    private List<TrafficOverrider> trafficOverriders;

    private boolean enough;

    public TrafficCounter(CountingPolicy countingPolicy) {
        handlers = new ArrayList<ThresholdHandler>();
        trafficOverriders = new ArrayList<TrafficOverrider>();
        this.setCountingPolicy(countingPolicy);
    }

    public void addHandler(ThresholdHandler handler) {

        if (handler instanceof TrafficOverrider) {
            trafficOverriders.add((TrafficOverrider) handler);
        }

        if (handlers == null) {
            handlers = new ArrayList<ThresholdHandler>();
        }

        insertHandler(handler, 0, handlers.size());
    }

    public void setCountingPolicy(CountingPolicy countingPolicy) {
        this.countingPolicy = countingPolicy;
    }

    public int countAndAction(Request request, Response response,
            String counterValue) {

        CounterFeedback counterFeedback = increaseCounter(counterValue);

        for (TrafficOverrider trafficOverrider : trafficOverriders) {
            trafficOverrider.overrideTraffic(request, response,
                    counterValue, counterFeedback);
        }

        for (ThresholdHandler handler : handlers) {
            if (handler.getLimit() < counterFeedback.getConsumed()) {
                int handlerResponse = handler.thresholdActivated(request,
                        response, counterValue, counterFeedback);
                if (handlerResponse != Filter.CONTINUE) {
                    return handlerResponse;
                }
            }
        }

        return Filter.CONTINUE;
    }

    protected abstract CounterFeedback increaseCounter(String counterValue);

    public String determineCounterValue(Request request) {
        return this.countingPolicy.determineCounterValue(request);
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

    public boolean isEnough() {
        return enough;
    }

    public void setEnough(boolean enough) {
        this.enough = enough;
    }

    /**
     * Handler creator
     */

    public ThresholdHandler createRateLimitationHandler(int limit) {
        ThresholdHandler handler = new RateLimitationHandler(limit);
        this.addHandler(handler);
        return handler;
    }

}
