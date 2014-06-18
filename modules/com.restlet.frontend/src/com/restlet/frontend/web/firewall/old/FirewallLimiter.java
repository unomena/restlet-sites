package com.restlet.frontend.web.firewall.old;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Stopwatch;

/**
 * Implement the following rate limitation features : speed rate limit
 * (requests) + number of simultaneous calls.
 * 
 * @author Guillaume Blondeau
 * 
 */
public class FirewallLimiter {

    /**
     * Period time in seconds
     */
    private int period;

    /**
     * Maximum number of calls allowed per window
     */
    private int maxCalls;

    /**
     * Number of calls done for this window
     */
    private int used;

    /**
     * Stopwatch which calculates time elapsed for windows
     * 
     * @see http
     *      ://docs.guava-libraries.googlecode.com/git/javadoc/com/google/common
     *      /base/Stopwatch.html
     */
    private Stopwatch stopwatch;

    private FirewallLimiter(int period, int maxCalls) {
        this.period = period;
        this.maxCalls = maxCalls;
        this.stopwatch = Stopwatch.createStarted();
    }

    /**
     * Called to increment number of calls
     * 
     * @return true : if allowed (increment calls done)
     * @return false : if not allowed (maximum number of calls for current
     *         window exceeded)
     */
    public synchronized boolean tryAcquire() {
        if (stopwatch.elapsed(TimeUnit.SECONDS) > period) {
            stopwatch.reset();
            stopwatch.start();
            used = 0;
        }
        if (used >= maxCalls)
            return false;
        used++;
        return true;
    }

    /**
     * Returns the following attributes in a map :
     * 
     * reset : timestamp in UTC epoch second of reset time
     * 
     * limit : maximum number of calls per window
     * 
     * remaining : remaining number of calls for current window
     * 
     * @return attributes
     */
    public Map<String, Object> getAttributes() {
        Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("reset",
                (System.currentTimeMillis() / 1000L + period - stopwatch
                        .elapsed(TimeUnit.SECONDS)));
        attributes.put("limit", maxCalls);
        attributes.put("remaining", maxCalls - used);
        return attributes;
    }

    /**
     * Factory for SpeedRateLimiter
     * 
     * @param period
     *            : in seconds
     * @param maxCalls
     *            : number of calls allowed per window
     * @param simultaneousCallsLimit
     *            : number of simultaneous calls allowed
     * @return created rate limiter
     */
    public static FirewallLimiter create(int period, int maxCalls) {
        return new FirewallLimiter(period, maxCalls);
    }

}
