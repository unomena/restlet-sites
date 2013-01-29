Tutorial
========

Introduction
============

Do you have a part of your web application that serves static pages like
Javadocs? Well, no need to setup an Apache server just for that, use
instead the dedicated org.restlet.resource.Directory class. See how
simple it is to use it:

    // URI of the root directory.  
    public static final String ROOT_URI = "file:///c:/restlet/docs/api/";  

       [...]  
       // Create a component
    Component component = new Component();  
    component.getServers().add(Protocol.HTTP, 8182);  
    component.getClients().add(Protocol.FILE);  

    // Create an application  
    Application application = new Application() {  
        @Override  
        public Restlet createInboundRoot() {  
            return new Directory(getContext(), ROOT_URI);  
        }  
    };  

    // Attach the application to the component and start it  
    component.getDefaultHost().attach(application);  
    component.start();

In order to run this example, you need to specify a valid value for
ROOT\_URI, In this case, it is set to
"file:///c:/restlet/docs/api/".Note that no additional configuration is
needed. If you want to customize the mapping between file extensions and
metadata (media type, language or encoding) or if you want to specify a
different index name, you can use the Application's
["metadataService"](http://web.archive.org/web/20110314164849/http://www.restlet.org/documentation/2.0/api/org/restlet/service/MetadataService.html)
property.

[Comments
(2)](http://web.archive.org/web/20110314164849/http://wiki.restlet.org/docs_2.0/13-restlet/27-restlet/326-restlet/374-restlet.html#)

Comments
[Hide](http://web.archive.org/web/20110314164849/http://wiki.restlet.org/docs_2.0/13-restlet/27-restlet/326-restlet/374-restlet.html#)
\

Created by beeftime on 11/27/10 8:07:35 AM

There are no imports in this example, where does the Directory()
function come from?

Created by Jerome Louvel on 11/27/10 2:19:44 PM

Added package name in description.

Add a comment

Please log in to be able to add comments.
