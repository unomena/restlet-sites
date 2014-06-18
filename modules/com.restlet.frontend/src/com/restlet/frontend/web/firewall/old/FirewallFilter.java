package com.restlet.frontend.web.firewall.old;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.Status;
import org.restlet.engine.header.Header;
import org.restlet.engine.header.HeaderConstants;
import org.restlet.routing.Filter;
import org.restlet.util.Series;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * Filter which handles rate limitation. This class handles with rate limitation
 * in-memory, so, it is made to deal with short-time rate limitation periods
 * (seconds to hour). The following features are available in this class :
 * <ul>
 * <li>Rate limitation : allows a maximum number of requests per user
 * (authenticated or not) per period.
 * <li>Simultaneous calls : throttles the number of simultaneous calls per user
 * (authenticated or not).
 * </ul>
 * 
 * @author Guillaume Blondeau
 * 
 */
public class FirewallFilter extends Filter {

    /**
     * Cache used to match username (or public IP for anonymous users) to their
     * SpeedRateLimiter instance.
     * 
     * @see http
     *      ://docs.guava-libraries.googlecode.com/git/javadoc/com/google/common
     *      /cache/LoadingCache.html
     */
    private LoadingCache<String, FirewallLimiter> cache;

    /**
     * Rate limitation period.
     */
    private int period;

    /**
     * Number of requests an authenticated user can do per period.
     */
    private int limitAuthenticated;

    /**
     * Number of requests an anonymous user can do per period.
     */
    private int limitAnonymous;

    /**
     * Return headers or not.
     */
    private boolean displayHeaders;

    public FirewallFilter(int limitAuthenticated, int limitAnonymous,
            int period, boolean displayHeaders, Restlet next) {
        this(limitAuthenticated, limitAnonymous, period, displayHeaders);
        this.setNext(next);
    }

    public FirewallFilter(int limitAuthenticated, int limitAnonymous,
            int period, boolean displayHeaders) {
        this.limitAuthenticated = limitAuthenticated;
        this.limitAnonymous = limitAnonymous;
        this.period = period;
        this.displayHeaders = displayHeaders;
        initialize();
    }

    private void initialize() {

        // Create cache loader. Associate a RateLimiter to the user.
        CacheLoader<String, FirewallLimiter> loader = new CacheLoader<String, FirewallLimiter>() {
            public FirewallLimiter load(String key) {
                if (Request.getCurrent().getClientInfo().getUser() != null) {
                    return FirewallLimiter.create(period, limitAuthenticated);
                } else {
                    return FirewallLimiter.create(period, limitAnonymous);
                }
            }
        };

        // [WORKAROUND] : if expireAfterWrite (period) ? do not need stopwatch
        // anymore in ratelimiter ?
        cache = CacheBuilder.newBuilder()
                .expireAfterAccess(period, TimeUnit.SECONDS).build(loader);
    }

    /**
     * Filters before processing by the next Restlet. Filters simultaneous call
     * (catches one call) & number of requests per period.
     */
    @Override
    protected int beforeHandle(Request request, Response response) {

        /*
         * Get username : identifier if authenticated, public IP if not.
         */

        String username;
        boolean isAuthenticated;

        if (request.getClientInfo().getUser() != null) {
            username = request.getClientInfo().getUser().getIdentifier();
            isAuthenticated = true;
        } else if (limitAnonymous != 0) {
            username = request.getClientInfo().getAddress();
            isAuthenticated = false;
        } else {
            // If limitAnonymous = 0 => return 403
            // => Anonymous users not allowed
            response.setStatus(Status.valueOf(403));
            return STOP;
        }

        FirewallLimiter firewallLimiter = cache.getUnchecked(username);

        /*
         * Check number of requests
         */

        if (displayHeaders) {
            setHeaders(response, firewallLimiter.getAttributes());
        }

        // Try acquire one request for number of requests.
        boolean isPermited = firewallLimiter.tryAcquire();
        if (isPermited) {
            return permited(isAuthenticated, request, response);
        } else {
            return notPermited(isAuthenticated, request,
                    response);
        }

    }

    /**
     * 
     * @param isAuthenticated
     * @return
     */
    protected int permited(boolean isAuthenticated, Request request,
            Response response) {
        return CONTINUE;
    }

    /**
     * 
     * @param isAuthenticated
     * @return
     */
    protected int notPermited(boolean isAuthenticated, Request request,
            Response response) {
        if (isAuthenticated) {
            response.setStatus(Status.valueOf(429), "Too many requests for "
                    + request.getClientInfo().getUser().getIdentifier()
                    + ": rate limitation.");
        } else {
            response.setStatus(Status.valueOf(429), "Too many requests for "
                    + request.getClientInfo().getAddress()
                    + ": rate limitation.");
        }
        return SKIP;
    }

    protected void setHeaders(Response response, Map<String, Object> attributes) {
        @SuppressWarnings("unchecked")
        Series<Header> headers = (Series<Header>) response.getAttributes().get(
                "org.restlet.http.headers");
        if (headers == null) {
            headers = new Series<Header>(Header.class);
        }
        headers.set("X-RateLimit-Remaining", attributes.get("remaining")
                .toString());
        headers.set("X-RateLimit-Limit", attributes.get("limit").toString());
        headers.set("X-RateLimit-Reset", attributes.get("reset").toString());
        response.getAttributes()
                .put(HeaderConstants.ATTRIBUTE_HEADERS, headers);
    }

    /*
     * Getters
     */

    /**
     * Returns the period (in seconds)
     * 
     * @return period duration in seconds
     */
    public int getPeriod() {
        return period;
    }

    /**
     * Returns the maximum number of requests allowed per period for
     * authenticated users.
     * 
     * @return maximum number of requests allowed per period for authenticated
     *         users
     */
    public int getLimitAuthenticated() {
        return limitAuthenticated;
    }

    /**
     * Returns the maximum number of requests allowed per period for anonymous
     * users.
     * 
     * @return maximum number of requests allowed per period for anonymous users
     */
    public int getLimitAnonymous() {
        return limitAnonymous;
    }

}