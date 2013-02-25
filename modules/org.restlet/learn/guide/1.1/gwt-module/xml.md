Working with XML
================

Description
===========

There is a special org.restlet.gwt.resource.XmlRepresentation class that
you can leverage to either parse a XML representation received or to
serialize a XML DOM. In addition, a shortcut
org.restlet.gwt.data.Response\#getEntityAsXml() was added to
automatically wrap the response entity in a XmlRepresentation instance.

Here is a sample code taken from the example application. The
XmlRepresentation gives access to the underlying DOM document via the
"getDocument()" method.

    Request request = new Request(Method.GET, "http://localhost:8888/test");
    // Indicates the client preferences and let the server handle the best representation with content negotiation.
    request.getClientInfo().getAcceptedMediaTypes().add(new Preference<MediaType>(MediaType.TEXT_XML));
    new Client(Protocol.HTTP).handle(request, new Callback() {
            @Override
            public void onEvent(Request request, Response response) {
                    // Get the representation as an XmlRepresentation
                    XmlRepresentation rep = response.getEntityAsXml();

                    // Loop on the nodes to retrieve the node names and text content.
                    NodeList nodes = rep.getDocument().getDocumentElement().getChildNodes();
                    for (int i = 0; i < nodes.getLength(); i++) {
                            Node node = nodes.item(i);
                            xmlRoot.addItem(node.getNodeName() + ":" + node.getFirstChild().getNodeValue());
                    }
            }
    });

