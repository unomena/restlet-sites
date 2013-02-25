Getting started with Maven
==========================

Introduction
============

Maven is a comprehensive project management system built around the
concept of POM (Project Object Model). One of the main advantages is the
automated handling of project dependencies, including their download.
For more information on Maven, check the [project home
page](http://maven.apache.org/).

The
[Maven](http://maven.apache.org/)
support appeared to be important for many Restlet users. The initial
response was to automatically generate the POM files for each module JAR
shipped within the Restlet distribution. This enabled users to upload
those JAR files to a local Maven repository, using a script like the one
available in the Wiki.

But, this was clearly not easy enough and forced users to download the
full distribution for each new version released, instead of just
updating a couple of JARs. There also was issues with some third-party
dependencies which aren't available in public Maven repositories, like
the db4o, AsyncWeb or Simple.

That's why it has been decided to launch two dedicated Maven
repositories. The first one is freely accessible from
[http://maven.restlet.org](http://maven.restlet.org/)
and contains all Restlet JARs and third party dependencies that aren't
available in the main public Maven repository. It will be automatically
refreshed every day.

Another repository is available at
[http://maven.noelios.com](http://maven.noelios.com/)
for the customers who subscribed to one of the [professional support
plans](http://www.noelios.com/products/support).
This repository is directly integrated with the release process and as
soon as a new release is made, it is available from this repository,
with no further delay.

Public repository configuration
===============================

Here are some instructions about how to configure Maven client to work
with the online Maven repository.

You should have Maven installed.

-   Go to [Maven download
    page](http://maven.apache.org/download.html)
-   Download the latest version of Maven and install it on your local
    computer
-   Add Maven bin folder to your PATH

Declare the repository for your project or for a parent project by
updating the *pom.xml* file and adding the following code to the
\<repositories\> section:

    <repository>
       <id>maven-restlet</id>
       <name>Public online Restlet repository</name>
       <url>http://maven.restlet.org</url>
    </repository> 

As an alternative, you can also declare the repository for all of your
projects. Go to the directory on the local computer where you just
install Maven. Open and edit *conf/settings.xml* file. Add to the
\<profiles\> section the following code:

    <profile> 
      <id>restlet</id>
      <repositories>
        <repository>
          <id>maven-restlet</id>
          <name>Public online Restlet repository</name>
          <url>http://maven.restlet.org</url>
        </repository>
      </repositories>
    </profile>

Just after the \</profiles\> add the following:

    <activeProfiles> 
      <activeProfile>restlet</activeProfile>
    </activeProfiles> 
       

Available artifacts
===================

The following table lists the available artifacts and their group and
artifact ids. With the introduction of the
[editions](/learn/guide/2.0#/13-restlet/275-restlet.html "Restlet Editions")
for the Restlet framework, it is necessary to make a distinction between
an extension for a given edition and the same extension for another
extension simply because the code of the extension may change between
each edition. This distinction is reflected in the group id of each
artifacts which contains a reference to an edition.They are all set on
the same pattern: "org.restlet.\<edition\>" where "\<edition\>" is
three-letters code of an edition among: jse (Java SE edition), jee (Java
EE edition), gae (Google App Engine edition), android (Android edition)
and gwt (google Web Toolkit edition). You can find
[here](/learn/guide/2.0#/13-restlet/28-restlet/313-restlet.html "Editions matrix")
a full view of the list of extensions and the editions that ship them. 

Group Id

Artifact ID

Description

org.restlet.\<edition\>

org.restlet

Restlet API

org.restlet.\<edition\>

org.restlet.example

Examples

org.restlet.\<edition\>

org.restlet.ext.atom

Atom extension

org.restlet.\<edition\>

org.restlet.ext.crypto

Cryptography extension including Amazon S3 and Windows Azure client
authentication.

org.restlet.\<edition\>

org.restlet.ext.fileupload

Integration with Apache FileUpload 1.2.

org.restlet.\<edition\>

org.restlet.ext.freemarker

Integration with FreeMarker 2.3.

org.restlet.\<edition\>

org.restlet.ext.grizzly

Integration with Grizzly NIO framework 1.9.

org.restlet.\<edition\>

org.restlet.ext.gwt

Server-side integration with GWT 2.0.

org.restlet.\<edition\>

org.restlet.ext.httpclient

Integration with Apache HTTP Client 4.0.

org.restlet.\<edition\>

org.restlet.ext.jaas

Support for JAAS authentication and authorization framework.

org.restlet.\<edition\>

org.restlet.ext.jackson

Integration with Jackson 1.3.

org.restlet.\<edition\>

org.restlet.ext.javamail

Integration with JavaMail 1.4 (POP3 and SMTP clients)

org.restlet.\<edition\>

org.restlet.ext.jaxb

Integration with Java XML Binding (JAXB) 2.1.

org.restlet.\<edition\>

org.restlet.ext.jaxrs

Implementation of JAX-RS 1.0 (JSR-311).

org.restlet.\<edition\>

org.restlet.ext.jdbc

Integration with Java DataBase Connectivity (JDBC) 3.0.

org.restlet.\<edition\>

org.restlet.ext.jetty

Integration with Jetty 7.0.

org.restlet.\<edition\>

org.restlet.ext.jibx

Integration with JiBX 1.2.

org.restlet.\<edition\>

org.restlet.ext.json

Support for JSON representations.

org.restlet.\<edition\>

org.restlet.ext.lucene

Integration with Apache Lucene 2.4.

org.restlet.\<edition\>

org.restlet.ext.net

Integration with Java URLConnection class.

org.restlet.\<edition\>

org.restlet.ext.netty

Integration with Netty 3.1.

org.restlet.\<edition\>

org.restlet.ext.odata

Support for the Microsoft WCF Data Services.

org.restlet.\<edition\>

org.restlet.ext.rdf

Support for the RDF parsing and generation.

org.restlet.\<edition\>

org.restlet.ext.rome

Integration with ROME 1.0.

org.restlet.\<edition\>

org.restlet.ext.servlet

Integration with Servlet API 2.5.

org.restlet.\<edition\>

org.restlet.ext.simple

Integration with Simple framework 4.1.

org.restlet.\<edition\>

org.restlet.ext.slf4j

Integration with SLF4J 1.5.

org.restlet.\<edition\>

org.restlet.ext.spring

Integration with Spring framework 2.5.

org.restlet.\<edition\>

org.restlet.ext.ssl

Support for SSL utilities and integration with jSSLutils library.

org.restlet.\<edition\>

org.restlet.ext.velocity

Integration with Apache Velocity 1.6.

org.restlet.\<edition\>

org.restlet.ext.wadl

Support the WADL specification.

org.restlet.\<edition\>

org.restlet.ext.xdb

Integration with Oracle 11g XML DB feature.

org.restlet.\<edition\>

org.restlet.ext.xml

Support for XML and XSLT representations.

org.restlet.\<edition\>

org.restlet.ext.xstream

Integration with XStream 1.3.

org.restlet.\<edition\>

org.restlet.test

Test module.

Sample dependencies declaration
===============================

Each project based on the Restlet Framework needs to declare at least
one dependency: the Restlet core module. According to your needs, you
should complete the list of dependencies with the required extensions
and connectors. For example, assuming your project is a Web server
delivering static files, you need one HTTP server connector such as
Simple. Since your Maven client correctly references the Restlet online
repository, just open and edit the *pom.xml* file for your project and
add the following lines of text into the \<dependencies\> section.

    <dependency>
      <groupId>org.restlet.jse</groupId>
      <artifactId>org.restlet</artifactId>
      <version>2.0.0</version>
    </dependency>
    <dependency>
      <groupId>org.restlet.jse</groupId>
      <artifactId>org.restlet.ext.simple</artifactId>
      <version>2.0.0</version>
    </dependency>

