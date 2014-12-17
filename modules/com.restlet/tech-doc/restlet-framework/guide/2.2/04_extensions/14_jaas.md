# Introduction

This extension facilitate the integration between the Restlet security
API introduced in version 2.0 and the JAAS standard for authentication
and authorization.

A typical use case is the creation of a JAAS Subject including
principals for the authenticated Restlet user and its granted roles.
This is achieved by the JaasUtils\#createSubject(ClientInfo) method.

In addition, a JaasVerifier support the verification of user credentials
based on a JAAS pluggable authentication mechanism and more precisely
based on JAAS login modules.

For additional details, please consult the
[Javadocs](javadocs://jse/ext/org/restlet/ext/jaas/package-summary.html).

# Authenticating with LDAP

This extension can be used for LDAP authentication, for example.
Considering this JAAS configuration:

      BasicJaasAuthenticationApplication {
        com.sun.security.auth.module.LdapLoginModule REQUIRED

            userProvider="ldap://ldap.example.net/"

            authIdentity="uid={USERNAME},ou=people,dc=example,dc=net"

      };

Using a verifier configured like this:

      JaasVerifier verifier = new JaasVerifier("BasicJaasAuthenticationApplication");
      verifier.setConfiguration(jaasConfig);
      verifier.setUserPrincipalClassName("com.sun.security.auth.UserPrincipal");
      authenticator.setVerifier(verifier);

A successful JAAS login will add principals like these to the subject:

       [LdapLoginModule] added LdapPrincipal "uid=bruno,ou=people,dc=example,dc=net"

to Subject

       [LdapLoginModule] added UserPrincipal "bruno" to Subject

Thus, the resulting principals in ClientInfo are:

      LdapPrincipal with name: "uid=bruno,ou=people,dc=example,dc=net"
      UserPrincipal with name: "bruno"

A new user is created based on the first UserPrincipal name: 'bruno' in
this example.
