FileUpload extension
====================

Introduction
============

This extension leverages the [Apache FileUpload
library](http://web.archive.org/web/20090205114806/http://commons.apache.org/fileupload/)
to provide a robust, high-performance, Web-based file upload in Restlet
server-side applications.

Description
===========

This extension lets you receive files sent by POST or PUT requests and
to parse the posted entity (which is actually an instance of the
"Representation" class) and to extract a list of FileItems from it, each
item corresponding to one file uploaded in the posted request, typically
from a Web form.

Please, refer to the [FileUpload extension
Javadocs](http://web.archive.org/web/20090205114806/http://www.restlet.org/documentation/1.1/ext/org/restlet/ext/fileupload/package-summary.html)
and the [FAQ
\#18](http://web.archive.org/web/20090205114806/http://www.restlet.org/documentation/1.1/faq#18)
for more details.

Here is the list of dependencies for this extension:

-   [Java Servlet
    2.4](http://web.archive.org/web/20090205114806/http://java.sun.com/products/servlet/)
-   [Apache Commons FileUpload
    1.2](http://web.archive.org/web/20090205114806/http://jakarta.apache.org/commons/fileupload/)

Usage example
=============

This sample code illustrates how to upload files with the FileUpload
extension. It is composed of 3 classes:

-   a resource "MyResource" which responds to GET and POST requests,
-   an application called "MyApplication" which routes all received
    requests to the resource,
-   a component called "TestFileUpload" which creates a local HTTP
    server on port 80 and contains only one application (one instance of
    "MyApplication") attached to the path "/testFileUpload".

Thus, each request to the following uri
"http://localhost/testFileUpload" will be handled by a new instance of
"MyResource".

The single representation of this resource is a web form with a file
select control and a submit button. It allows to set up a request with
an uploaded file that will be posted to the resource.

Every Resource instance handles the POST request in method
"acceptRepresentation" which accepts the posted entity as single
parameter. The aim of the MyResource instance is to parse the request,
get the file, save it on disk and send back its content as plain text to
the client.

Here is the content of the MyResource\#acceptRepresentation method:

       /**
         * Accepts and processes a representation posted to the resource. As
         * response, the content of the uploaded file is sent back the client.
         */
        @Override
        public void acceptRepresentation(Representation entity) {
            if (entity != null) {
                if (MediaType.MULTIPART_FORM_DATA.equals(entity.getMediaType(),
                        true)) {

                    // The Apache FileUpload project parses HTTP requests which
                    // conform to RFC 1867, "Form-based File Upload in HTML". That
                    // is, if an HTTP request is submitted using the POST method,
                    // and with a content type of "multipart/form-data", then
                    // FileUpload can parse that request, and get all uploaded files
                    // as FileItem.

                    // 1/ Create a factory for disk-based file items
                    DiskFileItemFactory factory = new DiskFileItemFactory();
                    factory.setSizeThreshold(1000240);

                    // 2/ Create a new file upload handler based on the Restlet
                    // FileUpload extension that will parse Restlet requests and
                    // generates FileItems.
                    RestletFileUpload upload = new RestletFileUpload(factory);
                    List items;
                    try {
                        // 3/ Request is parsed by the handler which generates a
                        // list of FileItems
                        items = upload.parseRequest(getRequest());

                        // Process only the uploaded item called "fileToUpload" and
                        // save it on disk
                        boolean found = false;
                        for (final Iterator it = items.iterator(); it
                                .hasNext()
                                && !found;) {
                            FileItem fi = it.next();
                            if (fi.getFieldName().equals("fileToUpload")) {
                                found = true;
                                File file = new File("c:\\temp\\file.txt");
                                fi.write(file);
                            }
                        }

                        // Once handled, the content of the uploaded file is sent
                        // back to the client.
                        Representation rep = null;
                        if (found) {
                            // Create a new representation based on disk file.
                            // The content is arbitrarily sent as plain text.
                            rep = new FileRepresentation(new File(
                                    "c:\\temp\\file.txt"), MediaType.TEXT_PLAIN, 0);
                        } else {
                            // Some problem occurs, sent back a simple line of text.
                            rep = new StringRepresentation("no file uploaded",
                                    MediaType.TEXT_PLAIN);
                        }
                        // Set the representation of the resource once the POST
                        // request has been handled.
                        getResponse().setEntity(rep);
                        // Set the status of the response.
                        getResponse().setStatus(Status.SUCCESS_OK);
                    } catch (Exception e) {
                        // The message of all thrown exception is sent back to
                        // client as simple plain text
                        getResponse().setEntity(
                                new StringRepresentation(e.getMessage(),
                                        MediaType.TEXT_PLAIN));
                        getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
                        e.printStackTrace();
                    }
                }
            } else {
                // POST request with no entity.
                getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
            }
        }

Before running this example, please add the following jars to the
classpath:

-   org.restlet (Restlet API)
-   com.noelios.restlet (Noelios reference implementation)
-   org.restlet.ext.fileupload (Restlet extension based on the Apache
    FileUpload project)
-   org.apache.commons.fileupload (Apache FileUpload project)
-   org.apache.commons.io (Apache FileUpload project)

Links
-----

-   [Apache FileUpload
    library](http://web.archive.org/web/20090205114806/http://commons.apache.org/fileupload/)
-   [Server
    connectors](http://web.archive.org/web/20090205114806/http://wiki.restlet.org/docs_1.1/38-restlet.html)
-   [Sample code of FileUpload extension (zip
    file)](http://web.archive.org/web/20090205114806/http://wiki.restlet.org/docs_1.1/42-restlet.html "Usage example of FileUpload extension")

