# Description

The GWTedition contains a JSON extension that provides a
org.restlet.client.ext.json.JsonRepresentation class that you can
leverage to either parse a JSON representation received or to serialize
a JSON value.

Here is a sample code taken from the example application. The
JsonRepresentation gives access to the underlying JSONValue after the
representation has been parsed.

    ClientResource r = new ClientResource("/test");

    // Set the callback object invoked when the response is received.
    r.setOnResponse(new Uniform() {
        public void handle(Request request, Response response) {
            // Get the representation as an JsonRepresentation
            JsonRepresentation rep = new JsonRepresentation(response.getEntity());

            // Displays the properties and values.
            try {
                JSONObject object = rep.getValue().isObject();
                if (object != null) {
                    for (String key : object.keySet()) {
                        jsonRoot.addItem(key + ":" + object.get(key));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    });

    // Indicates the client preferences and let the server handle
    // the best representation with content negotiation.
    r.get(MediaType.APPLICATION_JSON);
