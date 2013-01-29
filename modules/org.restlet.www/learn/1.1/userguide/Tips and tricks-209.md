Tips and tricks
===============

Responses with no entity
========================

This tip applies in the case of applications that run in GWT hosted
mode. \
 Imagine that a resource handles a POST request and tells a user to
redirect to another URI. In this case, it seems useless to add en entity
body since its content is generally discarded by the client. However,
you will notice that your Internet browser displays a white page even if
the POST request has been correctly issued by the server.\
 This is due to a limitation not fixed yet where very small entities are
swallowed by the servlet container of the GWT hosted mode.

If you face this problem, a simple workaround is just to add a
reasonably sized entity to the response. For example, you can add a
simple filter at the top of your hierarchy of nodes which ensure that an
entity is sent back to the client.

[Comments
(0)](http://web.archive.org/web/20101124061154/http://wiki.restlet.org/docs_1.1/13-restlet/144-restlet/209-restlet.html#)

Comments
[Hide](http://web.archive.org/web/20101124061154/http://wiki.restlet.org/docs_1.1/13-restlet/144-restlet/209-restlet.html#)
\
There are no comments.

Add a comment

Please log in to be able to add comments.
