Restlet edition for Java EE
===========================

Introduction
============

This chapter presents the Restlet Framework edition for Java EE (Java
Enterprise Edition).

This edition is aimed for development and deployment of Restlet
applications inside Java EE application server, or more precisely inside
Servlet containers such as [Apache
Tomcat](http://web.archive.org/web/20111218224609/http://tomcat.apache.org/).

Getting started
===============

The rest of this page should get you started with the Restlet Framework,
Java EE edition, in less than 10 minutes. It explains how to create a
resource that says "hello, world" and run it.

1.  [What do I
    need?](http://web.archive.org/web/20111218224609/http://wiki.restlet.org/docs_2.1/13-restlet/275-restlet/312-restlet.html#dsy312-restlet_requirements)
2.  [The "hello, world"
    application](http://web.archive.org/web/20111218224609/http://wiki.restlet.org/docs_2.1/13-restlet/275-restlet/312-restlet.html#dsy312-restlet_application)
3.  [Run in a Servlet
    container](http://web.archive.org/web/20111218224609/http://wiki.restlet.org/docs_2.1/13-restlet/275-restlet/312-restlet.html#dsy312-restlet_servletDeployment)
4.  [Conclusion](http://web.archive.org/web/20111218224609/http://wiki.restlet.org/docs_2.1/13-restlet/275-restlet/312-restlet.html#dsy312-restlet_conclusion)

What do I need?
---------------

We assume that you have a development environment set up and
operational, and that you already have installed the Java 1.5 (or
higher). In case you haven't downloaded the Restlet Framework yet,
select one of the available distributions of the [Restlet Framework
2.0](http://web.archive.org/web/20111218224609/http://www.restlet.org/downloads).

The "hello, world" application
------------------------------

Let's start with the core of a REST application: the Resource. Here is
the code of the single resource defined by the sample application.
Copy/paste the code in your "HelloWorldResource" class.

~~~~ {.brush: .java}
package firstSteps;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

/**
 * Resource which has only one representation.
 */
public class HelloWorldResource extends ServerResource {

    @Get
    public String represent() {
        return "hello, world";
    }

}
~~~~

Then, create the sample application. Let's call it
"FirstStepsApplication" and copy/paste the following code:

~~~~ {.brush: .java}
package firstSteps;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class FirstStepsApplication extends Application {

    /**
     * Creates a root Restlet that will receive all incoming calls.
     */
    @Override
    public synchronized Restlet createInboundRoot() {
        // Create a router Restlet that routes each call to a new instance of HelloWorldResource.
        Router router = new Router(getContext());

        // Defines only one route
        router.attach("/hello", HelloWorldResource.class);

        return router;
    }

}   
~~~~

Run in a Servlet container
--------------------------

Let's now deploy this Restlet application inside your favorite Servlet
container. Create a new Servlet Web application as usual, add a
"firstStepsServlet" package and put the resource and application classes
in. Add the archives listed below into the directory of librairies
(/WEB-INF/lib):

-   org.restlet.jar
-   org.restlet.ext.servlet.jar

Then, update the "web.xml" configuration file as follow:

~~~~ {.brush: .java}
<?xml version="1.0" encoding="UTF-8"?>  
<web-app id="WebApp_ID" version="2.4"  
            xmlns="http://java.sun.com/xml/ns/j2ee"  
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"  
            xsi:schemaLocation="http://java.sun.com/xml/ns/j2ee  
                 http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd">  
   <display-name>first steps servlet</display-name>  
  
   <!-- Restlet adapter -->  
   <servlet>  
      <servlet-name>RestletServlet</servlet-name>  
      <servlet-class>org.restlet.ext.servlet.ServerServlet</servlet-class>
      <init-param>
            <!-- Application class name -->
            <param-name>org.restlet.application</param-name>
            <param-value>firstSteps.FirstStepsApplication</param-value>
      </init-param>
   </servlet>  
  
   <!-- Catch all requests -->  
   <servlet-mapping>  
      <servlet-name>RestletServlet</servlet-name>  
      <url-pattern>/*</url-pattern>  
   </servlet-mapping>  
</web-app>  
~~~~

Finally, package the whole as a WAR file called for example
"firstStepsServlet.war" and deploy it inside your Servlet container.
Once you have launched the Servlet container, open your favorite web
browser, and enter the following URL:

~~~~ {.brush: .java}
http://<your server name>:<its port number>/firstStepsServlet/hello
~~~~

The server will happily welcome you with the expected "hello, world"
message. You can find the WAR file (packaged with archives taken from
Restlet Framework 2.0 Milestone 5) in the "First steps application"
files.

Run as a standalone Java application
------------------------------------

A Restlet application cannot only run inside a Servlet container, but
can also be run as a standalone Java application using a single
"org.restlet.jar" JAR.

Create also a main class, copy/paste the following code wich aims at
defining a new HTTP server listening on port 8182 and delegating all
requests to the "FirstStepsApplication".

~~~~ {.brush: .java}
public static void main(String[] args) throws Exception {  
    // Create a new Component.  
    Component component = new Component();  
  
    // Add a new HTTP server listening on port 8182.  
    component.getServers().add(Protocol.HTTP, 8182);  
  
    // Attach the sample application.  
    component.getDefaultHost().attach("/firstSteps",  
            new FirstStepsApplication());  
  
    // Start the component.  
    component.start();  
}          
~~~~

Once you have launched the main class, if you can open your favorite web
browser, and gently type the following URL:
http://localhost:8182/firstSteps/hello, the server will happily welcome
you with a nice "hello, world". Otherwise, make sure that the classpath
is correct and that no other program is currently using the port 8182.

You can find the sources of this sample application in the "First steps
application" files.

Conclusion
----------

We hope you that enjoyed these first steps and encourage you to check
[the equivalent page in the Java SE
edition](http://web.archive.org/web/20111218224609/http://wiki.restlet.org/docs_2.1/13-restlet/275-restlet/311-restlet.html "Restlet edition for Java SE")
for standalone deployments of the same application. This can also be a
convenient way to develop and test your Restlet application before
actually deploying it in a Java EE application server.

### Notes

-   Thanks to [Didier
    Girard](http://web.archive.org/web/20111218224609/http://www.ongwt.com/)
    for suggesting this page.

[Comments
(4)](http://web.archive.org/web/20111218224609/http://wiki.restlet.org/docs_2.1/13-restlet/275-restlet/312-restlet.html#)

Comments
[Hide](http://web.archive.org/web/20111218224609/http://wiki.restlet.org/docs_2.1/13-restlet/275-restlet/312-restlet.html#)
\

Created by navaneetha bandarapu on 12/1/11 8:48:11 PM

can any body tell what is mean by "make sure that the classpath is
correct" which class path is that? i am having hard time to run
standalone application, but I can run in servlet container
(eclipse/tomcat). Feels this site doesn't provide enough documentation
and it would have nice if they provide some restlet examples with latest
release.

Created by Aries McRae on 12/13/11 7:13:56 AM

Your client (e.g. gwt) will run into "Same Origin Policy" problems ,
unless you allow your restlet server response to set the
Access-Control-Allow-Origin.\
 \
For example, in your GWT client, if you use RequestBuilder to do an HTTP
get on a remote server, the response status code will be 0, rather than
200.\
 \
To get a 200 (i.e. success) response, do the following to your Restlet
class that extends the ServerResource:\
 \
final String RESPONSE\_HEADERS = "org.restlet.http.headers";\
 \
Form responseHeaders = (Form)
getResponse().getAttributes().get(RESPONSE\_HEADERS);\
 \
if (responseHeaders == null) {\
 responseHeaders = new Form();\
 getResponse().getAttributes().put(RESPONSE\_HEADERS, responseHeaders);\
}\
 \
responseHeaders.add("Access-Control-Allow-Origin", "\*");\
 \

Created by Aries McRae on 12/13/11 7:44:05 AM

navaneetha, classpath is not a restlet term, but a general java term.
The documentation above is simply saying that your org.restlet.jar must
be in your classpath. To familiarize yourself with the concept of java
classpath, see this wikipedia article:\
http://en.wikipedia.org/wiki/Classpath\_(Java)\
 \
If you're using the Eclipse EDI, you can include the org.restlet.jar in
your application classpath by going to right click \<your module\> ==\>
Java Build Path ==\> Libraries tab ==\> Add JARS, then point it to the
location of your org.restlet.jar.\
 \
As for including the FirstStepsApplication.java in your classpath, if
you're using the Eclipse IDE, once you compile your
FirstStepsApplication.java, Eclipse will compile it and place it in the
/\<your module name\>bin/ as FirstStepsApplication.class. The directory
/bin/ is your classpath assuming you're using the default eclipse
project creation settings.\
 \

Created by Jerome Louvel on 12/13/11 5:07:24 PM

Thanks Aries for the answer. I've granted you edition rights on the
wiki. Feel free to update the wiki page to add clarifications if
necessary.

Add a comment

Please log in to be able to add comments.
