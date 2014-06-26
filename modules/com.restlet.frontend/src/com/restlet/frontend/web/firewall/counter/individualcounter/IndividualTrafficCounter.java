package com.restlet.frontend.web.firewall.counter.individualcounter;

import com.restlet.frontend.web.firewall.old.counter.CounterFeedback;

public abstract class IndividualTrafficCounter {

    protected int consumed;

    public abstract CounterFeedback increase();

    public abstract void decrease();

}
