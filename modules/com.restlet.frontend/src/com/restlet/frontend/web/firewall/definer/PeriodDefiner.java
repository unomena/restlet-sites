package com.restlet.frontend.web.firewall.definer;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.restlet.frontend.web.firewall.counter.PeriodCounter;

public class PeriodDefiner extends TimeDefiner {

    private int period;

    public PeriodDefiner(int period) {
        super();
        this.period = period;
        initializeCache();
    }

    private void initializeCache() {

        CacheLoader<String, PeriodCounter> loader = new CacheLoader<String, PeriodCounter>() {
            public PeriodCounter load(String key) {
                return new PeriodCounter(period);
            }
        };

        cache = CacheBuilder.newBuilder()
                .expireAfterAccess(2 * period, TimeUnit.SECONDS).build(loader);

    }

}
