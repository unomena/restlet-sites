package com.restlet.frontend.web.firewall.old;

public class SimultaneousCallsLimiter {

    /**
     * Number of current simultaneous calls
     */
    private int simultaneousCalls;

    /**
     * Maximum number of simultaneous calls
     */
    private int simultaneousCallsLimit;

    private SimultaneousCallsLimiter(int simultaneousCallsLimit) {
        this.simultaneousCalls = 0;
        this.simultaneousCallsLimit = simultaneousCallsLimit;
    }

    /**
     * Used to calculate number of simultaneous calls. Try to acquire one call.
     * 
     * @return true if allowed (increment simultaneous calls)
     * @return false if not allowed (maximum number of simultaneous calls
     *         exceeded)
     */
    public synchronized boolean get() {
        if (simultaneousCalls >= simultaneousCallsLimit)
            return false;
        simultaneousCalls++;
        return true;
    }

    /**
     * Used to calculate number of simultaneous calls. Release one call.
     */
    public synchronized void release() {
        if (simultaneousCalls > 0)
            simultaneousCalls--;
    }

    /**
     * Factory for SpeedRateLimiter
     * 
     * @param window
     *            : in seconds
     * @param maxCalls
     *            : number of calls allowed per window
     * @param simultaneousCallsLimit
     *            : number of simultaneous calls allowed
     * @return created rate limiter
     */
    public static SimultaneousCallsLimiter create(int simultaneousCallsLimit) {
        return new SimultaneousCallsLimiter(simultaneousCallsLimit);
    }

}
