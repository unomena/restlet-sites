package com.restlet.frontend.web.firewall.lister;

import java.util.List;

public abstract class FirewallLister {

    private boolean blacklist;

    public abstract List<String> getList();

    public abstract void addToList(String ip);

    public boolean isBlacklist() {
        return blacklist;
    }

    public void setBlacklist(boolean blacklist) {
        this.blacklist = blacklist;
    }

}
