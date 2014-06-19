package com.restlet.frontend.web.firewall.type;

public enum UserType implements FirewallType {

    AUTHENTICATED(1), ANONYMOUS(2);

    private final int number;

    UserType(int number) {
        this.number = number;
    }

    @Override
    public int getNumber() {
        return this.number;
    }

    @Override
    public boolean equals(FirewallType firewallType) {
        if (this.getNumber() == 0 || firewallType.getNumber() == 0
                || (this.getNumber() == firewallType.getNumber())) {
            return true;
        }
        return false;
    }

}
