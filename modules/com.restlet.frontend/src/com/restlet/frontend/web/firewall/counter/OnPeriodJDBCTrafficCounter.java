package com.restlet.frontend.web.firewall.counter;

import com.restlet.frontend.web.firewall.counter.countingpolicy.CountingPolicy;
import com.restlet.frontend.web.firewall.counter.individualcounter.IndividualOnPeriodTrafficCounter;

public class OnPeriodJDBCTrafficCounter extends OnPeriodTrafficCounter {

    public OnPeriodJDBCTrafficCounter(int period, CountingPolicy countingPolicy) {
        super(period, countingPolicy);
        // TODO Auto-generated constructor stub
    }

    @Override
    public IndividualOnPeriodTrafficCounter initializeIndividualTrafficCounter() {
        // TODO Auto-generated method stub
        return null;
    }

}
