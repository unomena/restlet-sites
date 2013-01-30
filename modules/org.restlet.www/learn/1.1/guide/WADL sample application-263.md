WADL sample application
=======================

Documenting the "firstResource" sample application
==================================================

This sample code is an extension of the ["firstResource" sample
application](http://web.archive.org/web/20091124115935/http://www.restlet.org/documentation/1.1/firstResource).
The source code is available in the "org.restlet.example" extension,
more precisely in the "org.restlet.ext.wadl" package.

The aim is to provide WADL documentation for this application.
Basically, we must be able to generate a proper documentation for:

-   the whole application
-   each single defined resource, that is to say the "ItemsResource" and
    "ItemResource".

Here is the list of modifications.

FirstResourceApplication class
------------------------------

-   Make the FirstResourceApplication class a subclass of
    WADLApplication.
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

BaseResource class
------------------

-   Make the BaseResource class a subclass of WADLResource, in order to
    provide the WADL features to all inheriting resources.

ItemsResource
-------------

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

ItemResource
------------

-   Implement the "describe" method,in order to set a proper title.

<!-- -->

        @Override
        public Representation describe() {
            setTitle("Representation a single item");
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

Getting the documentation
=========================

The documentation is available via the OPTIONS method. and is available
under 2 formats: WADL or HTML.

WADL documentation
------------------

Here is a way to programmatically obtain the WADL documentation of the
application:

    Client client = new Client(Protocol.HTTP);

    // Displays the WADL documentation of the application
    client.options("http://localhost:8182/firstResource").getEntity().write(System.out);

Here is a way to programmatically obtain the WADL documentation of the
"items" resource:

    Client client = new Client(Protocol.HTTP);

    // Displays the WADL documentation of the application
    client.options("http://localhost:8182/firstResource/items").getEntity().write(System.out);

HTML documentation
------------------

This format is an XML transformation of the WADL representation with an
XSL sheet, developped and maintained by  Mark Nottingham:
[here](http://web.archive.org/web/20091124115935/http://www.mnot.net/blog/2007/11/02/wadl_stylesheet)

Here is a way to programmatically obtain the HTML documentation of the
application:

    Client client = new Client(Protocol.HTTP);

    // Displays the HTML documentation of the "item1" resource
    // Make sure to have a proper transformation engine (it has been tested
    // successfully with xalan 1.2.7)
    Request request = new Request(Method.OPTIONS, "http://localhost:8182/");
    request.getClientInfo().getAcceptedMediaTypes().add(new Preference<MediaType>(MediaType.TEXT_HTML));
    client.handle(request).getEntity().write(System.out);

NB: in order to work properly, you will certainly have to updated your
classpath with the archove of a convenient transformation engine. Xalan
2.7.1 has been successfully tested.

[Comments
(0)](http://web.archive.org/web/20091124115935/http://wiki.restlet.org/docs_1.1/13-restlet/28-restlet/72-restlet/263-restlet.html#)

Comments
[Hide](http://web.archive.org/web/20091124115935/http://wiki.restlet.org/docs_1.1/13-restlet/28-restlet/72-restlet/263-restlet.html#)
\
There are no comments.

Add a comment

Please log in to be able to add comments.
