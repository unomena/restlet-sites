Spring extension
================

Table of contents
=================

1.  [Introduction](http://web.archive.org/web/20090406195204/http://wiki.restlet.org/docs_1.1/13-restlet/28-restlet/70-restlet.html#dsy70-restlet_Introduction)
2.  [Description](http://web.archive.org/web/20090406195204/http://wiki.restlet.org/docs_1.1/13-restlet/28-restlet/70-restlet.html#dsy70-restlet_Description)
3.  [Integration
    modes](http://web.archive.org/web/20090406195204/http://wiki.restlet.org/docs_1.1/13-restlet/28-restlet/70-restlet/194-restlet.html "Spring extension - Integration modes")
4.  [Configuring Restlet
    beans](http://web.archive.org/web/20090406195204/http://wiki.restlet.org/docs_1.1/13-restlet/28-restlet/70-restlet/195-restlet.html "Spring extension - Configuring Restlet beans")
5.  [A complete
    example](http://web.archive.org/web/20090406195204/http://wiki.restlet.org/docs_1.1/13-restlet/28-restlet/70-restlet/196-restlet.html "Spring extension - A complete example")
6.  [Configuration of Restlet Resources in
    Spring](http://web.archive.org/web/20090406195204/http://wiki.restlet.org/docs_1.1/13-restlet/28-restlet/70-restlet/197-restlet.html "Spring extension - Configuration of Restlet Resources")

Introduction
============

This extension provides various modes of integration between the Restlet
framework and the popular Spring framework. Historically, this extension
emerged from the needs of Spring users, stuck between the Spring's
mechanism of Dependency Injection mostly based on JavaBean setters
(Setter Injection) and constructor arguments (Constructor Injection),
and the conceptual choices of the Restlet framework that didn't
systematize the use of simple POJOs. 

Description
===========

This extension aims at providing a better and more natural integration.
It comes in two part, one extension for the Restlet API
(org.restlet.ext.spring) and another for the Restlet engine
(com.noelios.restlet.ext.spring). Please, refer to the [Spring extension
Javadocs](http://web.archive.org/web/20090406195204/http://www.restlet.org/documentation/1.1/ext/org/restlet/ext/spring/package-summary.html)
and the [FAQ
\#23](http://web.archive.org/web/20090406195204/http://www.restlet.org/documentation/1.1/faq#23)
for more details and have a look the [Restlet
wiki](http://web.archive.org/web/20090406195204/http://wiki.restlet.org/)
for several sample codes.

Here is the list of dependencies for this extension:

-   [Spring framework
    2.0](http://web.archive.org/web/20090406195204/http://www.springframework.org/)

[Comments
(0)](http://web.archive.org/web/20090406195204/http://wiki.restlet.org/docs_1.1/13-restlet/28-restlet/70-restlet.html#)

Comments
[Hide](http://web.archive.org/web/20090406195204/http://wiki.restlet.org/docs_1.1/13-restlet/28-restlet/70-restlet.html#)
\
There are no comments.

Add a comment

Please log in to be able to add comments.
