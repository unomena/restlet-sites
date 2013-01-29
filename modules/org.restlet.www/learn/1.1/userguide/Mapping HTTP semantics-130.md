Mapping HTTP semantics
======================

Introduction
============

The Restlet API offers a higher level view of the HTTP protocol. It
tries to abstract and present in a clean object model, the
application-level semantics of HTTP. As a side feature, it is possible
to map other protocols to the same semantics, such as FILE, FTP, SMTP,
etc.

However, developers often know lower-level details of HTTP or need to
understand them for debugging purpose. This is the reason for this
document, explain the mapping between the HTTP semantics and the Restlet
API.

Note that the headers that are not supported yet can still be overridden
via the "org.restlet.http.headers" attribute of the request or the
response. A warning message is  logged : "Addition of the standard
header "XXX" is discouraged. Future versions of the Restlet API will
directly support it".

See details in the Javadocs of the
[Message.getAttributes()](http://web.archive.org/web/20120104223220/http://www.restlet.org/documentation/1.1/api/org/restlet/data/Message.html#getAttributes%28%29)
method.

Mapping headers
===============

HTTP header

Restlet property

Description

[Accept](http://web.archive.org/web/20120104223220/http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.1)

request.clientInfo.acceptedMediaTypes

The list of media-types accepted by the client. 

[Accept-Charset](http://web.archive.org/web/20120104223220/http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.2)

request.clientInfo.acceptedCharacterSets

The list of character sets accepted by the client.

[Accept-Encoding](http://web.archive.org/web/20120104223220/http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.3)

request.clientInfo.acceptedEncodings

The list of encodings accepted by the client.

[Accept-Language](http://web.archive.org/web/20120104223220/http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.4)

request.clientInfo.acceptedLanguages

The list of languages accepted by the client.

[Accept-Ranges](http://web.archive.org/web/20120104223220/http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.5)

[connector level]

Allows the server to indicate its support for range requests

[Age](http://web.archive.org/web/20120104223220/http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.6)

-

[Support for caching planned for Restlet
1.2](http://web.archive.org/web/20120104223220/http://restlet.tigris.org/issues/show_bug.cgi?id=213)

[Allow](http://web.archive.org/web/20120104223220/http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.7)

response.allowedMethods

For "Handler" subclasses (such as Resource), a dynamic search is made
that looks for the "allow\*" methods declared on the class and that
return "True".

[Authentication-Info](http://web.archive.org/web/20120104223220/http://rfc.net/rfc2617.html#s3.2.3)

-

Non standard HTTP header.

[Authorization](http://web.archive.org/web/20120104223220/http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.8)

request.challengeResponse

Credentials that contain the authentication information of the user
agent for the realm of the resource being requested.

[Cache-Control](http://web.archive.org/web/20120104223220/http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.9)

-

[Support for caching planned for Restlet
1.2](http://web.archive.org/web/20120104223220/http://restlet.tigris.org/issues/show_bug.cgi?id=213)

[Connection](http://web.archive.org/web/20120104223220/http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.10)

[connector level]

Set to "close" according to the server or client connector property
(serverKeepAlive or clientKeepAlive).

[Content-Disposition](http://web.archive.org/web/20120104223220/http://www.w3.org/Protocols/rfc2616/rfc2616-sec19.html#sec19.5.1)

message.entity.downloadName

If the download name is not null, the header value is "attachment;
filename=\<entity.downloadName\>".

[Content-Encoding](http://web.archive.org/web/20120104223220/http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.11)

entity.encodings

Indicates what additional content codings have been applied to the
entity-body.

[Content-Language](http://web.archive.org/web/20120104223220/http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.12)

entity.languages

Describes the natural language(s) of the intended audience for the
enclosed entity.

[Content-Length](http://web.archive.org/web/20120104223220/http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.13)

entity.size

The size of the entity-body, in decimal number of OCTETs.

[Content-Location](http://web.archive.org/web/20120104223220/http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.14)

entity.identifier

Indicates the resource location for the entity enclosed in the message.

[Content-MD5](http://web.archive.org/web/20120104223220/http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.15)

representation.digest

Value and algorithm name of the digest associated to a representation.

[Content-Range](http://web.archive.org/web/20120104223220/http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.15)

entity.range

Indicates where in the full entity-body the partial body should be
applied

[Content-Type](http://web.archive.org/web/20120104223220/http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.17)

entity.mediaType and entity.characterSet

Indicates the media type of the entity-body.

[Cookie](http://web.archive.org/web/20120104223220/http://www.w3.org/Protocols/rfc2109/rfc2109)

request.cookies

List of one or more cookies sent by the client to the server.

[Date](http://web.archive.org/web/20120104223220/http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.18)

current date

The date and time at which the message was originated.

[ETag](http://web.archive.org/web/20120104223220/http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.19)

entity.tag

The current value of the entity tag for the requested variant.

[Expect](http://web.archive.org/web/20120104223220/http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.20)

-

[Support for expect header planned for Restlet
1.2](http://web.archive.org/web/20120104223220/http://restlet.tigris.org/issues/show_bug.cgi?id=413)

[Expires](http://web.archive.org/web/20120104223220/http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.21)

entity.expirationDate

The date/time after which the response is considered stale.

[From](http://web.archive.org/web/20120104223220/http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.22)

-

[Support for misc headers planned for
later](http://web.archive.org/web/20120104223220/http://restlet.tigris.org/issues/show_bug.cgi?id=282)

[Host](http://web.archive.org/web/20120104223220/http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.23)

request.resourceRef (domain and port)

Specifies the Internet host and port number of the resource being
requested.

[If-Match](http://web.archive.org/web/20120104223220/http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.24)

request.conditions.match

Used with a method to make it conditional.

[If-Modified-Since](http://web.archive.org/web/20120104223220/http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.25)

request.conditions.modifiedSince

Used with a method to make it conditional.

[If-None-Match](http://web.archive.org/web/20120104223220/http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.26)

request.conditions.noneMatch

Used with a method to make it conditional.

[If-Range](http://web.archive.org/web/20120104223220/http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.27)

-

[Support planned for Restlet
1.2](http://web.archive.org/web/20120104223220/http://restlet.tigris.org/issues/show_bug.cgi?id=115)

[If-Unmodified-Since](http://web.archive.org/web/20120104223220/http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.28)

request.conditions.unmodifiedSince

Used with a method to make it conditional.

[Last-Modified](http://web.archive.org/web/20120104223220/http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.1)

entity.modificationDate

Indicates the date and time at which the origin server believes the
variant was last modified.

[Location](http://web.archive.org/web/20120104223220/http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.30)

response.locationRef

Used to redirect the recipient to a location other than the Request-URI
for completion of the request or identification of a new resource.

[Max-Forwards](http://web.archive.org/web/20120104223220/http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.31)

-

[Support for proxies planned for
later](http://web.archive.org/web/20120104223220/http://restlet.tigris.org/issues/show_bug.cgi?id=207)

[Pragma](http://web.archive.org/web/20120104223220/http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.32)

-

[Support for caching planned for Restlet
1.2](http://web.archive.org/web/20120104223220/http://restlet.tigris.org/issues/show_bug.cgi?id=213)

[Proxy-Authenticate](http://web.archive.org/web/20120104223220/http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.33)

-

[Support for proxies planned for
later](http://web.archive.org/web/20120104223220/http://restlet.tigris.org/issues/show_bug.cgi?id=207)

[Proxy-Authorization](http://web.archive.org/web/20120104223220/http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.34)

-

[Support for proxies planned for
later](http://web.archive.org/web/20120104223220/http://restlet.tigris.org/issues/show_bug.cgi?id=207)

[Range](http://web.archive.org/web/20120104223220/http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.35)

request.ranges

List one or more ranges to return from the entity

[Referer](http://web.archive.org/web/20120104223220/http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.36)

request.refererRef

The address (URI) of the resource from which the Request-URI was
obtained.

[Retry-After](http://web.archive.org/web/20120104223220/http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.37)

-

[Support for misc headers planned for
later](http://web.archive.org/web/20120104223220/http://restlet.tigris.org/issues/show_bug.cgi?id=282)

[Server](http://web.archive.org/web/20120104223220/http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.38)

response.serverInfo.agent

Information about the software used by the origin server to handle the
request.

[Set-Cookie](http://web.archive.org/web/20120104223220/http://www.w3.org/Protocols/rfc2109/rfc2109)

response.cookieSettings

List of one or more cookies sent by the server to the client.

[Set-Cookie2](http://web.archive.org/web/20120104223220/http://www.ietf.org/rfc/rfc2965.txt)

response.cookieSettings

List of one or more cookies sent by the server to the client.

[TE](http://web.archive.org/web/20120104223220/http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.39)

-

Not supported yet

[Trailer](http://web.archive.org/web/20120104223220/http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.40)

-

[Support for misc headers planned for
later](http://web.archive.org/web/20120104223220/http://restlet.tigris.org/issues/show_bug.cgi?id=282)

[Transfer-Encoding](http://web.archive.org/web/20120104223220/http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.41)

entity.size

Valuated to "chunked" if the entity is not null and its size is unknown
(Representation.UNKNOWN\_SIZE)

[Upgrade](http://web.archive.org/web/20120104223220/http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.42)

-

[Support for misc headers planned for
later](http://web.archive.org/web/20120104223220/http://restlet.tigris.org/issues/show_bug.cgi?id=282)

[User-Agent](http://web.archive.org/web/20120104223220/http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.43)

request.clientInfo.agent

Information about the user agent originating the request.

[Vary](http://web.archive.org/web/20120104223220/http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.44)

response.dimensions

Indicates the set of request-header fields that fully determines, while
the response is fresh, whether a cache is permitted to use the response
to reply to a subsequent request without revalidation.

[Via](http://web.archive.org/web/20120104223220/http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.45)

-

[Support for proxies planned for
later](http://web.archive.org/web/20120104223220/http://restlet.tigris.org/issues/show_bug.cgi?id=207)

[Warning](http://web.archive.org/web/20120104223220/http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.46)

-

[Support for caching planned for Restlet
1.2](http://web.archive.org/web/20120104223220/http://restlet.tigris.org/issues/show_bug.cgi?id=213)

[WWW-Authenticate](http://web.archive.org/web/20120104223220/http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.47)

response.challengeRequests

Indicates the authentication scheme(s) and parameters applicable to the
Request-URI.

X-Forwarded-For

request.clientInfo.addresses

The list of client IP addresses, including intermediary proxies.

X-HTTP-Method-Override

-

[Support for method tunnelling via header planned for Restlet
1.2](http://web.archive.org/web/20120104223220/http://restlet.tigris.org/issues/show_bug.cgi?id=297)

Appendix
========

[Registry](http://web.archive.org/web/20120104223220/http://www.iana.org/assignments/message-headers/perm-headers.html)[of
headers](http://web.archive.org/web/20120104223220/http://www.iana.org/assignments/message-headers/perm-headers.html)
maintained by IANA.

[Regsitry of HTTP status
codes](http://web.archive.org/web/20120104223220/http://www.iana.org/assignments/http-status-codes)
maintained by IANA.

[Comments
(0)](http://web.archive.org/web/20120104223220/http://wiki.restlet.org/docs_1.1/13-restlet/27-restlet/130-restlet.html#)

Comments
[Hide](http://web.archive.org/web/20120104223220/http://wiki.restlet.org/docs_1.1/13-restlet/27-restlet/130-restlet.html#)
\
There are no comments.

Add a comment

Please log in to be able to add comments.
