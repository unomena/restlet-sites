Apache FileUpload extension
===========================

Introduction
============

This extension leverages the [Apache FileUpload
library](http://web.archive.org/web/20100825101048/http://commons.apache.org/fileupload/)
to provide a robust, high-performance, Web-based file upload in Restlet
server-side applications.

Description
===========

This extension lets you receive files sent by POST or PUT requests and
to parse the posted entity (which is actually an instance of the
"Representation" class) and to extract a list of FileItems from it, each
item corresponding to one file uploaded in the posted request, typically
from a Web form.

For additional details, please consult the
[Javadocs](http://web.archive.org/web/20100825101048/http://www.restlet.org/documentation/2.0/jse/ext/org/restlet/ext/fileupload/package-summary.html).

Here is the list of dependencies for this extension:

-   [Java
    Servlet](http://web.archive.org/web/20100825101048/http://java.sun.com/products/servlet/)
-   [Apache Commons
    FileUpload](http://web.archive.org/web/20100825101048/http://jakarta.apache.org/commons/fileupload/)

Usage example
=============

This sample code illustrates how to upload files with the FileUpload
extension. It is composed of 3 classes:

-   a resource "MyResource" which responds to GET and POST requests,
-   an application called "MyApplication" which routes all received
    requests to the resource,
-   a component called "TestFileUpload" which creates a local HTTP
    server on port 8182 and contains only one application (one instance
    of "MyApplication") attached to the path "/testFileUpload".

Thus, each request to the following uri
"http://localhost/testFileUpload" will be handled by a new instance of
"MyResource".

The single representation of this resource is a web form with a file
select control and a submit button. It allows to set up a request with
an uploaded file that will be posted to the resource. The name of the
file select control ("fileToUpload") is referenced by the resource.

Every Resource instance handles the POST request in method "accept"
which accepts the posted entity as single parameter. The aim of the
MyResource instance is to parse the request, get the file, save it on
disk and send back its content as plain text to the client.

Here is the content of the MyResource\#accept method:

[?](#)

`@Post`{.java .color1}

`public`{.java .keyword}
`Representation accept(Representation entity) `{.java
.plain}`throws`{.java .keyword} `Exception {`{.java .plain}

`    `{.java .spaces}`Representation rep = `{.java .plain}`null`{.java
.keyword}`;`{.java .plain}

`    `{.java .spaces}`if`{.java .keyword} `(entity != `{.java
.plain}`null`{.java .keyword}`) {`{.java .plain}

`        `{.java .spaces}`if`{.java .keyword}
`(MediaType.MULTIPART_FORM_DATA.equals(entity.getMediaType(),`{.java
.plain}

`                `{.java .spaces}`true`{.java .keyword}`)) {`{.java
.plain}

`            `{.java .spaces}`String fileName = `{.java
.plain}`"c:\\temp\\file.txt"`{.java .string}`;`{.java .plain}

 

`            `{.java
.spaces}`// The Apache FileUpload project parses HTTP requests which`{.java
.comments}

`            `{.java
.spaces}`// conform to RFC 1867, "Form-based File Upload in HTML". That`{.java
.comments}

`            `{.java
.spaces}`// is, if an HTTP request is submitted using the POST method,`{.java
.comments}

`            `{.java
.spaces}`// and with a content type of "multipart/form-data", then`{.java
.comments}

`            `{.java
.spaces}`// FileUpload can parse that request, and get all uploaded files`{.java
.comments}

`            `{.java .spaces}`// as FileItem.`{.java .comments}

 

`            `{.java
.spaces}`// 1/ Create a factory for disk-based file items`{.java
.comments}

`            `{.java .spaces}`DiskFileItemFactory factory = `{.java
.plain}`new`{.java .keyword} `DiskFileItemFactory();`{.java .plain}

`            `{.java .spaces}`factory.setSizeThreshold(`{.java
.plain}`1000240`{.java .value}`);`{.java .plain}

 

`            `{.java
.spaces}`// 2/ Create a new file upload handler based on the Restlet`{.java
.comments}

`            `{.java
.spaces}`// FileUpload extension that will parse Restlet requests and`{.java
.comments}

`            `{.java .spaces}`// generates FileItems.`{.java .comments}

`            `{.java .spaces}`RestletFileUpload upload = `{.java
.plain}`new`{.java .keyword} `RestletFileUpload(factory);`{.java .plain}

`            `{.java .spaces}`List<FileItem> items;`{.java .plain}

 

`            `{.java
.spaces}`// 3/ Request is parsed by the handler which generates a`{.java
.comments}

`            `{.java .spaces}`// list of FileItems`{.java .comments}

`            `{.java
.spaces}`items = upload.parseRequest(getRequest());`{.java .plain}

 

`            `{.java
.spaces}`// Process only the uploaded item called "fileToUpload" and`{.java
.comments}

`            `{.java .spaces}`// save it on disk`{.java .comments}

`            `{.java .spaces}`boolean`{.java .keyword} `found = `{.java
.plain}`false`{.java .keyword}`;`{.java .plain}

`            `{.java .spaces}`for`{.java .keyword} `(`{.java
.plain}`final`{.java .keyword}
`Iterator<FileItem> it = items.iterator(); it`{.java .plain}

`                    `{.java .spaces}`.hasNext()`{.java .plain}

`                    `{.java .spaces}`&& !found;) {`{.java .plain}

`                `{.java .spaces}`FileItem fi = it.next();`{.java
.plain}

`                `{.java .spaces}`if`{.java .keyword}
`(fi.getFieldName().equals(`{.java .plain}`"fileToUpload"`{.java
.string}`)) {`{.java .plain}

`                    `{.java .spaces}`found = `{.java
.plain}`true`{.java .keyword}`;`{.java .plain}

`                    `{.java .spaces}`File file = `{.java
.plain}`new`{.java .keyword} `File(fileName);`{.java .plain}

`                    `{.java .spaces}`fi.write(file);`{.java .plain}

`                `{.java .spaces}`}`{.java .plain}

`            `{.java .spaces}`}`{.java .plain}

 

`            `{.java
.spaces}`// Once handled, the content of the uploaded file is sent`{.java
.comments}

`            `{.java .spaces}`// back to the client.`{.java .comments}

`            `{.java .spaces}`if`{.java .keyword} `(found) {`{.java
.plain}

`                `{.java
.spaces}`// Create a new representation based on disk file.`{.java
.comments}

`                `{.java
.spaces}`// The content is arbitrarily sent as plain text.`{.java
.comments}

`                `{.java .spaces}`rep = `{.java .plain}`new`{.java
.keyword} `FileRepresentation(`{.java .plain}`new`{.java .keyword}
`File(fileName),`{.java .plain}

`                        `{.java .spaces}`MediaType.TEXT_PLAIN, `{.java
.plain}`0`{.java .value}`);`{.java .plain}

`            `{.java .spaces}`} `{.java .plain}`else`{.java .keyword}
`{`{.java .plain}

`                `{.java
.spaces}`// Some problem occurs, sent back a simple line of text.`{.java
.comments}

`                `{.java .spaces}`rep = `{.java .plain}`new`{.java
.keyword} `StringRepresentation(`{.java
.plain}`"no file uploaded"`{.java .string}`,`{.java .plain}

`                        `{.java .spaces}`MediaType.TEXT_PLAIN);`{.java
.plain}

`            `{.java .spaces}`}`{.java .plain}

`        `{.java .spaces}`}`{.java .plain}

`    `{.java .spaces}`} `{.java .plain}`else`{.java .keyword} `{`{.java
.plain}

`        `{.java .spaces}`// POST request with no entity.`{.java
.comments}

`        `{.java
.spaces}`setStatus(Status.CLIENT_ERROR_BAD_REQUEST);`{.java .plain}

`    `{.java .spaces}`}`{.java .plain}

 

`    `{.java .spaces}`return`{.java .keyword} `rep;`{.java .plain}

`}`{.java .plain}

Before running this example, please add the following jars to the
classpath:

-   org.restlet (Restlet API)
-   org.restlet.ext.fileupload (Restlet extension based on the Apache
    FileUpload project)
-   org.apache.commons.fileupload (Apache FileUpload project)
-   org.apache.commons.io (Apache FileUpload project)
-   javax.servlet.jar (Servlet archive used by the FileUpload library)

Links
-----

-   [Apache FileUpload
    library](http://web.archive.org/web/20100825101048/http://commons.apache.org/fileupload/)
-   [Server
    connectors](http://web.archive.org/web/20100825101048/http://wiki.restlet.org/docs_2.0/38-restlet.html)
-   [Sample code of FileUpload extension (zip
    file)](http://web.archive.org/web/20100825101048/http://wiki.restlet.org/docs_2.0/42-restlet.html "Usage example of FileUpload extension")

