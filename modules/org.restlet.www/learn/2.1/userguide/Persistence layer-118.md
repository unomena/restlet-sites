Persistence layer
=================

Introduction
============

The Restlet framework is completely agnostic regarding the persistence
technology that you want to use. Many alternatives having been used
successfully and we are confident that you won't hit any special
limitation in this area.

Description
===========

The basic idea is that from a Restlet point of view, your application
with be composed of resources, extending the
org.restlet.resource.Resource class. Those subclassed will be in charge
of handling the incoming requests. One instance of your resource
subclass will be created for each request to handle, making sure that
you don't have to care about concurrent access at this point of your
application.

When you resource is instantiated, it will need to expose its
representations (via HEAD, GET methods), to store (PUT method), accept
(POST method) or remove (DELETE method) representations. During
construction, based on the actual identity of your resource and other
parameters or attributes of the request, you will be able to contact
your persistence backend in order to support your processing logic or
the representations of your resources returned.

[Comments
(0)](http://web.archive.org/web/20111015080437/http://wiki.restlet.org/docs_2.1/13-restlet/21-restlet/378-restlet/118-restlet.html#)

Comments
[Hide](http://web.archive.org/web/20111015080437/http://wiki.restlet.org/docs_2.1/13-restlet/21-restlet/378-restlet/118-restlet.html#)
\
There are no comments.

Add a comment

Please log in to be able to add comments.
