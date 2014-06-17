package com.restlet.frontend.web.firewall;

import java.util.concurrent.TimeUnit;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.Status;
import org.restlet.routing.Filter;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class SimultaneousCallsFilter extends Filter {

    /**
     * Cache used to match username (or public IP for anonymous users) to their
     * SpeedRateLimiter instance.
     * 
     * @see http 
     *      ://docs.guava-libraries.googlecode.com/git/javadoc/com/google/common
     *      /cache/LoadingCache.html
     */
    private LoadingCache<String, SimultaneousCallsLimiter> cache;

    /**
     * Number of simultaneous calls an authenticated user can do.
     */
    private int simultaneousCallsAuthenticated;

    /**
     * Number of simultaneous calls an anonymous user can do.
     */
    private int simultaneousCallsAnonymous;

    public SimultaneousCallsFilter(int simultaneousCallsAuthenticated,
            int simultaneousCallsAnonymous, Restlet next) {
        this(simultaneousCallsAuthenticated, simultaneousCallsAnonymous);
        setNext(next);
    }

    public SimultaneousCallsFilter(int simultaneousCallsAuthenticated,
            int simultaneousCallsAnonymous) {
        this.simultaneousCallsAuthenticated = simultaneousCallsAuthenticated;
        this.simultaneousCallsAnonymous = simultaneousCallsAnonymous;
        initialize();
    }

    private void initialize() {

        // Create cache loader. Associate a SimultaneousCallsLimiter to the
        // user.
        CacheLoader<String, SimultaneousCallsLimiter> loader = new CacheLoader<String, SimultaneousCallsLimiter>() {
            public SimultaneousCallsLimiter load(String key) {
                if (Request.getCurrent().getClientInfo().getUser() != null) {
                    return SimultaneousCallsLimiter
                            .create(simultaneousCallsAuthenticated);
                } else {
                    return SimultaneousCallsLimiter
                            .create(simultaneousCallsAnonymous);
                }
            }
        };

        cache = CacheBuilder.newBuilder()
                .expireAfterAccess(120, TimeUnit.SECONDS).build(loader);
    }

    @Override
    protected int beforeHandle(Request request, Response response) {

        /*
         * Get username : identifier if authenticated, public IP if not.
         */
        String username;
        if (request.getClientInfo().getUser() != null) {
            username = request.getClientInfo().getUser().getIdentifier();
        } else if (simultaneousCallsAnonymous != 0) {
            username = request.getClientInfo().getAddress();
        } else {
            // If limitAnonymous or simutaneousCallsAnonymous = 0 => return 403
            // => Anonymous users not allowed
            response.setStatus(Status.valueOf(403));
            return STOP;
        }

        SimultaneousCallsLimiter limiter = cache.getUnchecked(username);

        // Try acquire one call for number of simultaneous calls.
        boolean simultaneousPermission = limiter.get();
        if (!simultaneousPermission) {
            response.setStatus(Status.valueOf(429),
                    "Too many simultaneous calls");
            return STOP;
        }
        getLogger().info("Call acquired !");
        return CONTINUE;
    }

    protected void afterHandle(Request request, Response response) {

        /*
         * Get username : identifier if authenticated, public IP if not.
         */

        String username;
        if (request.getClientInfo().getUser() != null) {
            username = request.getClientInfo().getUser().getIdentifier();
        } else {
            username = request.getClientInfo().getAddress();
        }

        /*
         * Release one call (for simultaneous calls)
         */

        SimultaneousCallsLimiter limiter = cache.getUnchecked(username);
        limiter.release();
        getLogger().info("Call released !");
    }
}
