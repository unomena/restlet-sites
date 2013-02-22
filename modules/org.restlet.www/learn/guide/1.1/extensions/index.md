Restlet Extensions
==================

Introduction
============

The Restlet framework is composed of three parts:

-   The Restlet API
-   The Noelios Restlet Engine (NRE) implementing the API
-   Optional Restlet extensions.

This chapter presents all the extensions available in the Restlet
framework and describes how to add those extensions to your application.

Description
===========

The NRE lets you build complete Web applications by itself and is cpable
of leveraging other open source projects to facilitate tasks such as
generation of dynamic representations, management of uploaded files,
sending of mails, etc. There are two types of extensions.

The first one is for extensions built only on top of the Restlet API and
typically provide new type of representations (JAXB, FreeMarker, JiBx,
etc.) or support of important standards (Atom, WADL).

The second one covers extensions to the Restlet engine such as client
connectors, server connectors and authentication helpers.

Distribution
============

All extensions and their dependencies are shipped with the Restlet
distribution by the way of JAR files. Adding an extension to your
application is as simple as adding the archives of the chosen extension
and its dependencies to the classpath. 

You can also have a look to the [FAQ
\#4](http://restlet.org/learn/javadocs/1.1/faq#04)
and [FAQ
\#5](http://restlet.org/learn/javadocs/1.1/faq#05)
which completes this subject.

