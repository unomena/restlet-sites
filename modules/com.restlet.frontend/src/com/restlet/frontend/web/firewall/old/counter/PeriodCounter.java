package com.restlet.frontend.web.firewall.old.counter;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;

public class PeriodCounter extends TrafficCounter {

    private int period;

    public PeriodCounter(int period) {
        super();
        this.period = period;
        initializeCache();
    }

    private void initializeCache() {

        CacheLoader<String, UserPeriodCounter> loader = new CacheLoader<String, UserPeriodCounter>() {
            public UserPeriodCounter load(String key) {
                return new UserPeriodCounter(period);
            }
        };

        cache = CacheBuilder.newBuilder()
                .expireAfterAccess(2 * period, TimeUnit.SECONDS).build(loader);

    }

}
