package com.restlet.frontend.web.firewall.lister;

import java.util.ArrayList;
import java.util.List;

public class InMemoryLister extends FirewallLister {

    private List<String> list;

    public InMemoryLister() {
        list = new ArrayList<String>();
    }

    @Override
    public List<String> getList() {
        return list;
    }

    @Override
    public void addToList(String... ips) {
        for (String ip : ips) {
            if (!list.contains(ip)) {
                list.add(ip);
            }
        }
    }

    @Override
    public boolean isListed(String ip) {
        return list.contains(ip);
    }

}
