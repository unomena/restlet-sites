package com.restlet.frontend.web.firewall.counter;


public class UserSimultaneousCounter extends UserTrafficCounter {

    @Override
    public synchronized CounterFeedback increase() {
        CounterFeedback counterFeedback = new CounterFeedback();
        counterFeedback.setConsumed(consumed++);
        return counterFeedback;
    }

    @Override
    public synchronized void decrease() {
        consumed--;
    }

}
