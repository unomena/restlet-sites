Atom extension
==============

Introduction
============

This extension provides support for the Atom standard. Atom is an
evolution of the RSS standard that was introduced to remove know
limitations and enable new features and usages, especially in the light
of the REST principles.

Its main purpose it to define a format to represent Web feeds such as
news feed. This is called the [Atom Syndication
Format](http://web.archive.org/web/20100818233847/http://www.atomenabled.org/developers/syndication/atom-format-spec.php).

The second purpose is to support the publication and modification of
those Web feeds, remotely on the Web and in an interoperable manner.
This is called the [Atom Publishing
Protocol](http://web.archive.org/web/20100818233847/http://www.atomenabled.org/developers/protocol/atom-protocol-spec.php)
(APP).

Description
===========

The Restlet extension for Atom provides a complete Atom API for both Web
feeds and publication documents. This API is capable of both parsing and
formatting Atom and APP XML documents compliant with the 1.0
specifications.

The two main classes you should use are Feed (an Atom XML feed) and
Service (an AtomPub XML service descriptor) which are both subclasses of
SaxRepresentation. As such instances of both classes can be directly
returned as representation of your Restlet resources. They also support
parsing via the constructors accepting a Representation parameter.

For an usage example, check the source code of the
org.restlet.example.book.restlet.ch8.resources.FeedResource class.

For additional details, please consult  [the
Javadocs](http://web.archive.org/web/20100818233847/http://www.restlet.org/documentation/2.0/jse/ext/org/restlet/ext/atom/package-summary.html).

Links
-----

-   [AtomEnabled.org](http://web.archive.org/web/20100818233847/http://www.atomenabled.org/)
-   [Wikipedia
    entry](http://web.archive.org/web/20100818233847/http://en.wikipedia.org/wiki/Atom_%28standard%29)

[Comments
(2)](http://web.archive.org/web/20100818233847/http://wiki.restlet.org/docs_2.0/13-restlet/28-restlet/65-restlet.html#)

Comments
[Hide](http://web.archive.org/web/20100818233847/http://wiki.restlet.org/docs_2.0/13-restlet/28-restlet/65-restlet.html#)
\

Created by Kesav Kumar Kolla on 12/30/09 10:25:55 AM

Any one help me how to publish an atom service in 2.0? I am basically
trying to mimic the ADO.NET DataServices in resetlet. I have a model
class which I want to publish using restlet as a service. I need to be
able to consume this service using ASP.NET AJAX libraries.

Created by Jerome Louvel on 12/30/09 11:18:52 AM

Hi Kesav,\
 \
Sounds like a cool idea! Please join us in the "discuss" mailing list:
http://www.restlet.org/community/lists\
 \
Best regards,\
Jerome\

Add a comment

Please log in to be able to add comments.
