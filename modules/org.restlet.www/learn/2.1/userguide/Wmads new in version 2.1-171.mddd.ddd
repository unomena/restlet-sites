What's new in version 2.1
=========================

Introduction
============

In the next sections, you will get a synthesis of the major changes done
to the Restlet Framework in version 2.1. For more details, you can read
the [full list of
changes](http://web.archive.org/web/20120107072210/http://www.restlet.org/documentation/snapshot/jse/changes).

The sub-sections will be written when the 2.1 RC1 version is released
only.

Better documentation
--------------------

### Restlet in Action book [10/12 chapters written]

We will finish writing the *Restlet in Action* book for Manning
([already available in early
access](http://web.archive.org/web/20120107072210/http://www.restlet.org/documentation/books))
and make sure that the printed version is available at the same time as
the 2.1.0 release.

Scalable internal connector
---------------------------

### Non blocking NIO

In version 2.0, we have added support for [asynchronous processing of
calls](http://web.archive.org/web/20120107072210/http://wiki.restlet.org/developers/172-restlet/297-restlet.html)
as a preview feature, including provisional responses (1xx status code
in HTTP). This feature was only usable with the internal HTTP connector
that is part of the Restlet engine (org.restlet.jar file), relying on
message queues to support asynchronous handling. However, its IO
processing was still done in a blocking manner, requiring two threads
per connection which limits its scalability, even if persistent
connections and pipelining and now supported.

There, we have started work on a new [NIO version of this internal
connector](http://web.archive.org/web/20120107072210/http://wiki.restlet.org/developers/172-restlet/354-restlet.html)that
leverages the non-blocking features of NIO to support a large number of
concurrent connections and messages with only a single IO thread! The
first results were very promising and we want to complete this connector
in version 2.1 to replace the current internal connector with a lighter,
faster and more scalable one.

### SIP connector

SIP is a core protocol for Voice of IP (VoIP) to control multimedia
session. It has been designed based on the HTTP protocol, using the same
syntax for request and messages and with a similar processing flow,
leveraging a lot provisional responses. We explored over the past months
the possibility to provide a [SIP
connector](http://web.archive.org/web/20120107072210/http://wiki.restlet.org/developers/257-restlet/300-restlet.html)
based on the same internal connector that we use for HTTP and already we
have a prototype working in the Restlet Incubator.

For version 2.1, we want to complete this initial work and make sure its
works and scales properly on top of the new NIO based connector
mentioned above. This will ship as an org.restlet.ext.sip extension. We
also plan to explore a higher-level SIP application API that would
provide a  REST-minded alternative to the SIP Servlets.

Security enhancements
---------------------

### Google SDC connector

This protocol allows tunnelling HTTP calls from a public cloud such as
GAE, AWS or any other IaaS, to an intranet protected by a firewall,
without requiring changes to this firewall. See details in [this blog
post](http://web.archive.org/web/20120107072210/http://blog.noelios.com/2011/03/31/leveraging-sdc-beyond-google-cloud-with-restlet/).

### Google App Engine extension

This extension integrates GAE's authentication service with Restlet's
security API.

### OAuth 2.0 and OpenID 2.0 extensions

See user guide for details.

### CookieAuthenticator

Added to the Crypto extension.

Multipart HTML forms
--------------------

The support for composite representations such as multi-part forms, on
both the client and the server side is a recurrent need expressed by
users, we should address it directly in the API or via an extension.

Currently, users rely on the FileUpload extension on the server-side and
on alternative HTTP clients such as Apache HTTP Client 3.1 on the
client-side. It would be much better to have a built-in consistent
solution.

There will also be a focus on facilitating the validation of form
submissions based on [this specifications
document](http://web.archive.org/web/20120107072210/http://wiki.restlet.org/developers/172-restlet/g5/367-restlet.html).

Conneg service
--------------

Content negotiation has always been a strong feature of the Restlet
Framework. In version 2.0, its implementation and negotiation algorithm
is fixed in the Restlet Engine. In this new version, we want to make it
customizable via a new ConnegService.

Eclipse integration
-------------------

Eclipse is more than an IDE and now provides a comprehensive runtime
platform via the [Eclipse
RT](http://web.archive.org/web/20120107072210/http://www.eclipse.org/rt/)
project. In addition, OSGi and model-driven technologies developed by
the Eclipse foundation nicely fit with the Restlet Framework. In version
2.1, we will help developers to bridge the Restlet and the Eclipse
worlds.

### Model-driven REST

Based on our experience with customers, we believe that the combination
of Restlet and the pragmatic model-driven technologies developed by the
[Eclipse Modeling
project](http://web.archive.org/web/20120107072210/http://www.eclipse.org/modeling/)
adds a lot of value. In version 2.1, we will add those new related
extensions:

-   [EMF
    extension](http://web.archive.org/web/20120107072210/http://wiki.restlet.org/developers/257-restlet/345-restlet.html)
    : to convert EMF representation beans into XML, XMI or HTML
    representations

### Integration with Equinox/OSGi

We continued to mentor/support the ongoing project for [integrating
Restlet with Eclipse
Equinox](http://web.archive.org/web/20120107072210/http://blog.noelios.com/2010/05/06/gsoc-and-restlet-integration-with-equinox/)
(OSGi runtime) which is now part of Restlet Incubator.

### Eclipse update site

In addition to our Maven repository, Zip archives and Windows
installers, we want to add a possibility to install and update Restlet
modules and dependent libraries via the Eclipse IDE directly. For this
we will provide an update site. Restlet modules are already OSGi bundles
so this should be straightforward. See the [specifications
page](http://web.archive.org/web/20120107072210/http://wiki.restlet.org/developers/172-restlet/g1/417-restlet.html).

[Comments
(0)](http://web.archive.org/web/20120107072210/http://wiki.restlet.org/docs_2.1/13-restlet/21-restlet/171-restlet.html#)

Comments
[Hide](http://web.archive.org/web/20120107072210/http://wiki.restlet.org/docs_2.1/13-restlet/21-restlet/171-restlet.html#)
\
There are no comments.

Add a comment

Please log in to be able to add comments.
