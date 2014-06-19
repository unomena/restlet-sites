package com.restlet.frontend.web.firewall.counter;


public abstract class UserTrafficCounter {

    protected int consumed;

    public abstract CounterFeedback increase();

    public abstract void decrease();

}
