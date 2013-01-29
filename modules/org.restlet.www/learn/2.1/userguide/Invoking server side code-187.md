Invoking server side code
=========================

Description
===========

The Restlet API for GWT is a subset of the Restlet API. It is a subset
because, as mentioned above, parts of the Restlet API have been deleted,
where they depend on Java platform features not available in the
Javascript environment.

It is also a subset because it is fully (and only) asynchronous.  This
is necessary to conform to the behavior of the XmlHttpRequest-based
Client, as well as the structures of the unthreaded Javascript
environment.

Here is a snippet of code (from the downloadable example below) showing
briefly how to instantiate a ClientResource and make an asynchronous GET
call:

~~~~ {.brush: .java}
// Add behaviour on the close button.
closeButton.addClickHandler(new ClickHandler() {
    public void onClick(ClickEvent event) {
        // Add an AJAX call to the server
        ClientResource r = new ClientResource("/ping");

        // Set the callback object invoked when the response is received.
        r.setOnResponse(new Uniform() {
            public void handle(Request request, Response response) {
                try {
                    button.setText(response.getEntity().getText());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        r.get();
        dialogBox.hide();
    }
});
~~~~

[Comments
(0)](http://web.archive.org/web/20111106194156/http://wiki.restlet.org/docs_2.1/13-restlet/275-restlet/144-restlet/187-restlet.html#)

Comments
[Hide](http://web.archive.org/web/20111106194156/http://wiki.restlet.org/docs_2.1/13-restlet/275-restlet/144-restlet/187-restlet.html#)
\
There are no comments.

Add a comment

Please log in to be able to add comments.
