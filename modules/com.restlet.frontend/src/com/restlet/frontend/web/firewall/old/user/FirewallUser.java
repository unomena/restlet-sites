package com.restlet.frontend.web.firewall.old.user;

import org.restlet.Request;
import org.restlet.security.User;

import com.restlet.frontend.web.firewall.old.type.UserType;

public class FirewallUser {

    private UserType type;

    private String identifier;

    private FirewallUser(UserType type, String identifier) {
        super();
        this.type = type;
        this.identifier = identifier;
    }

    public static FirewallUser getUser(Request request) {

        User user = request.getClientInfo().getUser();

        if (user != null) {
            return new FirewallUser(UserType.AUTHENTICATED, request
                    .getClientInfo().getUser().getIdentifier());
        } else {
            return new FirewallUser(UserType.ANONYMOUS, request.getClientInfo()
                    .getAddress());
        }

    }

    public UserType getType() {
        return type;
    }

    public String getIdentifier() {
        return identifier;
    }

}
