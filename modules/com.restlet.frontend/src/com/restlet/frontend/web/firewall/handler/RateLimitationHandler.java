package com.restlet.frontend.web.firewall.handler;

import java.util.logging.Level;

import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Status;
import org.restlet.engine.header.Header;
import org.restlet.engine.header.HeaderConstants;
import org.restlet.routing.Filter;
import org.restlet.util.Series;

import com.restlet.frontend.web.firewall.counter.CounterFeedback;

public class RateLimitationHandler extends ThresholdHandler implements
        TrafficOverrider {

    public RateLimitationHandler(int limit) {
        super(limit);
    }

    @Override
    public void overrideTraffic(Request request, Response response,
            CounterFeedback counterFeedback) {
        if (counterFeedback.getConsumed() <= getLimit(counterFeedback
                .getGroup())) {
            setHeaders(response, counterFeedback);
        }
    }

    @Override
    public int thresholdActivated(Request request, Response response,
            CounterFeedback counterFeedback) {
        
        Context.getCurrentLogger().log(Level.FINE,
                "User " + counterFeedback.getCounterValue() + " rate limited.");
                
        response.setStatus(Status.CLIENT_ERROR_FORBIDDEN,
                "Too many requests for " + counterFeedback.getCounterValue()
                        + ": rate limitation.");
        return Filter.SKIP;
    }

    private void setHeaders(Response response, CounterFeedback counterFeedback) {
        @SuppressWarnings("unchecked")
        Series<Header> headers = (Series<Header>) response.getAttributes().get(
                "org.restlet.http.headers");
        if (headers == null) {
            headers = new Series<Header>(Header.class);
        }
        headers.set("X-RateLimit-Remaining",
                Integer.toString(getLimit(counterFeedback.getGroup())
                        - counterFeedback.getConsumed()));
        headers.set("X-RateLimit-Limit",
                Integer.toString(getLimit(counterFeedback.getGroup())));
        headers.set("X-RateLimit-Reset",
                Long.toString(counterFeedback.getReset()));
        response.getAttributes()
                .put(HeaderConstants.ATTRIBUTE_HEADERS, headers);
    }

}
