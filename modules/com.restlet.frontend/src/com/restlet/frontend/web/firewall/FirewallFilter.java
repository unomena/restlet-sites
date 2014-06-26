package com.restlet.frontend.web.firewall;

import java.util.ArrayList;
import java.util.List;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.routing.Filter;

import com.restlet.frontend.web.firewall.counter.OnPeriodInMemoryTrafficCounter;
import com.restlet.frontend.web.firewall.counter.TrafficCounter;
import com.restlet.frontend.web.firewall.counter.countingpolicy.IPCountingPolicy;

public class FirewallFilter extends Filter {

    public final static int COUNTER_VALIDATE = 0;

    public final static int COUNTER_CONTINUE = 1;

    public final static int COUNTER_STOP = 2;

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
            int code = timeDefiner.countAndAction(request, response);
            if (code == Filter.STOP || code == Filter.SKIP) {
                return code;
            }
        }
        return responseCode;
    }

    @Override
    protected void afterHandle(Request request, Response response) {
        // TODO
        // for (TrafficCounter timeDefiner : trafficCounters) {
        // timeDefiner.afterHandle(request, response);
        // }
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

    public TrafficCounter addPeriodInMemoryIPCounter(int period) {
        TrafficCounter timeCounter = new OnPeriodInMemoryTrafficCounter(period,
                new IPCountingPolicy());
        this.addCounter(timeCounter);
        return timeCounter;
    }

}
