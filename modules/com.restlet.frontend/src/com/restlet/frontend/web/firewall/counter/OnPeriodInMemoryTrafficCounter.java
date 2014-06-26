package com.restlet.frontend.web.firewall.counter;

import com.restlet.frontend.web.firewall.counter.countingPolicy.CountingPolicy;
import com.restlet.frontend.web.firewall.counter.individualCounter.IndividualOnPeriodInMemoryTrafficCounter;
import com.restlet.frontend.web.firewall.counter.individualCounter.IndividualOnPeriodTrafficCounter;

public class OnPeriodInMemoryTrafficCounter extends OnPeriodTrafficCounter {

    public OnPeriodInMemoryTrafficCounter(int period,
            CountingPolicy countingPolicy) {
        super(period, countingPolicy);
    }

    @Override
    public IndividualOnPeriodTrafficCounter initializeIndividualTrafficCounter() {
        return new IndividualOnPeriodInMemoryTrafficCounter(getPeriod());
    }

}
