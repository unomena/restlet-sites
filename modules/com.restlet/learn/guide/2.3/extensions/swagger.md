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

Customization
=============

If you want to add information to your Swagger definition, to get them from files or any other customization, you can easily change the behavior of the Restlet providing the definition in the SwaggerApplication class by overriding the method _getwaggerSpecificationRestlet()_. Here is how to proceed, for this example, we will get the Swagger definition from files:

```java

@Override
    public SwaggerSpecificationRestlet getSwaggerSpecificationRestlet(
            Context context) {
        return new SwaggerSpecificationRestlet(getContext()) {
            @Override
            public Representation getApiDeclaration(String category) {
                JacksonRepresentation<ApiDeclaration> result = new JacksonRepresentation<ApiDeclaration>(
                        new FileRepresentation("/path/to/my/repo/" + category,
                                MediaType.APPLICATION_JSON),
                        ApiDeclaration.class);
                return result;
            }

            @Override
            public Representation getResourceListing() {
                JacksonRepresentation<ApiDeclaration> result = new JacksonRepresentation<ApiDeclaration>(
                        new FileRepresentation("/path/to/my/repo/api-docs",
                                MediaType.APPLICATION_JSON),
                        ApiDeclaration.class);
                return result;
            }
        };
    }

```

Swagger-UI
==========

To display the Swagger-UI of your API, go on the page http://petstore.swagger.wordnik.com, enter the URL of your Swagger definition and click on explore.

![swagger-ui](/modules/com.restlet/learn/archives/images/swaggerExtensionSwaggerUI.png)

For additional details, please consult the
[Javadocs](javadocs://jse/ext/org/restlet/ext/swagger/package-summary.html).
