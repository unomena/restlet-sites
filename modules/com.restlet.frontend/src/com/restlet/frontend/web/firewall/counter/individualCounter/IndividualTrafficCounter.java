package com.restlet.frontend.web.firewall.counter.individualCounter;

import com.restlet.frontend.web.firewall.counter.CounterFeedback;

public abstract class IndividualTrafficCounter {

    protected int consumed;

    public abstract CounterFeedback increase();

    public abstract void decrease();

}
