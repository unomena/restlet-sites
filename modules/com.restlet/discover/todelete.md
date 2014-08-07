# First Steps

## <a name="toc">Table of contents</a>

This page should allow you to taste the simplicity of the Restlet
Framework in less than 10 minutes. It explains how to create a Resource
that says "hello, world".

1.  [What do I need?](#part01)
2.  [The "hello, world" application](#part02)
3.  [Run in a Servlet container](#part03)
4.  [Run as a standalone Java application](#part04)
5.  [Conclusion](#conclusion)

#### What do I need?

We assume that you have a development environment set up and
operational, and that you already have installed the JRE 1.5 (or
higher). In case you haven't downloaded Restlet yet, select one of the
available distributions of the latest release of the [Restlet
framework](/download/ "Restlet framework").

#### The "hello, world" application.

Let's start with the core of a REST application: the Resource. Here is
the code of the single resource defined by the sample application.
Copy/paste the code in your "HelloWorldResource" class.

~~~~ {.java:nocontrols:nogutter}
package firstSteps;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class HelloWorldResource extends ServerResource {

    @Get
    public String represent() {
        return "hello, world";
    }
}
   
~~~~

Then, create the sample application. Let's call it
"FirstStepsApplication" and copy/paste the following code:

~~~~ {.java:nocontrols:nogutter}
package firstSteps;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class FirstStepsApplication extends Application {

    /**
     * Creates a root Restlet that will receive all incoming calls.
     */
    @Override
    public Restlet createInboundRoot() {
        // Create a router Restlet that routes each call to a
        // new instance of HelloWorldResource.
        Router router = new Router(getContext());

        // Defines only one route
        router.attachDefault(HelloWorldResource.class);

        return router;
    }
}
   
~~~~

#### Run in a Servlet container

As you are probably more familiar with Servlets, we propose you to run
the Restlet application inside your favorite Servlet container. Create a
new Servlet Web application as usual, add a "firstStepsServlet" package
and put the resource and application classes in. Add the archives listed
below into the directory of librairies (/WEB-INF/lib):

-   org.restlet.jar
-   org.restlet.ext.servlet.jar

Then, update the "web.xml" configuration file as follow:

~~~~ {.xml:nocontrols:nogutter}
<?xml version="1.0" encoding="UTF-8"?>
<web-app id="WebApp_ID" version="2.4"
            xmlns="http://java.sun.com/xml/ns/j2ee"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://java.sun.com/xml/ns/j2ee
                 http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd">
   <display-name>first steps servlet</display-name>
   <!-- Application class name -->
   <context-param>
      <param-name>org.restlet.application</param-name>
      <param-value>
         firstSteps.FirstStepsApplication
      </param-value>
   </context-param>

   <!-- Restlet adapter -->
   <servlet>
      <servlet-name>RestletServlet</servlet-name>
      <servlet-class>
         org.restlet.ext.servlet.ServerServlet
      </servlet-class>
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
Once you have launched the Servlet container, kindly open your favourite
web browser, and gently type the following URL: "http://\<your server
name\>:\<its port number\>/firstStepsServlet". The server will happily
welcome you with a nice "hello, world".

#### Run as a standalone Java application

A Restlet application cannot only run inside a Servlet container, but
can also be run as a standalone Java application using a couple of JARs:

-   org.restlet.jar

Create also a main class, copy/paste the following code wich aims at
defining a new HTTP server listening on port 8182 and delegating all
requests to the "FirstStepsApplication".

~~~~ {.java:nocontrols:nogutter}
public static void main(String[] args) {
    try {
        // Create a new Component.
        Component component = new Component();

        // Add a new HTTP server listening on port 8182.
        component.getServers().add(Protocol.HTTP, 8182);

        // Attach the sample application.
        component.getDefaultHost().attach(new FirstStepsApplication());

        // Start the component.
        component.start();
    } catch (Exception e) {
        // Something is wrong.
        e.printStackTrace();
    }
}
   
~~~~

Once you have launched the main class, if you kindly open your favourite
web browser, and gently type the following URL: http://localhost:8182/,
the server will happily welcome you with a nice "hello, world".
Otherwise, make sure that the classpath is correct and that no other
program is currently using the port 8182.

## <a name="conclusion">Conclusion</a>

We hope you that enjoyed these simple steps and we now encourage you to
[move on to the Learn section](/learn/) for more advanced examples.

### <a name="notes">Notes</a>

-   Thanks to Didier Girard for suggesting this page.
