Logging
=======

Introduction
============

Restlet relies on JDK's logging API to log records about its activity.
For a complete documentation on this standard API, you can check the
[related JDK
documentation](http://web.archive.org/web/20090126045410/http://java.sun.com/j2se/1.5.0/docs/guide/logging/index.html).
For additional configuration details, you should also read the [Javadocs
of the LogManager
class](http://web.archive.org/web/20090126045410/http://java.sun.com/j2se/1.5.0/docs/api/java/util/logging/LogManager.html).
For example, to indicate the location of your logging properties file,
you should add the following parameter to your JVM:

    -Djava.util.logging.config.file="/home/myApp/config/myLogging.properties"

When developing your Restlet code, you should always use the current
context to get a logger. This will ensure the proper naming and
filtering of your log records. Most classes like Restlet subclasses or
Resource subclasses offer a "getLogger()" method. Otherwise, you can
rely on the static "Context.getCurrentLogger()" method.

Note also that the are two main types of log record in Restlet:

-   The code related log records, for example used for tracing,
    debugging or error logging
-   The access related log records, used to log the requests received by
    a component (identical to IIS or Apache logs)

Logger names
============

As a single JVM can contain several Restlet components at the same time
and each component can also contain several applications, we need a way
to filter the log records for each application if needed. For this
purpose, we leverage the hierarchical nature of JDK loggers:

-   At the root of all Restlet loggers is the logger named "org.restlet"
-   Below you have a logger per component, based on the simple class
    name. For example "org.restlet.MyComponent".
-   Below you have a logger per application, based on the simple class
    name. For example "org.restlet.MyComponent.MyApplication".
-   Each component also logs access using a name such as
    "org.restlet.MyComponent.LogService". This name can be customized
    using the "LogService.loggerName" property.

It can often be difficult to configure your logging properties file
because you don't always know precisely the logger names. For Restlet
code, everything is under the root "org.restlet" logger so it is
relatively easy.

However, as the Restlet extensions rely on many third-party library, you
need to understand how each one handles logging in order to consistently
configure you logging. Many of them rely on [Apache Commons Logging
API](http://web.archive.org/web/20090126045410/http://commons.apache.org/logging/)
as a neutral API that can plug implementations such as
[Log4J](http://web.archive.org/web/20090126045410/http://logging.apache.org/log4j/)
or JDK Logging. Other use the neutral SLF4J, but in most of the cases,
it is possible to redirect those alternative logging mechanisms to the
JDK logging one.

Here is an attempt to list the logger names used by those libraries.
Please help us to complete this table.

Library

Package name

Logger name

Comment

db4o

com.db4o

Grizzly

com.sun.grizzly

Bloat

edu.purdue.cs.bloat

Java Activation

javax.activation

Java Mail

javax.mail

Java Servlet

javax.servlet

JAX-RS

javax.ws.rs

JAXB

javax.xml.bind

StAX

javax.xml.stream

JLine

jline

JXTA

net.jxta

OAuth

net.oauth

AntLR

org.antlr.runtime

Commons Codec

org.apache.commons.codec

Commons Collections

org.apache.commons.collections

Commons DBCP

org.apache.commons.dbcp

Commons FileUpload

org.apache.commons.fileupload

Commons HTTP Client

org.apache.commons.httpclient

org.apache.commons.httpclient.\*\
 httpclient.wire.\*

For more details, see the [logging documentation
page](http://web.archive.org/web/20090126045410/http://hc.apache.org/httpclient-3.x/logging.html).

Commons IO

org.apache.commons.io

Commons Lang

org.apache.commons.lang

Commons Logging

org.apache.commons.logging

Commons Pool

org.apache.commons.pool

MINA

org.apache.mina

Velocity

org.apache.velocity

Bouncy Castle

org.bouncycastle

FreeMarker

freemarker

JiBX

org.jibx.runtime

JSON

org.json

JUnit

junit

Jetty

org.mortbay.jetty

org.mortbay.\*

Jetty chooses its logger from the property\
 "org.mortbay.log.class", biased towards SLF4J but overrideable

Simple

simple

Spring

org.springframework

org.springframework.\*

Commons logging

Tanuki Wrapper

org.tanukisoftware.wrapper

If the logger name you are looking for isn't listed, there is an easy
way to detect it. You just have to call the static
"com.noelios.restlet.util.TraceHandler.register()" method at the
beginning of your program. It will replace the default console handler
by a more compact one that will display the logger name for each log
record received by the root logger (i.e. all the log records).

Sample configuration
====================

As a starting point for your own logging properties file, here is the
one we use on our Restlet base Web server. Feel free to copy and paste
as needed.

    # ================================
    # ==                            ==
    # ==   Web Logging Properties   ==
    # ==                            ==
    # ================================


    # ------------------
    # General properties
    # ------------------


    # This defines a whitespace separated list of class names for handler classes to load and register as handlers on 
    # the root Logger (the Logger named ""). Each class name must be for a Handler class which has a default constructor. 
    # Note that these Handlers may be created lazily, when they are first used.
    handlers=java.util.logging.FileHandler

In this first section, we declare one default handler that will receive
the log records. It is a file handler that will be configured below.

    # ------------------
    # Loggers properties
    # ------------------

    .level=WARNING
    org.mortbay.level=WARNING
    org.restlet.level=WARNING
    com.noelios.level=WARNING

    com.noelios.web.WebComponent.www.level=INFO
    com.noelios.web.WebComponent.www.handlers=com.noelios.restlet.util.AccessLogFileHandler
    com.noelios.web.WebComponent.www.useParentHandlers=false

In this second section, we indicate that by default we are only
interested in log records with a WARNING level. We also configure the
Mortbay's Jetty, Restlet API and NRE log levels to WARNING. This isn't
necessary here but it can be useful when you need to lower the level of
only one of them at some point, for debugging purpose for example.

We also configured a logger for the WWW access log of our Restlet
component. For information, our Component subclass has this code in its
constructor:

    getLogService().setLoggerName("com.noelios.web.WebComponent.www");

Also note that we use a specific handler for this logger, the
AccessLogFileHandler which is provided in the NRE. It can be easily
configurer to produce Apache-style HTTP log files.

    # -------------------------
    # ConsoleHandler properties
    # -------------------------

    # Specifies the default level for the Handler  (defaults to Level.INFO).
    # java.util.logging.ConsoleHandler.level=WARNING

    # Specifies the name of a Filter class to use (defaults to no Filter).
    # java.util.logging.ConsoleHandler.filter=

    # Specifies the name of a Formatter class to use (defaults to java.util.logging.SimpleFormatter).
    # java.util.logging.ConsoleHandler.formatter=

    # The name of the character set encoding to use (defaults to the default platform encoding).
    # java.util.logging.ConsoleHandler.encoding=

In the section above we have disabled the default ConsoleHandler
configuration as we don't use it on our server-side application.

    # ------------------------------
    # General FileHandler properties
    # ------------------------------

    # Specifies the default level for the Handler  (defaults to Level.ALL).
    # java.util.logging.FileHandler.level=ALL

    # Specifies the name of a Filter class to use (defaults to no Filter).
    # java.util.logging.FileHandler.filter= 

    # Specifies the name of a Formatter class to use (defaults to java.util.logging.XMLFormatter)
    java.util.logging.FileHandler.formatter=java.util.logging.SimpleFormatter

    # The name of the character set encoding to use (defaults to the default platform encoding).
    # java.util.logging.FileHandler.encoding=

    # Specifies an approximate maximum amount to write (in bytes) to any one file. 
    # If this is zero, then there is no limit. (Defaults to no limit).
    java.util.logging.FileHandler.limit=10000000

    # Specifies how many output files to cycle through (defaults to 1).
    java.util.logging.FileHandler.count=100

    # Specifies a pattern for generating the output file name. (Defaults to "%h/java%u.log").
    # A pattern consists of a string that includes the following special components that will be replaced at runtime:
    #    "/" the local pathname separator
    #    "%t" the system temporary directory
    #    "%h" the value of the "user.home" system property
    #    "%g" the generation number to distinguish rotated logs
    #    "%u" a unique number to resolve conflicts
    #    "%%" translates to a single percent sign "%" 
    java.util.logging.FileHandler.pattern=/home/prod/data/log/WebComponent-app-%u-%g.log

    # Specifies whether the FileHandler should append onto any existing files (defaults to false).
    # java.util.logging.FileHandler.append=

Here we specify the file size limit, the number of rotation files (100)
and the file name template.

    # -------------------------
    # LogFileHandler properties
    # -------------------------

    # Specifies the default level for the Handler  (defaults to Level.ALL).
    # com.noelios.restlet.util.AccessLogFileHandler.level=ALL

    # Specifies the name of a Filter class to use (defaults to no Filter).
    # com.noelios.restlet.util.AccessLogFileHandler.filter= 

    # Specifies the name of a Formatter class to use (defaults to java.util.logging.XMLFormatter)
    com.noelios.restlet.util.AccessLogFileHandler.formatter=com.noelios.restlet.util.AccessLogFormatter

    # The name of the character set encoding to use (defaults to the default platform encoding).
    # com.noelios.restlet.util.AccessLogFileHandler.encoding=

    # Specifies an approximate maximum amount to write (in bytes) to any one file. 
    # If this is zero, then there is no limit. (Defaults to no limit).
    com.noelios.restlet.util.AccessLogFileHandler.limit=10000000

    # Specifies how many output files to cycle through (defaults to 1).
    com.noelios.restlet.util.AccessLogFileHandler.count=50

    # Specifies a pattern for generating the output file name. (Defaults to "%h/java%u.log").
    # A pattern consists of a string that includes the following special components that will be replaced at runtime:
    #    "/" the local pathname separator
    #    "%t" the system temporary directory
    #    "%h" the value of the "user.home" system property
    #    "%g" the generation number to distinguish rotated logs
    #    "%u" a unique number to resolve conflicts
    #    "%%" translates to a single percent sign "%" 
    com.noelios.restlet.util.AccessLogFileHandler.pattern=/home/prod/data/log/WebComponent-www-%u-%g.log

    # Specifies whether the FileHandler should append onto any existing files (defaults to false).
    # com.noelios.restlet.util.AccessLogFileHandler.append=

This is similar to the previous section, but specific to our NRE
AccessLogFileHandler log handler. This let's us use a specific log
formatter called AccessLogFormatter, also provided by the NRE.

Replacing default JDK logging with log4j
========================================

Some users that prefer to use log4j instead of the java.util.logging
API, especially because it has more features ready to be leveraged and
what seems to bit a more flexible configuration language. Sometimes,
Restlet has just to integrate into an existing loggin strategy.

In these cases, we recommand the usage of the JUL to log4j bridge. One
possibility is to use the [bridge provided by the SLF4J
project](http://web.archive.org/web/20090126045410/http://www.slf4j.org/legacy.html).
Once it is installed, you will just need to add these lines of code and
configure log4j properly:

     import org.slf4j.bridge.SLF4JBridgeHandler;
     SLF4JBridgeHandler.install();

Additional resources
====================

-   [Java Logging API and How To Use
    It](http://web.archive.org/web/20090126045410/http://www.crazysquirrel.com/computing/java/logging.jspx)

[Comments
(0)](http://web.archive.org/web/20090126045410/http://wiki.restlet.org/docs_1.1/13-restlet/48-restlet/101-restlet.html#)

Comments
[Hide](http://web.archive.org/web/20090126045410/http://wiki.restlet.org/docs_1.1/13-restlet/48-restlet/101-restlet.html#)
\
There are no comments.

Add a comment

Please log in to be able to add comments.
