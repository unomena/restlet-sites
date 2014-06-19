package com.restlet.frontend.web.firewall.counter;

import java.util.concurrent.TimeUnit;

import com.google.common.base.Stopwatch;

public class UserPeriodCounter extends UserTrafficCounter {

    private int period;

    private Stopwatch stopwatch;

    public UserPeriodCounter(int period) {
        this.period = period;
        this.stopwatch = Stopwatch.createStarted();
    }

    @Override
    public synchronized CounterFeedback increase() {
        if (stopwatch.elapsed(TimeUnit.SECONDS) > period) {
            stopwatch.reset();
            stopwatch.start();
            consumed = 0;
        }
        CounterFeedback counterFeedback = new CounterFeedback();
        counterFeedback.setConsumed(consumed++);
        counterFeedback.setElapsed(stopwatch.elapsed(TimeUnit.SECONDS));
        return counterFeedback;
    }

    @Override
    public synchronized void decrease() {
    }

}
