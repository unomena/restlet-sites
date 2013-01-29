Client connectors
=================

Introduction
============

It is possible to declare client connectors when the application is
hosted on a Servlet container. The Javadocs of the Servlet adapter
([ServerServlet](http://web.archive.org/web/20111118102610/http://www.restlet.org/documentation/2.0/jee/ext/org/restlet/ext/servlet/ServerServlet.html)
class) answers this question and others related to the configuration of
Restlet based applications.

The web.xml file declares the client connectors in a dedicated
"org.restlet.clients" parameter:

    <servlet>
        <servlet-name>RestletAdapter</servlet-name>
        [...] 
        <!-- List of supported client protocols (Optional - Only in mode 3) -->
        <init-param>
            <param-name>org.restlet.clients</param-name>
            <param-value>HTTP HTTPS FILE</param-value>
        </init-param>
        [...] 
    </servlet>

[Comments
(0)](http://web.archive.org/web/20111118102610/http://wiki.restlet.org/docs_2.1/13-restlet/275-restlet/312-restlet/379-restlet.html#)

Comments
[Hide](http://web.archive.org/web/20111118102610/http://wiki.restlet.org/docs_2.1/13-restlet/275-restlet/312-restlet/379-restlet.html#)
\
There are no comments.

Add a comment

Please log in to be able to add comments.
