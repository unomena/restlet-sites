package com.restlet.frontend.web.firewall.counter;

public class CounterFeedback {

    private int consumed;
    
    private long elapsed;

    public int getConsumed() {
        return consumed;
    }

    public void setConsumed(int consumed) {
        this.consumed = consumed;
    }

    public long getElapsed() {
        return elapsed;
    }

    public void setElapsed(long elapsed) {
        this.elapsed = elapsed;
    }

}
