# Introduction

This extension support
[WADL](http://en.wikipedia.org/wiki/Web_Application_Description_Language),
the Web Application Description Language. It allows you to configure a
Restlet component or application based on a WADL/XML document or to
dynamically generate the WADL/XML document of an existing Restlet
application. It also knows how to convert this WADL/XML document into a
user friendly HTML document.

For additional details, please consult the
[Javadocs](javadocs://jse/ext/org/restlet/ext/wadl/package-summary.html).

# Usage example

This sample code is an extension of the ["firstResource" sample
application](javadocs://firstResource).
The source code is available in the "org.restlet.example" extension,
more precisely in the "org.restlet.ext.wadl" package.

The aim is to provide WADL documentation for this application.
Basically, we must be able to generate a proper documentation for:

-   the whole application
-   each single defined resource, that is to say the "ItemsResource" and
    "ItemResource".

Here is the list of modifications.

## FirstResourceApplication class

-   Make the FirstResourceApplication class a subclass of
    WadlApplication.
-   Implement the "getApplicationInfo" method in order to add a title
    and a simple description:

<!-- -->

        @Override
        public ApplicationInfo getApplicationInfo(Request request, Response response) {
            ApplicationInfo result = super.getApplicationInfo(request, response);

            DocumentationInfo docInfo = new DocumentationInfo(
                    "This sample application shows how to generate online documentation.");
            docInfo.setTitle("First resource sample application.");
            result.setDocumentation(docInfo);

            return result;
        }

## BaseResource class

-   Make the BaseResource class a subclass of WADLServerResource, in
    order to provide the WADL features to all inheriting resources.

## ItemsResource

-   Implement the "describe" method,in order to set a proper title.

<!-- -->

        @Override
        protected Representation describe() {
            setTitle("List of items.");
            return super.describe();
        }

-   Implement the"describeGet" method

<!-- -->

        @Override
        protected void describeGet(MethodInfo info) {
            info.setIdentifier("items");
            info.setDocumentation("Retrieve the list of current items.");

            RepresentationInfo repInfo = new RepresentationInfo(MediaType.TEXT_XML);
            repInfo.setXmlElement("items");
            repInfo.setDocumentation("List of items as XML file");
            info.getResponse().getRepresentations().add(repInfo);
        }

-   Implement the "describePost" method

<!-- -->

        @Override
        protected void describePost(MethodInfo info) {
            info.setIdentifier("create_item");
            info.setDocumentation("To create an item.");

            RepresentationInfo repInfo = new RepresentationInfo(
                    MediaType.APPLICATION_WWW_FORM);
            ParameterInfo param = new ParameterInfo("name", ParameterStyle.PLAIN,
                    "Name of the item");
            repInfo.getParameters().add(param);
            param = new ParameterInfo("description", ParameterStyle.PLAIN,
                    "Description of the item");
            repInfo.getParameters().add(param);
            repInfo.getStatuses().add(Status.SUCCESS_CREATED);

            repInfo.setDocumentation("Web form.");
            info.getRequest().getRepresentations().add(repInfo);

            FaultInfo faultInfo = new FaultInfo(Status.CLIENT_ERROR_NOT_FOUND);
            faultInfo.setIdentifier("itemError");
            faultInfo.setMediaType(MediaType.TEXT_HTML);
            info.getResponse().getFaults().add(faultInfo);
        }

## ItemResource

-   Implement the "describe" method,in order to set a proper title.

<!-- -->

        @Override
        public Representation describe() {
            setTitle("Representation of a single item");
            return super.describe();
        }

-   Implement the"describeGet" method

<!-- -->

        @Override
        protected void describeGet(MethodInfo info) {
            info.setIdentifier("item");
            info.setDocumentation("To retrieve details of a specific item");

            RepresentationInfo repInfo = new RepresentationInfo(MediaType.TEXT_XML);
            repInfo.setXmlElement("item");
            repInfo.setDocumentation("XML representation of the current item.");
            info.getResponse().getRepresentations().add(repInfo);

            FaultInfo faultInfo = new FaultInfo(Status.CLIENT_ERROR_NOT_FOUND,
                    "Item not found");
            faultInfo.setIdentifier("itemError");
            faultInfo.setMediaType(MediaType.TEXT_HTML);
            info.getResponse().getFaults().add(faultInfo);
        }

-   Implement the "describeDelete" method

<!-- -->

        @Override
        protected void describeDelete(MethodInfo info) {
            info.setDocumentation("Delete the current item.");

            RepresentationInfo repInfo = new RepresentationInfo();
            repInfo.setDocumentation("No representation is returned.");
            repInfo.getStatuses().add(Status.SUCCESS_NO_CONTENT);
            info.getResponse().getRepresentations().add(repInfo);
        }

-   Implement the "describePut" method

<!-- -->

        @Override
        protected void describePut(MethodInfo info) {
            info.setDocumentation("Update or create the current item.");

            RepresentationInfo repInfo = new RepresentationInfo(
                    MediaType.APPLICATION_WWW_FORM);
            ParameterInfo param = new ParameterInfo("name", ParameterStyle.PLAIN,
                    "Name of the item");
            repInfo.getParameters().add(param);
            param = new ParameterInfo("description", ParameterStyle.PLAIN,
                    "Description of the item");
            repInfo.getParameters().add(param);
            repInfo.getStatuses().add(Status.SUCCESS_OK);
            repInfo.getStatuses().add(Status.SUCCESS_CREATED);

            repInfo.setDocumentation("Web form.");
            info.getRequest().getRepresentations().add(repInfo);

            super.describePut(info);
        }

# Getting the documentation

The documentation is available via the OPTIONS method. and is available
under 2 formats: WADL or HTML.

## WADL documentation

Here is a way to programmatically obtain the WADL documentation of the
application:

    ClientResource app = new ClientResource("http://localhost:8182/firstResource");

    // Displays the WADL documentation of the application
    app.options().write(System.out);

Here is a way to programmatically obtain the WADL documentation of the
"items" resource:

    ClientResource items = new ClientResource("http://localhost:8182/firstResource/items");

    // Displays the WADL documentation of the application
    items.options().write(System.out);

## HTML documentation

This format is an XML transformation of the WADL representation with an
XSL sheet, developped and maintained by  Mark Nottingham:
[here](http://www.mnot.net/blog/2007/11/02/wadl_stylesheet)

Here is a way to programmatically obtain the HTML documentation of the
application:

    ClientResource app = new ClientResource("http://localhost:8182/firstResource");

    // Displays an HTML documentation of the application
    app.options(MediaType.TEXT_HTML).write(System.out);

In order to work properly, you will certainly have to update your
classpath with the archive of a convenient transformation engine. Xalan
2.7.1 has been successfully tested.

# Advanced topics

## Indicate schema relative to an XML representation

### A bit of theory

Quoting the WADL specification:

    The "grammars" element acts as a container for definitions of the format of data
    exchanged during execution of the protocol described by the WADL document. Such
    definitions may be included inline or by reference using the include element.

For example:

    <grammars>
      <include href="NewsSearchResponse.xsd"/>
      <include href="Error.xsd"/>
    </grammars>

At this time, the WADL extension of the Restlet framework supports only
"included" and not "inline" schemas via the GrammarsInfo\#includes
attribute.

Then, for XML-based representations, the "element" attribute specifies
the qualified name of the root element as described within the grammars
section.

For example:

    <representation mediaType="application/xml" element="yn:ResultSet"/>

Assuming that the "yn" namespace is declared in the document:

    <application [...] xmlns:yn="urn:yahoo:yn" >

### Implementation with Restlet

At the level of the subclass of WadlApplication, override the
getApplicationInfo method:

    @Override
    public ApplicationInfo getApplicationInfo(Request request, Response response) {
        ApplicationInfo appInfo = super.getApplicationInfo(request, response);
        appInfo.getNamespaces().put("urn:yahoo:yn", "yn");
        GrammarsInfo grammar = new GrammarsInfo();
        IncludeInfo include = new IncludeInfo();
        include.setTargetRef(new Reference("NewsSearchResponse.xsd"));
        grammar.getIncludes().add(include);
        appInfo.setGrammars(grammar);
        return appInfo;
    }

Then, at the level of the subclass of WadlResource, update the
RepresentationInfo\#element attribute:

    RepresentationInfo formRepresentation = new RepresentationInfo();
    formRepresentation.setXmlElement("yn:ResultSet");
