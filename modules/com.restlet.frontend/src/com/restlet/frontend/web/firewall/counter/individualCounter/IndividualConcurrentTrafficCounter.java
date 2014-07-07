package com.restlet.frontend.web.firewall.counter.individualCounter;

import com.restlet.frontend.web.firewall.counter.CounterFeedback;

public class IndividualConcurrentTrafficCounter extends
        IndividualTrafficCounter {

    @Override
    public synchronized CounterFeedback increase() {
        consumed++;
        CounterFeedback counterFeedback = new CounterFeedback();
        counterFeedback.setConsumed(consumed);
        return counterFeedback;
    }

    @Override
    public synchronized void decrease() {
        consumed--;
    }

}
