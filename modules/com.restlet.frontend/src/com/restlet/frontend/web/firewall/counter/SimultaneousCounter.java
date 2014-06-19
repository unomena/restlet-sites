package com.restlet.frontend.web.firewall.counter;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;

public class SimultaneousCounter extends TrafficCounter {

    public SimultaneousCounter() {
        initializeCache();
    }

    private void initializeCache() {

        CacheLoader<String, UserSimultaneousCounter> loader = new CacheLoader<String, UserSimultaneousCounter>() {
            public UserSimultaneousCounter load(String key) {
                return new UserSimultaneousCounter();
            }
        };

        cache = CacheBuilder.newBuilder()
                .expireAfterAccess(10, TimeUnit.SECONDS).build(loader);

    }

}
