Apache HTTP Client extension
============================

Introduction
============

This connector is based on [Apache Commons HTTP
client](http://web.archive.org/web/20101209061751/http://hc.apache.org/httpcomponents-client/).
It provides an HTTP and HTTPS client connector with advanced
multi-threading and connection reuse support.

Description
===========

As pointed out by the Apache HTTPClient tutorial it is crucial to read
entirely each response. It allows to release the underlying connection.
Not doing so may cause future requests to block.

This connector supports the following protocols: HTTP, HTTPS. The list
of supported specific parameters is available in the Javadocs:

-   [HTTP client
    parameters](http://web.archive.org/web/20101209061751/http://www.restlet.org/documentation/2.0/jse/ext/org/restlet/ext/httpclient/HttpClientHelper.html)

For additional details, please consult the
[Javadocs](http://web.archive.org/web/20101209061751/http://www.restlet.org/documentation/2.0/jse/ext/org/restlet/ext/httpclient/package-summary.html).

[Comments
(0)](http://web.archive.org/web/20101209061751/http://wiki.restlet.org/docs_2.0/13-restlet/28-restlet/75-restlet.html#)

Comments
[Hide](http://web.archive.org/web/20101209061751/http://wiki.restlet.org/docs_2.0/13-restlet/28-restlet/75-restlet.html#)
\
There are no comments.

Add a comment

Please log in to be able to add comments.
