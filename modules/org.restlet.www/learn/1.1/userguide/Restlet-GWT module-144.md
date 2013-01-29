Restlet-GWT module
==================

This covers version 1.1 of the Restlet Framework. [See this
page](http://web.archive.org/web/20100428000308/http://wiki.restlet.org/docs_2.0/13-restlet/275-restlet/144-restlet.html)for
version 2.0.

Introduction
============

This chapter presents the Restlet-GWT module, which is a port of the
client side Restlet API to GWT 1.5.

Description
===========

Google Web Toolkit is a powerful and widely used platform for rich
internet application. It is based on a smart compiler taking Java source
and producing optimized JavaScript (byte)code.

By default, GWT recommends using a custom GWT-RPC mechanism to
communicate between the GWT front-end (Web browser) and the back-end
(Web server). In addition, the back-end has to be built using a Servlet
container in order to work properly and to invoke your custom classes
and methods. This might remind you of the RMI (Remote Method Invocation)
or CORBA mechanisms and it comes with the same issues: the tight
coupling between client and server code. Also, this reduces the
opportunities of integration with other back-end technologies, outside
Java/Servlet.

As you know, REST is a much more flexible and interoperable way to
communicate between a client and a server. On the server-side, you can
use Restlet or alternative technologies. But on the GWT front-end, the
support has been limited so far. The GWT API does contain classes to
send HTTP requests but they are quite low-level (you need to understand
the HTTP headers syntax for example) and slow down productivity.

Thanks to the new GWT 1.5 release, it was possible to quickly port the
Restlet API and Engine to GWT. We kept only the classes required for the
client-side obviously and had to remove classes based on Java APIs not
available in GWT (such as NIO channels or BIO streams). We tried to
provide an easy integration with JSON and XML as they are the two most
common media types to exchange representations in REST for rich clients.

[Comments
(0)](http://web.archive.org/web/20100428000308/http://wiki.restlet.org/docs_1.1/13-restlet/144-restlet.html#)

Comments
[Hide](http://web.archive.org/web/20100428000308/http://wiki.restlet.org/docs_1.1/13-restlet/144-restlet.html#)
\
There are no comments.

Add a comment

Please log in to be able to add comments.
