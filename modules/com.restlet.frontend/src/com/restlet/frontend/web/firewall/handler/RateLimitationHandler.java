package com.restlet.frontend.web.firewall.handler;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Status;
import org.restlet.engine.header.Header;
import org.restlet.engine.header.HeaderConstants;
import org.restlet.routing.Filter;
import org.restlet.util.Series;

import com.restlet.frontend.web.firewall.old.counter.CounterFeedback;

public class RateLimitationHandler extends ThresholdHandler implements
        TrafficOverrider {

    public RateLimitationHandler(int limit) {
        super(limit);
    }

    @Override
    public void overrideTraffic(Request request, Response response,
            String identifier, CounterFeedback counterFeedback) {
        if (counterFeedback.getConsumed() <= getLimit()) {
            setHeaders(response, counterFeedback);
        }

    }

    @Override
    public int thresholdActivated(Request request, Response response,
            String identifier, CounterFeedback counterFeedback) {
        response.setStatus(Status.valueOf(429), "Too many requests for "
                + identifier + ": rate limitation.");
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
                Integer.toString(getLimit() - counterFeedback.getConsumed()));
        headers.set("X-RateLimit-Limit", Integer.toString(getLimit()));
        headers.set("X-RateLimit-Reset",
                Long.toString(counterFeedback.getReset()));
        response.getAttributes()
                .put(HeaderConstants.ATTRIBUTE_HEADERS, headers);
    }

}
