package com.restlet.frontend.web.firewall.counter;


public abstract class TrafficCounter {

    protected int consumed;

    public abstract int increase();

    public abstract void decrease();

}
