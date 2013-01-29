Architecture flexibility
========================

Introduction
============

GWT's idiomatic RPC approach
============================

![restgwt0](Architecture%20flexibility-185_files/data_004.html "restgwt0")

Figure 1. (For comparison)  GWT's idiomatic RPC approach requires a
Servlet container and communicates opaquely via JSON-encoded data
atoms.  Alternatives involve writing low-level HTTP code.

The client-side Restlet/GWT module
==================================

![restgwt1](Architecture%20flexibility-185_files/data.html "restgwt1")

Figure 2.  The client-side Restlet/GWT module (org.restlet.gwt) plugs
into your Google Web Toolkit code as a module, and allows you to use the
high level Restlet API to talk to any server platform.  XML and JSON
representations are supported by default, and of course you can develop
your own representational abstractions as well.  Code written for
Restlet should be simple to port to Restlet-GWT.

Restlet/GWT can work alongside GWT-RPC
======================================

![restgwt2](Architecture%20flexibility-185_files/data_005.html "restgwt2")

Figure 3. Restlet/GWT can work alongside GWT-RPC and other mechanisms. 
REST APIs exposed by your custom Servlets can work alongside GWT-RPC in
a Servlet container environment.

Restlet, an ideal server side companion for Restlet-GWT
=======================================================

![restgwt4](Architecture%20flexibility-185_files/data_003.html "restgwt4")

Figure 4. .  Not only does Restlet provide a simple means of defining
and mapping REST resources, it also provides useful facilities like the
Redirector, which can be used to gateway requests to other servers,
easily working around the single-source limitations of the AJAX
programming model.  The Restlet server-side library even works in GWT
Hosted Mode using the Noelios GWT Extension, so your entire application
can be readily debugged.

Integration with standalone Restlet connectors
==============================================

![restgwt5](Architecture%20flexibility-185_files/data_002.html "restgwt5")

Figure 5.  If you don't need to use the Servlet-dependent GWT-RPC API,
your compiled GWT application can be deployed very efficiently in a
standalone Restlet environment using one of the embedded server
connectors.  Restlet 1.1's Net server connector, for example, has no
dependencies other than Java; with this, it is possible to create and
release an extremely small, but very full featured, standalone
application.

[Comments
(0)](http://web.archive.org/web/20111106194146/http://wiki.restlet.org/docs_2.1/13-restlet/275-restlet/144-restlet/185-restlet.html#)

Comments
[Hide](http://web.archive.org/web/20111106194146/http://wiki.restlet.org/docs_2.1/13-restlet/275-restlet/144-restlet/185-restlet.html#)
\
There are no comments.

Add a comment

Please log in to be able to add comments.
