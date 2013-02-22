Securing applications
=====================

Introduction
============

As there are numerous protocols (like HTTP, SMTP, etc.) that we want to
support and as each one has various ways to authenticate requests (HTTP
Basic, HTTP Digest, SMTP Plain, etc.), the Restlet API provides a
flexible mechanism to support authentication.

Confidentiality
---------------

SSL is typically used to ensure that requests and responses are
exchanged confidentially between clients and a server. Configuring SSL
requires the setting of several parameters on the HTTPS server connector
instance used. You need to provide a key store containing the
certificate for your server instance.  See connectors' documentation for
more configuration details.

Note that SSL configuration is closely associated with an IP address and
a listening port. This may cause issue with the usage of virtual hosting
where several domains and applications share the same IP adress and
listening port. It is not possible to associate a SSL certificate per
virtual host because in order to determine a virtual host, we need to
have read and parsed the HTTP request headers to get the "Host" header.
You can only do that if you already use the certificate to read the
incoming SSL stream.

In this case, the only solution is to have two listening server sockets
(hence two IP addresses if you want to use the default HTTPS 443 port)
and then two Restlet server connectors configured, each one pointing to
a specific certificate. With some additional work, it is possible to use
the same key store to provide both certificate. This requires usage of
alias names and custom SSL context factories.

