package com.restlet.frontend.web.firewall.handler;

import java.util.logging.Level;

import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.routing.Filter;

import com.restlet.frontend.web.firewall.counter.CounterFeedback;
import com.restlet.frontend.web.firewall.lister.FirewallLister;

public class BlacklistHandler extends ThresholdHandler {

    public BlacklistHandler(int limit) {
        super(limit);
    }

    @Override
    public int thresholdActivated(Request request, Response response,
            CounterFeedback counterFeedback) {

        if (counterFeedback.getConsumed() == getLimit(counterFeedback
                .getGroup()) + 1
                && request.getAttributes().get("firewallLister") != null) {

            Context.getCurrentLogger().log(
                    Level.INFO,
                    "IP " + request.getClientInfo().getAddress()
                            + " blacklisted");

            ((FirewallLister) request.getAttributes().get("firewallLister"))
                    .addToList(request.getClientInfo().getAddress());

        }

        return Filter.SKIP;
    }

}
