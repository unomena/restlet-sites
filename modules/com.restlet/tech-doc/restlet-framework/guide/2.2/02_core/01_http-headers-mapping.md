# Introduction

The Restlet API offers a higher level view of the HTTP protocol. It
tries to abstract and present in a clean object model, the
application-level semantics of HTTP. As a side feature, it is possible
to map other protocols to the same semantics, such as FILE, FTP, SMTP,
etc.

However, developers often know lower-level details of HTTP or need to
understand them for debugging purpose. This is the reason for this
document, explaining the mapping between the HTTP semantics and the
Restlet API.

Note that the headers that are not supported yet can still be overridden
via the "org.restlet.http.headers" attribute of the request or the
response. A warning message is  logged : "Addition of the standard
header "XXX" is discouraged. Future versions of the Restlet API will
directly support it".

See details in the Javadocs of the
[Message.getAttributes()](javadocs://jse/api/org/restlet/Message.html#getAttributes%28%29)
method.

# From HTTP headers to Restlet API

 HTTP header                                                                             Restlet property name                     Restlet property class                      Description
 --------------------------------------------------------------------------------------- ----------------------------------------- ------------------------------------------- ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
 [Accept](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.1)                request.clientInfo.acceptedMediaTypes     org.restlet.data.Preference\<MediaType\>    The list of media-types accepted by the client. 
 [Accept-Charset](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.2)        request.clientInfo.acceptedCharacterSets  org.restlet.data.Preference\<CharacterSet\> The list of character sets accepted by the client.
 [Accept-Encoding](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.3)       request.clientInfo.acceptedEncodings      org.restlet.data.Preference\<Encoding\>     The list of encodings accepted by the client.
 [Accept-Language](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.4)       request.clientInfo.acceptedLanguages      org.restlet.data.Preference\<Language\>     The list of languages accepted by the client.
 [Accept-Ranges](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.5)         response.serverInfo.acceptRanges          boolean                                     Allows the server to indicate its support for range requests
 [Age](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.6)                   response.age                              int                                         The estimated amount of time since the response was generated or revalidated by the origin server.
 [Allow](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.7)                 response.allowedMethods                   Set\<org.restlet.data.Method\>              For "Handler" subclasses (such as Resource), a dynamic search is made that looks for the "allow\*" methods declared on the class and that return "True".
 [Authentication-Info](http://tools.ietf.org/html/rfc2617#section-3.2.3)                 response.authenticationInfo               org.restlet.data.AuthenticationInfo         Authentication information sent by an origin server to a client after a successful authentication attempt.
 [Authorization](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.8)         request.challengeResponse                 org.restlet.data.ChallengeResponse          Credentials that contain the authentication information of the user agent for the realm of the resource being requested.
 [Cache-Control](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.9)         message.cacheDirectives                   List\<org.restlet.data.CacheDirective\>     List of directives that must be obeyed by all caching mechanisms along the request/response chain.
 [Connection](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.10)           [HTTP connectors]                         -                                           Set to "close" according to the server or client connector property (serverKeepAlive or clientKeepAlive, keepAlive). The internal client and server connectors introduce the "persistingConnections" parameter.
 [Content-Disposition](http://www.w3.org/Protocols/rfc2616/rfc2616-sec19.html#sec19.5.1) message.entity.disposition                org.restlet.data.Disposition                Means for the origin server to suggest a default filename if the user requests that the content is saved to a file.
 [Content-Encoding](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.11)     message.entity.encodings                  List\<org.restlet.data.Encoding\>           Indicates what additional content codings have been applied to the entity-body.
 [Content-Language](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.12)     message.entity.languages                  List\<org.restlet.data.Language\>           Describes the natural language(s) of the intended audience for the enclosed entity.
 [Content-Length](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.13)       message.entity.size                       long                                        The size of the entity-body, in decimal number of OCTETs.
 [Content-Location](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.14)     message.entity.locationRef                org.restlet.data.Reference                  Indicates the resource location for the entity enclosed in the message.
 [Content-MD5](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.15)          message.entity.digest                     org.restlet.data.Digest                     Value and algorithm name of the digest associated to a representation.
 [Content-Range](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.15)        message.entity.range                      org.restlet.data.Range                      Indicates where in the full entity-body the partial body should be applied.
 [Content-Type](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.17)         message.entity.mediaType and characterSet org.restlet.data.MediaType + CharacterSet   Indicates the media type of the entity-body.
 [Cookie](http://www.w3.org/Protocols/rfc2109/rfc2109)                                   request.cookies                           Series\<org.restlet.data.Cookie\>           List of one or more cookies sent by the client to the server.
 [Date](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.18)                 message.date                              Date                                        The date and time at which the message was originated.
 [ETag](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.19)                 message.entity.tag                        org.restlet.data.Tag                        The current value of the entity tag for the requested variant.
 [Expect](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.20)               request.clientInfo.expectations           List\<org.restlet.data.Expectation\>        Indicates that particular server behaviors are required by the client.
 [Expires](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.21)              message.entity.expirationDate             Date                                        The date/time after which the response is considered stale.
 [From](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.22)                 request.clientInfo.from                   String                                      The email address of the human user controlling the user agent.
 [Host](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.23)                 request.hostRef                           Reference                                   Specifies the Internet host and port number of the resource being requested.
 [If-Match](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.24)             request.conditions.match                  List\<org.restlet.data.Tag\>                Used with a method to make it conditional.
 [If-Modified-Since](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.25)    request.conditions.modifiedSince          Date                                        Used with a method to make it conditional.
 [If-None-Match](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.26)        request.conditions.noneMatch              List\<org.restlet.data.Tag\>                Used with a method to make it conditional.
 [If-Range](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.27)             request.conditions.rangeTag and rangeDate org.restlet.data.Tag + Date                 Used to conditionally return a part or the entire resource representation.
 [If-Unmodified-Since](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.28)  request.conditions.unmodifiedSince        Date                                        Used with a method to make it conditional.
 [Last-Modified](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.1)         message.entity.modificationDate           Date                                        Indicates the date and time at which the origin server believes the variant was last modified.
 [Location](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.30)             response.locationRef                      org.restlet.data.Reference                  Used to redirect the recipient to a location other than the Request-URI for completion of the request or identification of a new resource.
 [Max-Forwards](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.31)         request.maxForwards                       int                                         Maximum number of proxies or gateways that can forward the request to the next inbound server.
 [Pragma](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.32)               [Deprecated]                              -                                           [Support for misc headers planned for later](http://restlet.tigris.org/issues/show_bug.cgi?id=282)
 [Proxy-Authenticate](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.33)   response.proxyChallengeRequests           List\<org.restlet.data.ChallengeRequest\>   Indicates the authentication scheme(s) and parameters applicable to the proxy.
 [Proxy-Authorization](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.34)  request.proxyChallengeResponse            org.restlet.data.ChallengeResponse          Credentials that contain the authentication information of the user agent for the proxy.
 [Range](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.35)                request.ranges                            List\<org.restlet.data.Range\>              List one or more ranges to return from the entity.
 [Referer](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.36)              request.refererRef                        Reference                                   The address (URI) of the resource from which the Request-URI was obtained.
 [Retry-After](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.37)          response.retryAfter                       Date                                        Indicates how long the service is expected to be unavailable to the requesting client.
 [Server](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.38)               response.serverInfo.agent                 String                                      Information about the software used by the origin server to handle the request.
 [Set-Cookie](http://www.w3.org/Protocols/rfc2109/rfc2109)                               response.cookieSettings                   Series\<org.restlet.data.CookieSetting\>    List of one or more cookies sent by the server to the client.
 [Set-Cookie2](http://www.ietf.org/rfc/rfc2965.txt)                                      response.cookieSettings                   Series\<org.restlet.data.CookieSetting\>    List of one or more cookies sent by the server to the client.
 [TE](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.39)                   -                                         -                                           [Support for misc headers planned for later](http://restlet.tigris.org/issues/show_bug.cgi?id=282)
 [Trailer](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.40)              -                                         -                                           [Support for misc headers planned for later](http://restlet.tigris.org/issues/show_bug.cgi?id=282)
 [Transfer-Encoding](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.41)    [HTTP connectors]                         -                                           Valuated to "chunked" if the entity is not null and its size is unknown (Representation.UNKNOWN\_SIZE).
 [Upgrade](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.42)              -                                         -                                           [Support for misc headers planned for later](http://restlet.tigris.org/issues/show_bug.cgi?id=282)
 [User-Agent](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.43)           request.clientInfo.agent                  String                                      Information about the user agent originating the request.
 [Vary](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.44)                 response.dimensions                       Set\<org.restlet.data.Dimension\>           Indicates the set of request-header fields that fully determines, while the response is fresh, whether a cache is permitted to use the response to reply to a subsequent request without revalidation.
 [Via](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.45)                  message.recipientsInfo                    org.restlet.data.RecipientInfo              Used by gateways and proxies to indicate the intermediate protocols and recipients between the user agent and the server on requests, and between the origin server and the client on responses
 [Warning](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.46)              message.warnings                          List\<org.restlet.data.Warning\>            The additional warnings information.
 [WWW-Authenticate](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.47)     response.challengeRequests                List\<org.restlet.data.ChallengeRequest\>   Indicates the authentication scheme(s) and parameters applicable to the Request-URI.
 X-Forwarded-For                                                                         request.clientInfo.forwardedAddresses     List\<String\>                              The list of client IP addresses, including intermediary proxies.
 X-HTTP-Method-Override                                                                  [Tunnel service]                          org.restlet.data.Method                     Allows to override the HTTP method specified in the request (typically by a limited client such as a browser) by one specified in this special extension header.

# Appendix

[Registry](http://www.iana.org/assignments/message-headers/perm-headers.html) [of headers](http://www.iana.org/assignments/message-headers/perm-headers.html) maintained by IANA.
[Regsitry of HTTP status codes](http://www.iana.org/assignments/http-status-codes) maintained by IANA.
