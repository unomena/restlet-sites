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

        handlers.add(handler);
    }

    public void setCountingPolicy(CountingPolicy countingPolicy) {
        this.countingPolicy = countingPolicy;
    }

    public int countAndAction(Request request, Response response,
            String counterValue) {

        CounterFeedback counterFeedback = increaseCounter(counterValue);
        counterFeedback.setGroup(determineCounterGroup(counterValue));
        counterFeedback.setCounterValue(counterValue);

        for (TrafficOverrider trafficOverrider : trafficOverriders) {
            trafficOverrider
                    .overrideTraffic(request, response, counterFeedback);
        }

        for (ThresholdHandler handler : handlers) {
            if (handler.getLimit(counterFeedback.getGroup()) < counterFeedback
                    .getConsumed()) {
                int handlerResponse = handler.thresholdActivated(request,
                        response, counterFeedback);
                if (handlerResponse != Filter.CONTINUE) {
                    return handlerResponse;
                }
            }
        }

        return Filter.CONTINUE;
    }

    public void decrease(String counterValue) {
        decreaseCounter(counterValue);
    }

    protected abstract CounterFeedback increaseCounter(String counterValue);
    
    protected abstract void decreaseCounter(String counterValue);

    private String determineCounterGroup(String counterValue) {
        return countingPolicy.determineCounterGroup(counterValue);
    }

    public String determineCounterValue(Request request) {
        return this.countingPolicy.determineCounterValue(request);
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
