package com.restlet.frontend.web.firewall.lister;

import java.util.List;

public class InMemoryLister extends FirewallLister {

    private List<String> list;

    @Override
    public List<String> getList() {
        return list;
    }

    @Override
    public void addToList(String ip) {
        if (!list.contains(ip)) {
            list.add(ip);
        }
    }

}
