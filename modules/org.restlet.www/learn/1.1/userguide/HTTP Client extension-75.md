HTTP Client extension
=====================

Introduction
============

This connector is based on [Apache Commons HTTP
client](http://web.archive.org/web/20090204182437/http://jakarta.apache.org/commons/httpclient/).
It provides an HTTP and HTTPS client connector with advanced
multi-threading and connection reuse support.

Description
===========

As pointed out by the Apache HTTPClient tutorial it is crucial to read
entirely each response. It allows to release the underlying connection.
Not doing so may cause future requests to block. See [Apache HTTPClient
3.x
tutorial](http://web.archive.org/web/20090204182437/http://jakarta.apache.org/httpcomponents/httpclient-3.x/tutorial.html).

This connector supports the following protocols: HTTP, HTTPS.

The list of supported specific parameters is available in the javadocs:

-   [HTTP client
    parameters](http://web.archive.org/web/20090204182437/http://www.restlet.org/documentation/1.1/ext/com/noelios/restlet/ext/httpclient/HttpClientHelper.html)

Here is the list of dependencies of this connector:

-   [Apache Commons HTTP Client
    3.1](http://web.archive.org/web/20090204182437/http://jakarta.apache.org/commons/httpclient/)
-   [Apache Commons Codec
    1.3](http://web.archive.org/web/20090204182437/http://jakarta.apache.org/commons/codec/)
-   [Apache Commons Logging
    1.1](http://web.archive.org/web/20090204182437/http://jakarta.apache.org/commons/logging/)

[Comments
(0)](http://web.archive.org/web/20090204182437/http://wiki.restlet.org/docs_1.1/13-restlet/28-restlet/75-restlet.html#)

Comments
[Hide](http://web.archive.org/web/20090204182437/http://wiki.restlet.org/docs_1.1/13-restlet/28-restlet/75-restlet.html#)
\
There are no comments.

Add a comment

Please log in to be able to add comments.
