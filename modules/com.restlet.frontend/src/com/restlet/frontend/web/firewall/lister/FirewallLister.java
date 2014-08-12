package com.restlet.frontend.web.firewall.lister;

import java.util.List;

public abstract class FirewallLister {

    public abstract List<String> getList();

    public abstract boolean isListed(String ip);

    public abstract void addToList(String... ips);

}
