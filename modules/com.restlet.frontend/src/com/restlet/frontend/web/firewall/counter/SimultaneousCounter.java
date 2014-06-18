package com.restlet.frontend.web.firewall.counter;

public class SimultaneousCounter extends TrafficCounter {

    @Override
    public synchronized int increase() {
        System.out.println("Simultaneous : " + (consumed + 1));
        return consumed++;
    }

    @Override
    public synchronized void decrease() {
        consumed--;
        System.out.println("Simultaneous : " + consumed);
    }

}
