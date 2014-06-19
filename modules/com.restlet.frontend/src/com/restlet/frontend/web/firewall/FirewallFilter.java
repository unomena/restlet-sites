package com.restlet.frontend.web.firewall;

import java.util.ArrayList;
import java.util.List;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.routing.Filter;

import com.restlet.frontend.web.firewall.counter.PeriodCounter;
import com.restlet.frontend.web.firewall.counter.SimultaneousCounter;
import com.restlet.frontend.web.firewall.counter.TrafficCounter;
import com.restlet.frontend.web.firewall.type.HandlerType;

public class FirewallFilter extends Filter {

    private List<TrafficCounter> trafficCounters;

    public FirewallFilter(Restlet next) {
        this.setNext(next);
    }

    public FirewallFilter() {

    }

    @Override
    protected int beforeHandle(Request request, Response response) {

        int responseCode = Filter.CONTINUE;
        for (TrafficCounter timeDefiner : trafficCounters) {
            int code = timeDefiner.beforeHandle(request, response);
            if (code > responseCode) {
                responseCode = code;
            }
        }
        return responseCode;
    }

    @Override
    protected void afterHandle(Request request, Response response) {
        for (TrafficCounter timeDefiner : trafficCounters) {
            timeDefiner.afterHandle(request, response);
        }
    }

    public void addCounter(TrafficCounter definer) {

        if (trafficCounters == null) {
            trafficCounters = new ArrayList<TrafficCounter>();
        }
        trafficCounters.add(definer);
    }

    /**
     * TrafficCounter creators
     */

    public TrafficCounter addPeriodCounter(int period) {
        TrafficCounter timeCounter = new PeriodCounter(period);
        this.addCounter(timeCounter);
        return timeCounter;
    }

    public TrafficCounter addSimultaneousCounter() {
        TrafficCounter timeCounter = new SimultaneousCounter();
        this.addCounter(timeCounter);
        return timeCounter;
    }

    /**
     * Structure creator (TrafficCounter + ThresholdHandler)
     */

    public TrafficCounter addPeriodRateLimitationHandler(int period, int limit,
            HandlerType handlerType) {
        TrafficCounter trafficCounter = this.addPeriodCounter(period);
        trafficCounter.addRateLimitationHandler(limit, handlerType);
        return trafficCounter;
    }

    public TrafficCounter addPeriodAlertHandler(int period, int limit,
            HandlerType handlerType) {
        TrafficCounter trafficCounter = this.addPeriodCounter(period);
        trafficCounter.addAlertHandler(limit, handlerType);
        return trafficCounter;
    }

    public TrafficCounter addSimultaneousRateLimitationHandler(int limit,
            HandlerType handlerType) {
        TrafficCounter trafficCounter = this.addSimultaneousCounter();
        trafficCounter.addRateLimitationHandler(limit, handlerType);
        return trafficCounter;
    }

    public TrafficCounter addSimultaneousAlertHandler(int limit,
            HandlerType handlerType) {
        TrafficCounter trafficCounter = this.addSimultaneousCounter();
        trafficCounter.addAlertHandler(limit, handlerType);
        return trafficCounter;
    }
}
