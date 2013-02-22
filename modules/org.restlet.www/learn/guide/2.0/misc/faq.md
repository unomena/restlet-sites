FAQ
===

How to use Restlet in an Applet ?
=================================

In version 2.0, the Restlet engine creates its own classloader, instance
of the EngineClassLoader class. This is fine most of the time, except
when a security manager is used, such as for Applets running inside a
sandbox.

The solution is to use a custom Restlet engine as below, that won't
create this new classloader:

    public class AppletEngine extends Engine {

        @Override
        protected ClassLoader createClassLoader() {
            return getClass().getClassLoader();
        }

    }

Now you just need to call this line before using the Restlet API:

    Engine.setInstance(new AppletEngine());

Solve 405 status code responses
===============================

Having set up your server resource with annotated methods, you're ready
to send it requests and eager to get JSON, XML representations of its
state. But unfortunately, it fails. In some cases, you're returned an
error response with a [HTTP 405 status
code.](http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.6)By
definition, it means that the sent method is not supported which keep
you quite disappointed as the resource does declare a "@Get" method!

The solution is all about content negotiation. Let's say your client
requires a JSON representation. Before the resource is sollicitated to
generate the desired representation, the content negotiation algorithm
will detect if the resource is really able to generate a JSON
representation, that is to say there is a Java method in the code of the
server resource which is (A/) properly annotated, and (B/) having a
compatible return type. the case (A/) is easy to understand, let's focus
on case (B/). This may happen when you server resource use annotation
with media type parameters:

~~~~ {.brush: .java}
public class MyResource extends ServerResource {
   @Get("html")
   public String toHtml() {
      return "<html><body>hello, world</body></html>";
   }
}
~~~~

In this case, the client requires a JSON representation but the server
resource is not able to generate it.

This may also happen when you rely on the [converter
service](http://wiki.restlet.org/docs_2.0/13-restlet/27-restlet/331-restlet/360-restlet.html),
and that you don't have properly configured the classpath of your
project. Did you reference the archive of the right Restlet extension
(such as the Jackson extension) and the archives of its library
dependencies?

How to trace the internal client and server connectors?
=======================================================

These connectors are configured via the parameter called "tracing" of
their context (see the
[javadocs](http://www.restlet.org/documentation/2.1/jse/engine/org/restlet/engine/connector/BaseHelper.html)).

Here is a sample code that illustrates how to configure the HTTP  server
connector of a Component:

    Component c = new Component();
    Server s = new Server(Protocol.HTTP, 8182);
    c.getServers().add(s);
    s.getContext().getParameters().add("tracing", "true");

Here is a sample code that illustrates how to configure the HTTP  client
connector of a resource:

    Client client = new Client(new Context(), Protocol.HTTP);
    client.getContext().getParameters().add("tracing", "true");
    ClientResource resource = new ClientResource("http://localhost:8182/<resource>");
    resource.setNext(client);

