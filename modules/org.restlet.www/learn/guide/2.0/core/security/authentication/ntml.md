NTLM authentication
===================

Using the "net" extension
=========================

At this time, the access to services secured with NTML is supported via
the ["java.net"
extension](http://wiki.restlet.org/docs_2.0/13-restlet/28-restlet/79-restlet.html "Net extension")
based on the support of NTML authenticated introduced in the JDK5. So,
add this connector to your application as explained in the
["connectors"](http://wiki.restlet.org/docs_2.0/13-restlet/27-restlet/325-restlet/37-restlet.html "Connectors")
document.\
 The required step is to setup your custom Authenticator instance wich
will be referenced each the client connector will issue a request.
According to the JDK5 javadocs, just proceed as follow:

    // Create your own authenticator
    Authenticator a = new Authenticator() {
        public PasswordAuthentication getPasswordAuthentication() {
            return (new PasswordAuthentication("<your account>", "<your password>".toCharArray()));
        }
    };
    // Sets the default Authenticator
    Authenticator.setDefault(a);

##### Authenticate with NTML all requests performed via the HTTP client connector provided by the "net" extension.

No additional steps are required so far.

This does not apply to the Android extension. In this case, the support
of NTLM authentication leverages the Apache "HttpClient" extension.

Using the Apache "HttpClient" extension
=======================================

The "HttpClient" library does not provide a direct support of the the
NTLM authentication scheme (see
[here](http://hc.apache.org/httpcomponents-client/ntlm.html)).
However, it explains how to leverage the Samba JCIFS library as an NTML
Engine.

Basically, the following steps are required:

-   Add the the "HttpClient" connector to you project.
-   Add the [JCIFS
    archive](http://jcifs.samba.org/)
    to your project.
-   Set up a dedicated client helper that will handle the NTML requests
    using the Apache HttpClient and JCIFS libraries

Here is [a sample
implementation](http://wiki.restlet.org/docs_2.0/363-restlet/version/default/part/AttachmentData/data/MyNtlmHttpClientHelper.java "MyNtlmHttpClientHelper")
(text/x-java, 2.6 kB,
[info](http://wiki.restlet.org/docs_2.0/363-restlet.html))
of such client helper. It extends the one provided by the ["HttpClient"
extension](http://wiki.restlet.org/docs_2.0/13-restlet/28-restlet/75-restlet.html "Apache HTTP Client extension")
that must be added to your project.

-   Make the Engine register this client helper:

Here is a sample code that manually adds this client helper:

    Engine.getInstance().getRegisteredClients().add(0, new MyNtlmHttpClientHelper(new Client(Protocol.HTTP)));

