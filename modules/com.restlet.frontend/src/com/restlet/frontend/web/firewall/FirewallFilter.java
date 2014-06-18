package com.restlet.frontend.web.firewall;

import java.util.ArrayList;
import java.util.List;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.routing.Filter;

import com.restlet.frontend.web.firewall.definer.TimeDefiner;

public class FirewallFilter extends Filter {

    private List<TimeDefiner> timeDefiners;

    public FirewallFilter(Restlet next) {
        this.setNext(next);
    }

    public FirewallFilter() {

    }

    @Override
    protected int beforeHandle(Request request, Response response) {

        int responseCode = Filter.CONTINUE;
        for (TimeDefiner timeDefiner : timeDefiners) {
            int code = timeDefiner.beforeHandle(request, response);
            if (code > responseCode) {
                responseCode = code;
            }
        }
        return responseCode;
    }

    @Override
    protected void afterHandle(Request request, Response response) {
        for (TimeDefiner timeDefiner : timeDefiners) {
            timeDefiner.afterHandle(request, response);
        }
    }

    public void addDefiner(TimeDefiner definer) {

        if (timeDefiners == null) {
            timeDefiners = new ArrayList<TimeDefiner>();
        }
        timeDefiners.add(definer);
    }
}
