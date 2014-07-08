Swagger extension
=================

Introduction
============

This extension provides a preview integration with Swagger including:
- generation of Swagger descriptor in JSON
- introspect JAX-RS API based applications
- introspect Restlet API based applications

Additional work is required to:
- parse a Swagger descriptor in JSON to set up an application

In this scenario, we will add Swagger support to a Restlet based API then display its Swagger-UI.

Usage
=====

You must add the following jar (provided in the "lib" directory of
[restlet framework](http://restlet.com/download/current#release=stable&edition=jse&distribution=zip 
"download restlet framework")) to your classpath: 

- org.restlet.ext.swagger

Make your application class extend org.restlet.ext.swagger.SwaggerApplication instead of org.restlet.Application.

By default, the Swagger documentation will be available on the path "/api-docs" of your API. If you want to change this path, you can specify it manually in the method _createInboundRoot_: 

```java
public Restlet createInboundRoot() {
        // Router for the API's resources
        Router apiRouter = createApiRouter();
        attachSwaggerSpecificationRestlet(apiRouter, "/docs");
        // Protect the set of resources
        ChallengeAuthenticator guard = createApiGuard(apiRouter);
        return guard;
    }

```

Here, you specify that the Swagger definition will be provided on the path "/docs".

Swagger-UI
==========

To display the Swagger-UI of your API, go on the page http://petstore.swagger.wordnik.com, enter the URL of your Swagger definition and click on explore.

![swagger-ui](/modules/com.restlet/learn/archives/images/swaggerExtensionSwaggerUI.png)

For additional details, please consult the
[Javadocs](javadocs://jse/ext/org/restlet/ext/swagger/package-summary.html).
