Internal connectors
===================

Here is the list of connectors built-in the Restlet engine:

-   FILE : to access to local file via the uniform Restlet interface
    (URI and uniform methods)
-   CLAP : ClassLoader Access Protocol to access to local entities via
    the various classloaders available
-   RIAP : Restlet Internal Access Protocol to access to other resources
    hosted in the same Restlet Component or VirtualHost or Application,
    allowing powerful modularization of larger Restlet applications.
-   HTTP client : supporting chunked encoding but not persistent
    connections nor HTTPS
-   HTTP server : supporting chunked encoding but not persistent
    connections nor HTTPS

Note that those connectors available in the engine JAR but still need to
be declared in your Restlet component, otherwise your applications won't
be able to use them!

[Comments
(0)](http://web.archive.org/web/20090414235241/http://wiki.restlet.org/docs_1.1/13-restlet/48-restlet/86-restlet.html#)

Comments
[Hide](http://web.archive.org/web/20090414235241/http://wiki.restlet.org/docs_1.1/13-restlet/48-restlet/86-restlet.html#)
\
There are no comments.

Add a comment

Please log in to be able to add comments.