In addition to the parameters that are similar but specific to [each
type of HTTPS server
connector](http://wiki.restlet.org/docs_1.1/38-restlet.html),
it is possible to configure the SSL connectors using an
[SslContextFactory](http://restlet.org/learn/javadocs/1.1/nre/com/noelios/restlet/util/SslContextFactory.html),
in a way that is common to all three types of HTTPS server connectors
(Simple, Jetty and Grizzly). Configuring SSL is done in this order:

1.  An instance of SslContextFactory can be passed in the
    `sslContextFactory` *attribute* of the connector's context. This can
    be useful for cases that requires such an instance to be customized.
2.  When no `sslContextFactory` *attribute* is set, the full name of a
    concrete class extending SslContextFactory can be passed in the
    `sslContextFactory` *parameter* of the connector's context. Such a
    class *must*have a default constructor. The context's parameters are
    passed to its `init` method, so as to initialize the
    SslContextFactory instance via text parameters.\
     The
    [com.noelios.restlet.util.DefaultSslContextFactory](http://restlet.org/learn/javadocs/1.1/nre/com/noelios/restlet/util/DefaultSslContextFactory.html#init%28org.restlet.util.Series%29)
    is an SslContextFactory that supports a basic set of parameters, and
    will default to the values specified in the `javax.net.ssl.*` system
    properties (see [JSSE Reference
    guide](http://java.sun.com/j2se/1.5.0/docs/guide/security/jsse/JSSERefGuide.html#SystemProps)).\
     There can in fact be several values of sslContextFactory (since
    there can be several values for parameters), in which case the first
    one constructed and initialized successfully will be used.
3.  If no `sslContextFactory` *attribute* or *parameter* is set, the
    configuration will fall back to the parameters that are specific to
    each type of connector.

SSL client authentication is not configured as part of the SSL context,
although the trust store and which peer certificates to trust are.

Authentication
--------------

In Restlet, authentication and authorization are handled very
differently than in the Servlet world. Here you have full control of the
process, no external XML descriptor is necessary.

The default approach is to use the org.restlet.Guard class (a Restlet
filter) or a subclass of it. By default, this class uses a map to store
the login/password couples but this can be customized by overriding the
"authenticate(Request)" method.

From an org.restlet.data.Request, the login/password can be retrieved
using these methods:

 - Request.getChallengeResponse().getIdentifier() : String    // LOGIN\
  - Request.getChallengeResponse().getSecret() : String        //
PASSWORD

The HTTP server connectors currently only support HTTP BASIC
authentication (the most widely used).

### SSL client authentication

To enable client-side SSL authentication on an HTTPS server connector,
set the `wantClientAuthentication` or `needClientAuthentication`
parameters to `true` (in the first case, presenting a client certificate
will be optional). The chain of client certificate is then accessible as
List of X509Certificates in the `org.restlet.https.clientCertificates`
Request attribute.

Coarsed grained authorization
-----------------------------

For the authorizations that are common to a set of resources, a Guard
subclass can also be used by overriding the "authorize(Request)" method.
Note that this method accepts all authenticated requests by default. You
can plugin in your own mechanism here, like an access to a LDAP
repository.

Fine grained authorization
--------------------------

If the permissions are very fine grained, the authorizations should
probably handled at the resource level directly instead of the Guard
filter level (unless you want to put a filter in front of each
resource). With the approach, you can make your permission depend on the
value of the target resource on the resource and specific method
invoked, etc.

Actors
======

Restlet API
-----------

### ChallengeRequest

Contains information about the authentication challenge that is sent by
an origin server to a client.

For server-side Restlet applications, this object will typically be
created and set on the current Response by a Guard added to the request
processing chain. Then, when the response goes back to the Restlet
engine, the server connector and the matching authentication helper will
format it into the proper protocol artifact such as a "WWW-Authenticate"
header in HTTP.

For client-side Restlet applications, this object will typically be
received in response to a request to a target resource requiring
authentication. In HTTP, this is signalled by a 401 (Unauthorized)
status. This means that a new request must be sent to the server with a
valid challenge response set for the required challenge scheme. The
client connector and matching AuthenticationHelper are responsible for
parsing the protocol artifact such as the "WWW-Authenticate" header in
HTTP.

### ChallengeResponse

Contains information about the authentication challenge response sent by
a client to an origin server.

For server-side Restlet applications, this object will typically be
created by the server connector with the help from the matching
AuthenticationHelper for parsing.

For client-side Restlet applications, this object must be manually
created before invoking the context's client dispatcher for example.

### ChallengeScheme

Indicates the challenge scheme used to authenticate remote clients. This
only identifies the scheme used or to be used but doesn't contain the
actual logic needed to handle the scheme. This is the role of the
AuthenticatorHelper subclasses.

### Guard

This is a Restlet Filter that guards the Restlets or Resources attached
to it from unauthenticated and/or unauthorized requests.

When a request reaches it, it first attempts to authenticate it, i.e. to
make sure that the challenge scheme used is supported and that the
credentials given by the client (such as a login and password) are
valid. The actual implementation of the authentication is delegated to
the matching authentication helper. The result of this authentication
can be:

Valid: the authentication credentials are valid, the right scheme was
used and the credentials could be verified by calling back the
checkSecret() method on Guard. Here are the next steps

1.  The authorize() method is called and if authorization is given the
    accept() method is invoked, which delegates to the attached Restlet
    or Resource by default. Otherwise, the forbid method is called,
    which sets the response status to CLIENT\_ERROR\_FORBIDDEN (403).

Missing: no credentials could be found, the challenge() method is
invoked which delegates to the matching authenticator helper.

Invalid: bad credentials were given such as a wrong password or
unsupported scheme was used. If the "rechallenge" property is true, the
challenge() method is invoked otherwise, the forbid() method is invoked.

Stale: the credentials expired and must be renew. Therefore, the
challenge() method is invoked.

Noelios Restlet Engine
----------------------

Most of the logic related to authentication is located in the package
"com.noelios.restlet.authentication".

### AuthenticationUtils

Static utilities methods to parse HTTP headers, find the matching
authentication helper and misc methods.

### AuthenticationHelper

Base class for authentication helpers.

There are also subclasses for the schemes internally supported by the
NRE :  HTTP Basic, HTTP Digest, HTTP Amazon S3 (client) and SMTP Plain.

Extensions
----------

In addition to the internal authentication helpers, additional schemes
can be supported using pluggable extensions. Currently, there is an
extension available for the HTTP OAuth scheme.

