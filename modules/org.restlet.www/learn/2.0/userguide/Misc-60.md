Misc
====

Enhanced Maven support
======================

A long time ago, we offered a Maven distribution via [our own Maven
repositories](http://web.archive.org/web/20120119082108/http://www.restlet.org/downloads/maven)
and regularly we try to improve its quality, for example working with
Buckminster users to adjust our Maven metadata. However, we use a custom
forge based on Ant as our official build system and this has been
causing some pains to Maven developers and putting some barriers for
potential contributors.

Thanks to ideas and contributions from the community, we are now
providing Maven POM files in our SVN repository as an alternative way to
build Restlet. Of course, those POM files are the same that are
distributed in our Maven repository and are consistently synchronized
with our main Ant script to ensure that they don’t diverge in term of
dependency versions for example.

![](Misc-60_files/maven1.png)

For details on building Restlet with Maven, please read [this short
page](http://web.archive.org/web/20120119082108/http://wiki.restlet.org/developers/179-restlet/240-restlet.html)
on our developers wiki. Note that we have also adjusted our Maven
GroupId (only “org.restlet” is used now) are redistributed third-party
libraries are now packaged with a “org.restlet.lib.” ArtifactId prefix.

Licensing changes
=================

Eclipse Public License 1.0 is an additional licensing option offered

OAuth extension
===============

The OAuth extension available in Restlet 1.1 has been moved to the
[Restlet
Incubator](http://web.archive.org/web/20120119082108/http://wiki.restlet.org/docs_2.0/257-restlet.html?branch=main "Restlet Incubator")
as it would require too much work to get aligned with the new Restlet
security and resource APIs in version 2.0. Note that this is temporary
and we definitely want to reintroduce this feature in Restlet 2.1.

[Comments
(0)](http://web.archive.org/web/20120119082108/http://wiki.restlet.org/docs_2.0/13-restlet/21-restlet/171-restlet/60-restlet.html#)

Comments
[Hide](http://web.archive.org/web/20120119082108/http://wiki.restlet.org/docs_2.0/13-restlet/21-restlet/171-restlet/60-restlet.html#)
\
There are no comments.

Add a comment

Please log in to be able to add comments.
