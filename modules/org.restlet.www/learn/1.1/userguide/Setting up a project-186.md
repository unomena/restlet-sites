Setting up a project
====================

Client Side
===========

To use Restlet-GWT on the client side of your GWT application (Figure
2-5 above):

​1) Create an application
[normally](http://web.archive.org/web/20100429055116/http://code.google.com/webtoolkit/gettingstarted.html)
with the applicationCreator and/or projectCreator scripts supplied with
GWT, or using your favorite GWT design or IDE plugins.

​2) Add the Restlet-GWT JAR (org.restlet.gwt.jar) to the project
classpath *^[explain]^*

​3) Add the following to your application's module definition file
(*yourapp*.gwt.xml):

    <inherits name='org.restlet.GWT'/>

This will make the Restlet-GWT API available to your GWT compiled code. 
The Restlet-GWT module in turn inherits the GWT standard
[HTTP](http://web.archive.org/web/20100429055116/http://google-web-toolkit.googlecode.com/svn/javadoc/1.4/com/google/gwt/http/client/package-summary.html),
[JSON](http://web.archive.org/web/20100429055116/http://google-web-toolkit.googlecode.com/svn/javadoc/1.4/com/google/gwt/json/client/package-summary.html),
and
[XML](http://web.archive.org/web/20100429055116/http://google-web-toolkit.googlecode.com/svn/javadoc/1.4/com/google/gwt/xml/client/package-summary.html)
modules. You can also check the [full Javadocs of the API
online](http://web.archive.org/web/20100429055116/http://www.restlet.org/documentation/snapshot/gwt/).

Hosted Mode
===========

If you would like to debug your application in hosted mode using Restlet
on the server side as well (Figure 4 above), you must also modify the
GWT-generated `web.xml` file in hosted mode's
`%projectHome%/tomcat/webapps/WEB-INF` directory (note that this
directory is generated dynamically by GWT when launching the hosted mode
for the first time). 
[GwtShellServletWrapper](http://web.archive.org/web/20100429055116/http://www.restlet.org/documentation/snapshot/ext/com/noelios/restlet/ext/gwt/GwtShellServletWrapper.html),
provided by the [Noelios GWT
Extension](http://web.archive.org/web/20100429055116/http://www.restlet.org/documentation/snapshot/ext/com/noelios/restlet/ext/gwt/package-summary.html)
(com.noelios.restlet.ext.gwt\_1.5.jar) extends
[ServerServlet](http://web.archive.org/web/20100429055116/http://www.restlet.org/documentation/snapshot/ext/com/noelios/restlet/ext/servlet/ServerServlet.html)
and shares its semantics, but also knows how to pass Hosted Mode
requests to the GWT Shell Servlet.

​1) Add com.noelios.restlet.ext.gwt\_1.5.jar, org.restlet.jar,
com.noelios.restlet.jar, and com.noelios.restlet.ext.servlet\_2.5.jar to
the project classpath ^*[explain]*^.  Also, be sure to add any other
Restlet extension JARs necessary for extensions you plan to use on the
server side.

​2) Modify the web.xml to use the GwtShellServletWrapper:

    <?xml version="1.0" encoding="UTF-8"?>
    <web-app>

        <servlet>
            <servlet-name>adapter</servlet-name>
            <servlet-class>com.noelios.restlet.ext.gwt.GwtShellServletWrapper</servlet-class>
            <init-param>
              <param-name>org.restlet.application</param-name>
              <param-value>application</param-value>
            </init-param>
            <init-param>
              <param-name>module</param-name>
              <param-value>module</param-value>
            </init-param>
        </servlet>
        
        <servlet-mapping>
            <servlet-name>adapter</servlet-name>
            <url-pattern>/*</url-pattern>
        </servlet-mapping>

    </web-app>

For *application*, supply the name of your Restlet Application, e.g.
`org.restlet.test.gwt.server.TestApplication`.  You can also supply a
*component* via an `org.restlet.component` parameter, or any other
permitted ServerServlet configuration parameter.

The *module* should be the name of your GWT module as seen in the hosted
mode URI path.  This is the package name plus the base filename of your
GWT module file, e.g. `org.restlet.test.gwt.TestClient`.

The web.xml file can be overwritten by GWT Hosted Mode in some
situations.  If your Restlet server side functionality fails and you
start receiving messages about server requests being incorrectly
dispatched to an unknown module, check that the web.xml file still
contains valid contents.

[Comments
(2)](http://web.archive.org/web/20100429055116/http://wiki.restlet.org/docs_1.1/13-restlet/144-restlet/186-restlet.html#)

Comments
[Hide](http://web.archive.org/web/20100429055116/http://wiki.restlet.org/docs_1.1/13-restlet/144-restlet/186-restlet.html#)
\

Created by Martin Pernollet on 3/9/09 7:10:47 PM

The weird thing is that your servlet wrapper must have the module name
as input parameter. It's like having to define who will be the clients
of a server in advance!\
 \
The web.xml file of standard GWT project does not imply to define which
GWT module will be using connecting to the servlet. What is the reason
for such a difference? \
 \
A concrete problem is that I sometime have several modules into a single
Eclipse project, and I have to change the web.xml each time I change
module.\
 \
Thanks in advance for your explanations.

Created by Jerome Louvel on 3/17/09 10:49:44 AM

Hi Martin,\
 \
The wrapper must distinguish between requests for Restlet and requests
for GWT. The current criteria is to look for a GWT module name in the
path. \
 \
We could provide other criteria such as the path base for Restlet
applications. All other requests would go to GWT.\
 \
I suggest that you post your message/RFE to our mailing list where other
GWT users/developers might help:\
http://www.restlet.org/community/lists\
 \
Best regards,\
Jerome\

Add a comment

Please log in to be able to add comments.
