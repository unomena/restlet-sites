Examples
========

Introduction
============

An example project download is provided that works both under Eclipse
and via ant build.  This sets up the basic framework for GWT compilation
and debugging in Hosted Mode, provides a basic Restlet-powered server,
and demonstrates how the compiled GWT application can be bundled into an
executable server JAR.

Download [Restlet GWT -- Simple
Example](http://wiki.restlet.org/docs_1.1/162-restlet/version/default/part/AttachmentData/data/RestletGWTSimpleExample.zip "Restlet GWT -- Simple Example")
(application/x-zip-compressed, 2.5 MB,
[info](http://wiki.restlet.org/docs_1.1/162-restlet.html))

This is a simple example demonstrating some basic patterns for using
Restlet and GWT.  It produces an executable JAR file which depends only
on core Restlet libraries (included in "lib") to start a small Java Web
server on port 8888, which you can visit to access a  compiled GWT
application that, in turn, talks to the Web server.

You can also run the application in GWT Hosted Mode under Eclipse by
using the included SimpleExample.launch configuration; right click this
and choose Run As ... SimpleExample.

It is structured as an Eclipse project; you should be able to import it
into your Eclipse 3.3 or better workspace.  You can also run the ant
build script directly to produce the executable.

You must supply your own GWT 1.5 binaries; update the Eclipse build path
and/or the GWT\_1.5 property in the ant build script to point to the GWT
binaries for your platform.

Zip content
-----------

Name

Description

src

Source files of both:

-   client page developped with GWT and Restlet, in package
    "org.restlet.example.gwt.client".
-   Web server application developped with Restlet that serves the
    compiled web page and hosts a resource requested from the Web page.

build.xml

Ant script that:

-   compiles the GWT page
-   produces a final JAR file (RestletGWTSimpleExample.jar") of the
    server code in directory "dist". It also includes the
    "/etc/manifest.mf" file.

dist

Final directory where is located the final JAR of the server code.

etc

Directory where is located the "MANIFEST.MF" file of the server final
JAR.

lib

Libraries taken from the Restlet framework (server and client APIs and
implementations, GWT integration, servlet integration).

tomcat

Gathers configuration files for running the sample application inside
Tomcat.

SimpleExample.launch

Used to run the application in GWT Hosted Mode under Eclipse; right
click this and choose "Run As ... SimpleExample".

README.txt

Read it!

Description
===========

GWT page
--------

You can find the source code of this page in directory
"src/org/restlet/example/gwt/client".

Once the server is running, this page can be accessed at the following
URL: "http://localhost:8888/SimpleExample.html" .

This page is in charge to display several sample item such image,
button, etc organized in panels. All of these objects are instances of
GWT classes:

    // Define an image
    Image img = new Image("http://code.google.com/webtoolkit/logo-185x175.png");
    // Define a button
    final Button button = new Button("Click me");
    [...]
    // Define a panel
    VerticalPanel vPanel = new VerticalPanel();
    // We can add style names.
    vPanel.addStyleName("widePanel");
    vPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
    // Add image, button, tree
    vPanel.add(img);
    vPanel.add(button);

These class illustrates also how to add an asynchronous call with AJAX
inside the final Web page. It is as simple as to use a simple Restlet
client in order to request the "ping" resource located at URL
'"http://localhost:8888/ping" :

                    final Client client = new Client(Protocol.HTTP);
                    client.get("http://localhost:8888/ping", new Callback() {
                        @Override
                        public void onEvent(Request request, Response response) {
                            button.setText(response.getEntity().getText());
                        }
                    });

Server side
-----------

Basically, the server s responsible to serve the generated page, and to
respond to asynchronous call described just above.

The generated page is served by a simple Directory Restlet from the
"bin" directory when the server is run under Eclipse.

The asynchronous call is delegated to the PingResource class which
inherits from the Restlet Resource class. It simply answers to requests
with a line of text.

        @Override
        public Representation represent(Variant variant) throws ResourceException {
            return new StringRepresentation(
                    "The Restlet server side is alive. Method called: "
                            + getRequest().getMethod(), MediaType.TEXT_PLAIN);
        }

