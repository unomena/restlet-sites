Restlet edition for Java SE
===========================

Introduction
============

This chapter presents the Restlet Framework edition for Java SE (Java
Standard Edition).

This edition is aimed for development and deployment of Restlet
applications inside a regular Java virtual machine using the internal
HTTP server of the Restlet Engine, or a pluggable one such as Jetty.
[This
page](/learn/guide/2.1#/13-restlet/27-restlet/325-restlet/37-restlet.html "Connectors")
contains a detailed list of available HTTP server connectors.

Getting started
===============

The rest of this page should get you started with the Restlet Framework,
Java SE edition, in less than 10 minutes. It explains how to create a
resource that says "hello, world" and run it.

1.  [What do I
    need?](/learn/guide/2.0#/13-restlet/275-restlet/312-restlet/edit/1021428d75520b32821c6a676e4d5f442f074c17/part-SimpleDocumentContent#requirements)
2.  [The "hello, world"
    application](/learn/guide/2.0#/13-restlet/275-restlet/312-restlet/edit/1021428d75520b32821c6a676e4d5f442f074c17/part-SimpleDocumentContent#application)
3.  [Run as a standalone Java
    application](/learn/guide/2.0#/13-restlet/275-restlet/312-restlet/edit/1021428d75520b32821c6a676e4d5f442f074c17/part-SimpleDocumentContent#standaloneDeployment)
4.  [Conclusion](/learn/guide/2.0#/13-restlet/275-restlet/312-restlet/edit/1021428d75520b32821c6a676e4d5f442f074c17/part-SimpleDocumentContent#conclusion)

What do I need?
---------------

We assume that you have a development environment set up and
operational, and that you already have installed the Java 1.5 (or
higher). In case you haven't downloaded the Restlet Framework yet,
select one of the available distributions of the [Restlet Framework
2.0](http://restlet.org/downloads).

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

Run as a standalone Java application
------------------------------------

A Restlet application can run inside a regular Java virtual machine or
Java Runtime Environment (JRE), using a single "org.restlet.jar" JAR in
the classpath. For this we only need to create a Restlet component and
associate a HTTP server connector.

Create also a main class, copy/paste the following code which aims at
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

You can find the sources of this sample application in the
"[FirstStepsStandalone](/learn/guide/2.1#/371-restlet/version/default/part/AttachmentData/data "firstStepsStandalone")
(application/zip, 1.4 kB,
[info](/learn/guide/2.1#/371-restlet.html))"
file.

Conclusion
----------

We hope you that enjoyed these first steps and encourage you to check
[the equivalent page in the Java EE
edition](/learn/guide/2.1#/13-restlet/275-restlet/312-restlet.html "Restlet edition for Java EE")
for deployments of the same application in Servlet containers. This can
also be a convenient way to deploy your Restlet application in an
existing Java EE application server available in your organization.

### Notes

-   Thanks to [Didier
    Girard](http://www.ongwt.com/)
    for suggesting this page.

