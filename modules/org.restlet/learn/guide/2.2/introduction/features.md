# Features

## Introduction

Restlet Framework is mature and scalable, based on a small core and many optional extensions, making it suitable for any kind of web API development, including cross-channel web sites and applications.

## Client and server side

Thanks to its unique design, it can be used on both client-side and server-side, or even both sides at the same time like for composite web APIs and mashups. With the availability of a port to Google Web Toolkit, it can even be used in your favorite Web browser!

## Servlet compatible

Restlet was an attempt to build a better Servlet API, aligned with the true Web architecture (REST) and standards (HTTP, URI). Therefore the Restlet API has no dependency on the Servlet API, it only depends on the Java SE. However, it is perfectly possible to deploy a Restlet application into Java EE application servers or just Servlet containers. This is
possible using an adapter Servlet provided as an extension.

## Complete Web server

Contrary to the Servlet API, the Restlet API gives you extensive control on the URI mapping and on the virtual hosts configuration. It includes a powerful Directory class to server static files in a way similar to Apache Web Server. For example, our Restlet.org web site is directly powered by Restlet Framework on a regular JVM!

Here is a more complete list of features provided by the internal Web server:
- Static file serving similar to Apache HTTP Server, with metadata association based on file extensions.
- Transparent content negotiation based on client preferences.
- Conditional requests automatically supported for resources.
- Remote edition of files based on PUT and DELETE methods (aka mini-WebDAV mode).
- Decoder service transparently decodes compressed or encoded input
  representations. This service is transparent for Restlet
  applications.
