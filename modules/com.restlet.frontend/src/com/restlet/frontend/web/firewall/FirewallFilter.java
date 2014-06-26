package com.restlet.frontend.web.firewall;

import java.util.ArrayList;
import java.util.List;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.routing.Filter;

import com.restlet.frontend.web.firewall.counter.OnPeriodInMemoryTrafficCounter;
import com.restlet.frontend.web.firewall.counter.TrafficCounter;
import com.restlet.frontend.web.firewall.counter.countingPolicy.IPCountingPolicy;
import com.restlet.frontend.web.firewall.counter.countingPolicy.UserCountingPolicy;

public class FirewallFilter extends Filter {

    private List<TrafficCounter> trafficCounters;

    public FirewallFilter(Restlet next) {
        this.setNext(next);
    }

    public FirewallFilter() {

    }

    @Override
    protected int beforeHandle(Request request, Response response) {

        for (TrafficCounter trafficCounter : trafficCounters) {
            String counterValue = trafficCounter.determineCounterValue(request);
            if (counterValue == null) {
                continue;
            }
            int code = trafficCounter.countAndAction(request, response,
                    counterValue);
            if (code != Filter.CONTINUE || trafficCounter.isEnough()) {
                return code;
            }
        }

        return Filter.CONTINUE;
    }

    @Override
    protected void afterHandle(Request request, Response response) {
        // TODO
        // for (TrafficCounter timeDefiner : trafficCounters) {
        // timeDefiner.afterHandle(request, response);
        // }
    }

    public void addCounter(TrafficCounter counter) {

        if (trafficCounters == null) {
            trafficCounters = new ArrayList<TrafficCounter>();
        }
        trafficCounters.add(counter);
    }

    /**
     * TrafficCounter creators
     */

    public TrafficCounter addPeriodInMemoryIPCounter(int period) {
        TrafficCounter trafficCounter = new OnPeriodInMemoryTrafficCounter(period,
                new IPCountingPolicy());
        this.addCounter(trafficCounter);
        return trafficCounter;
    }

    public TrafficCounter addPeriodInMemoryUserCounter(int period) {
        TrafficCounter trafficCounter = new OnPeriodInMemoryTrafficCounter(
                period, new UserCountingPolicy());
        this.addCounter(trafficCounter);
        return trafficCounter;
    }

}
