Presentation layer
==================

Introduction
============

When compared to the Servlet API, the Restlet API doesn't have a sister
API like Java Server Pages (JSP). Instead we made the design choice to
be equally open to all presentation technologies. This openess is
materialized in the Representation class which is used for response
entities. 

Description
===========

More concretely, we provide integrations with three popular template
technologies : XSLT, FreeMarker and Apache Velocity. In addition,
integration with Facelets has been achieved by a third party and it
should be very easy to support any other reusable template technology.

The design idea of those extensions is to use a TemplateRepresentation
that combines at generation time a data model with a template document.

[Comments
(0)](http://web.archive.org/web/20120107041124/http://wiki.restlet.org/docs_2.0/13-restlet/21-restlet/378-restlet/116-restlet.html#)

Comments
[Hide](http://web.archive.org/web/20120107041124/http://wiki.restlet.org/docs_2.0/13-restlet/21-restlet/378-restlet/116-restlet.html#)
\
There are no comments.

Add a comment

Please log in to be able to add comments.
