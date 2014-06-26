package com.restlet.frontend.web.firewall.old.counter;


public abstract class UserTrafficCounter {

    protected int consumed;

    public abstract CounterFeedback increase();

    public abstract void decrease();

}
