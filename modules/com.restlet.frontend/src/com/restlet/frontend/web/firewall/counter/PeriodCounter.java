package com.restlet.frontend.web.firewall.counter;

import java.util.concurrent.TimeUnit;

import com.google.common.base.Stopwatch;

public class PeriodCounter extends TrafficCounter {

    private int period;

    private Stopwatch stopwatch;

    public PeriodCounter(int period) {
        this.period = period;
        this.stopwatch = Stopwatch.createStarted();
    }

    @Override
    public synchronized int increase() {
        if (stopwatch.elapsed(TimeUnit.SECONDS) > period) {
            stopwatch.reset();
            stopwatch.start();
            consumed = 0;
        }
        return consumed++;
    }

    @Override
    public synchronized void decrease() {
    }

}
