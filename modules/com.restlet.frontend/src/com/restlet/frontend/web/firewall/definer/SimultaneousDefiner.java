package com.restlet.frontend.web.firewall.definer;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.restlet.frontend.web.firewall.counter.SimultaneousCounter;

public class SimultaneousDefiner extends TimeDefiner {

    public SimultaneousDefiner() {
        initializeCache();
    }

    private void initializeCache() {

        CacheLoader<String, SimultaneousCounter> loader = new CacheLoader<String, SimultaneousCounter>() {
            public SimultaneousCounter load(String key) {
                return new SimultaneousCounter();
            }
        };

        cache = CacheBuilder.newBuilder()
                .expireAfterAccess(10, TimeUnit.SECONDS).build(loader);

    }

}
