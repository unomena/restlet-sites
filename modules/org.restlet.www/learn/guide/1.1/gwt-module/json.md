Working with JSON
=================

Description
===========

There is a special org.restlet.gwt.resource.JsonRepresentation class
that you can leverage to either parse a JSON representation received or
to serialize a JSON value. In addition, a shortcut
org.restlet.gwt.data.Response\#getEntityAsJson() was added to
automatically wrap the response entity in a JsonRepresentation instance.

Here is a sample code taken from the example application. The
JsonRepresentation gives access to the underlying JSONValue after the
representation has been parsed.

    Request request = new Request(Method.GET, "http://localhost:8888/test");
    // Indicates the client preferences and let the server handle the best representation with content negotiation.
    request.getClientInfo().getAcceptedMediaTypes().add(new Preference<MediaType>(MediaType.APPLICATION_JSON));
    new Client(Protocol.HTTP).handle(request, new Callback() {
            @Override
            public void onEvent(Request request, Response response) {
                    // Get the representation as an JsonRepresentation
                    JsonRepresentation rep = response.getEntityAsJson();
                    // Displays the properties and values.
                    JSONObject object = rep.getValue().isObject();
                    if (object != null) {
                            for (String key : object.keySet()) {
                                    jsonRoot.addItem(key + ":" + object.get(key));
                            }
                    }
            }
    });

