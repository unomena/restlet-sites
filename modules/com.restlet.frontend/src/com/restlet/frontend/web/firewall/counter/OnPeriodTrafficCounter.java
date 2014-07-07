package com.restlet.frontend.web.firewall.counter;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.restlet.frontend.web.firewall.counter.countingPolicy.CountingPolicy;
import com.restlet.frontend.web.firewall.counter.individualCounter.IndividualOnPeriodTrafficCounter;

public abstract class OnPeriodTrafficCounter extends TrafficCounter {

    private int period;

    private LoadingCache<String, IndividualOnPeriodTrafficCounter> cache;

    public OnPeriodTrafficCounter(int period, CountingPolicy countingPolicy) {
        super(countingPolicy);
        this.period = period;
        initializeCache();
    }

    private void initializeCache() {

        CacheLoader<String, IndividualOnPeriodTrafficCounter> loader = new CacheLoader<String, IndividualOnPeriodTrafficCounter>() {
            public IndividualOnPeriodTrafficCounter load(String key) {
                return initializeIndividualTrafficCounter();
            }
        };

        cache = CacheBuilder.newBuilder()
                .expireAfterAccess(2 * period, TimeUnit.SECONDS).build(loader);

    }
    
    public abstract IndividualOnPeriodTrafficCounter initializeIndividualTrafficCounter();

    @Override
    protected CounterFeedback increaseCounter(String counterValue) {
        IndividualOnPeriodTrafficCounter individualCounter = cache
                .getUnchecked(counterValue);
        return individualCounter.increase();
    }

    @Override
    protected void decreaseCounter(String counterValue) {

    }

    protected int getPeriod() {
        return this.period;
    }

}
