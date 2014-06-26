package com.restlet.frontend.web.firewall.counter;

import com.restlet.frontend.web.firewall.counter.countingpolicy.CountingPolicy;
import com.restlet.frontend.web.firewall.counter.individualcounter.IndividualOnPeriodInMemoryTrafficCounter;
import com.restlet.frontend.web.firewall.counter.individualcounter.IndividualOnPeriodTrafficCounter;

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
