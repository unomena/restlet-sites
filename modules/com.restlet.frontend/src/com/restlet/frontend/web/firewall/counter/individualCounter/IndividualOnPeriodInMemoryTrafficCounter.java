package com.restlet.frontend.web.firewall.counter.individualCounter;

import java.util.concurrent.TimeUnit;

import com.google.common.base.Stopwatch;
import com.restlet.frontend.web.firewall.counter.CounterFeedback;

public class IndividualOnPeriodInMemoryTrafficCounter extends
        IndividualOnPeriodTrafficCounter {

    private Stopwatch stopwatch;

    public IndividualOnPeriodInMemoryTrafficCounter(int period) {
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
        consumed++;
        CounterFeedback counterFeedback = new CounterFeedback();
        counterFeedback.setConsumed(consumed);
        counterFeedback.setElapsed(stopwatch.elapsed(TimeUnit.SECONDS));
        counterFeedback.setReset(System.currentTimeMillis() / 1000L + period
                - stopwatch.elapsed(TimeUnit.SECONDS));
        return counterFeedback;
    }

    @Override
    public synchronized void decrease() {
    }

}
