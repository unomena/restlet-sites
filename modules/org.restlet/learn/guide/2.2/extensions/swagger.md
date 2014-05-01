Swagger extension
=================

This extension provides a preview integration with Swagger including:
* generation of Swagger descriptor in JSON
* introspect JAX-RS API based applications

Additional work is required to:
* ship the Swagger UI
* parse a Swagger descriptor in JSON to set up an application
* extend the introspection to Restlet API based applications


Sample code
===========

A simpe source code is available [here](/learn/archives/examples/swagger/${restlet-version-minor}/org.restlet.example.swagger-jaxrs.zip): 

One side, develop your JaxRs based application, and document it using Swagger annotations.

```java
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@Path("/hello")
@Api(value = "/hello", description = "This is a sample \"Hello, world\" resource")
public class HelloWorldServerResource {

    @GET
    @Produces("text/plain")
    @ApiOperation(value = "hello", notes = "Returns the representation of hello resource.")
    public String hello() {
        return "hello, world";
    }
}

```

Add this resource us usual to your JaxRs application:

```java
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

public class SampleJaxrApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(HelloWorldServerResource.class);
        return classes;
    }

}
```


Then, finally, attach your application, and the Swagger documentation endpoint:

```java
import javax.ws.rs.core.Application;

import org.restlet.Component;
import org.restlet.data.Protocol;
import org.restlet.ext.jaxrs.JaxRsApplication;
import org.restlet.ext.swagger.SwaggerUiRestlet;

public class SampleServer {
    public static void main(String[] args) throws Exception {
        // create the application container
        Component comp = new Component();
        comp.getServers().add(Protocol.HTTP, 8182);

        // create JAX-RS runtime environment
        JaxRsApplication application = new JaxRsApplication();
        // attach ApplicationConfig
        Application app = new SampleJaxrApplication();
        application.add(app);

        // attach the application to the component and start it
        comp.getDefaultHost().attach("/v1", application);

        // then, add the Swagger documentation endpoint
        String basePath = "http://example.com:8182";
        String docPath = "/api-doc";

        SwaggerUiRestlet apiDoc = new SwaggerUiRestlet();
        // specify the documented application
        apiDoc.setApiInboundRoot(application);
        // add extra documentation
        apiDoc.setApiVersion("1.0.0");
        apiDoc.setAuthor("Restlet Inc.");
        apiDoc.setBasePath(basePath + "/v1");
        apiDoc.setJsonPath(basePath + docPath);
        // attach the Swagger documentation endpoint
        comp.getDefaultHost().attach(docPath, apiDoc);

        comp.start();
    }
}
```

Then invoke this api using swagger-ui by specifying the URL of the Swagger documentation endpoint:
```
http://example.com:8182/api-doc
```


For additional details, please consult the
[Javadocs](javadocs://jse/ext/org/restlet/ext/swagger/package-summary.html).