- Log service writes all accesses to your applications in a standard
  Web log file. The log format follows the [W3C Extended Log File
  Format](http://www.w3.org/TR/WD-logfile.html)
  and is fully customizable.
- Powerful URI based redirection support similar to Apache Rewrite
  module.
- Extensive and flexible security support (authentication and
  authorization) being added to Restlet 2.0.

## Presentation and persistence agnostic

By staying open to all presentation technologies (HTML, Swing, Eclipse,
GWT, AJAX, etc.), all persistence technologies (JDBC, Hibernate, db4o,
iBatis, etc.) and all execution environments (standalone JavaSE, JavaEE,
Servlet, Spring, OSGi, Guice, SLEE, etc.), your investment in Restlet is
secured. With very little care, your Restlet applications can even be
fully portable from one environment to the other.

## Multiple editions

REST principles have no limit, they can be applied everywhere the Web is
and even in places where there is no Internet but needs for
communication or effective decoupling. Currently, the Restlet Framework
is available in the Android, GWT, GAE, classic Java editions:

- Restlet edition for Java SE, letting you run your Restlet
  applications on regular JVMs.
- Restlet edition for Java EE, letting you run your Restlet
  applications in regular Servlet containers.
- Restlet edition for GWT, letting you leverage the Restlet API from
  within any Web browser, without plugins.
- Restlet edition for GAE, letting you deploy Restlet applications on
  Google AppEngine cloud computing platform.
- Restlet edition for Android, letting you deploy Restlet applications
  on Google Android mobile devices.
- Restlet edition for OSGi, letting you deploy Restlet applications
  on dynamic and embedded OSGi environments.

## Native REST support

- Core REST concepts have equivalent Java artifact (Resource,
  Representation, Connector or Component for example).
- Suitable for both client-side and server-side web applications. The
  innovation is that that it uses the same API, reducing the learning
  curve and the software footprint.
- Concept of "URIs as UI" supported based on the URI Templates
  standard. This results in a very flexible yet simple routing with
  automatic extraction of URI variables into request attributes.
- Tunnelling service lets browsers issue any HTTP method (PUT, DELETE,
  MOVE, etc.) through a simple HTTP POST. This service is transparent
  for Restlet applications.
- Ready for the Semantic Web (Web 3.0), with full RDF supports.

## Available Connectors

- Multiple server HTTP connectors available, based on either
  [Eclipse Jetty](http://www.eclipse.org/jetty/) or the
  [Simple framework](http://www.simpleframework.org/).
- [AJP](http://tomcat.apache.org/connectors-doc/)
  server connector available to let you plug behind an Apache HTTP
  server or Microsoft IIS. It is based on Jetty's connector.
- Multiple client HTTP connectors available, based on either the JDK's
  [HttpURLConnection](http://java.sun.com/j2se/1.5.0/docs/api/java/net/HttpURLConnection.html)
  class or on [Apache HTTP
  Client](http://jakarta.apache.org/commons/httpclient/).
- Compact internal HTTP client and server for development and light
  deployments. No external dependency needed.
- Client SMTP, SMTPS, POP v3 and POPS v3 connectors are provided based
  on [JavaMail](http://java.sun.com/products/javamail/)
  and a custom email XML format.
- Client JDBC connector based on the JDBC API, a custom request XML
  format and the JDBC [WebRowSet interface](http://java.sun.com/j2se/1.5.0/docs/api/javax/sql/rowset/WebRowSet.html)
  for XML responses.
- Client FILE connector supports GET, PUT and DELETE methods on files
  and directories. In addition, it is able to return directory
  listings.
- Client CLAP connector to access to the Classloader resources.
- Client and server [RIAP connectors](../core/base/connectors)
  to access to the Restlet internal resources, directly inside the
  JVM, relatively to the current application or virtual host or
  component.
- Client SOLR connector to call embedded [Apache Lucene Solr](http://lucene.apache.org/solr/)
  search and indexing engine.

## Available Representations

- Built-in support for XML representations (JAX, JibX, DOM or SAX
  based) with a simple XPath API based on JDK's built-in XPath engine.
- Integration with the [FreeMarker template engine](http://freemarker.org/)
- Integration with the [Velocity template engine](http://velocity.apache.org/)
- Integration with [Apache FileUpload](http://jakarta.apache.org/commons/fileupload/)
  to support multi-part forms and easily handle large file uploads from browsers
- Transformer filter to easily apply XSLT stylesheets on XML
  representations. It is based on JDK's built-in XSLT engine.
- Extensible set of core representations based on NIO readable or
  writable channels, BIO input or output streams.
- Support for Atom and JSON standards.
- Integration with [Apache Lucene Tika](http://lucene.apache.org/tika/)
  to support metadata extraction from any representation.

## Flexible configuration

- Complete configuration possible in Java via the Restlet API
- Configuration possible via Restlet XML and WADL files
- Servlet adapter provided to let you deploy any Restlet application
  in Servlet compliant containers like Tomcat, when the usage of
  standalone HTTP connectors is not possible.
- Implementation of the JAX-RS 1.0 standard API (based on JSR-311).
- Deployment as native services is possible and illustrated using the
  powerful [Java Service Wrapper](http://wrapper.tanukisoftware.org/).
- Extensive integration with popular Spring IoC framework.
- Deployment to Oracle 11g embedded JVM supported by special
  extension.

## Security

- Supports HTTP Basic and Digest authentication (client and server side)
- Supports Amazon S3 authentication
- Supports Microsoft Shared Key and Shared Key Lite authentication (client side)
- Supports OAuth 2.0 authentication
- Supports HTTPS (HTTP over SSL)
- Supports SMTPS (SMTP over SSL) and SMTP-STARTTLS
- Supports POPS (POP over SSL)

## Scalability

- Fully multi-threaded design with per-request Resource instances to
  reduce thread-safety issues when developing applications.
- Intentional removal of Servlet-like HTTP sessions. This concept,
  attractive as a first sight, is one of the major issue for Servlet
  scalability and is going against the stateless exchanges promoted by
  REST.
- Supports non-blocking NIO modes to decouple the number of
  connections from the number of threads.
- Supports asynchronous request processing, decoupled from IO
  operations. Unlike the Servlet API, the Restlet applications don't
  have a direct control on the outputstream, they only provide output
  representation to be written by the server connector.

## Upcoming features

Is something important for you missing? Maybe we are already working on
it or are planning to do so.

We suggest that you have a look at [our public roadmap](http://restlet.org/learn/roadmap)
or at our [issue tracker on GitHub](https://github.com/restlet/restlet-framework-java/issues).
Feel free to create some new ones if needed!
