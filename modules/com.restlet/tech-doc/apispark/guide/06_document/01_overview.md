
APISpark can enhance the way you document an existing web API hosted outside the APISpark platform.

# Enhanced API documentation

APISpark can help you document an existing API in the following ways:

* Automatically generated **documentation**  
* Automatically generated **client SDKs** and **server skeletons**

# The web API Descriptor

A web API **Descriptor** is a type of APISpark cell that documents a web API. Many features are then accessible via the Descriptor. 

You may create a web API Descriptor by introspecting existing code (Restlet framework, JAX_RS) or by importing an existing web API definition in the Swagger format.


Similarly, an APISpark Descriptor can be automatically translated to the Swagger or RAML formats.

A Descriptor can be edited by members of the team that provide the described API. On the other hand, the Descriptor can be consulted by consumers of the API, who can also benefit from the elements derived from the Descriptor such as client SDKs.

# The process of documenting your external API

Here is a summary of the process that allows you to document an existing API using APISpark's **Descriptor**:

1. Develop web API (outside the APISpark platform)
2. Create a Descriptor by introspection, or by importing a Swagger definition
3. Maintain the Descriptor either by hand or automatically via introspection
4. Generate documentation + SDKs for your API consumers to download

In the following sections, we'll show you how to manually create and edit a web API Descriptor. Then we'll explain how to automatically generate a web API Descriptor from your existing web API implementation.
