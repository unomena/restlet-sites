package com.restlet.frontend.web.firewall.counter;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.restlet.Context;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.restlet.frontend.web.firewall.counter.countingPolicy.CountingPolicy;
import com.restlet.frontend.web.firewall.counter.individualCounter.IndividualConcurrentTrafficCounter;

public class ConcurrentTrafficCounter extends TrafficCounter {

    private LoadingCache<String, IndividualConcurrentTrafficCounter> cache;

    public ConcurrentTrafficCounter(CountingPolicy countingPolicy) {
        super(countingPolicy);
        initializeCache();
    }

    private void initializeCache() {

        CacheLoader<String, IndividualConcurrentTrafficCounter> loader = new CacheLoader<String, IndividualConcurrentTrafficCounter>() {
            public IndividualConcurrentTrafficCounter load(String key) {
                return new IndividualConcurrentTrafficCounter();
            }
        };

        cache = CacheBuilder.newBuilder()
                .expireAfterAccess(2, TimeUnit.MINUTES).build(loader);
    }

    @Override
    protected CounterFeedback increaseCounter(String counterValue) {
        IndividualConcurrentTrafficCounter counter = cache
                .getUnchecked(counterValue);
        return counter.increase();
    }

    @Override
    protected void decreaseCounter(String counterValue) {

        Context.getCurrentLogger().log(
                Level.FINE,
                "Counter " + this.getClass()
                + " decreased. User : " + counterValue);

        IndividualConcurrentTrafficCounter counter = cache
                .getUnchecked(counterValue);
        counter.decrease();
    }

}
