First server
============

Introduction
============

Let's first see how the Restlet Framework can listen to client requests
and reply to them. We will use the internal Restlet HTTP server
connector (even though it is possible to switch to others such as the
one based on
[Jetty](http://web.archive.org/web/20120120062702/http://wiki.restlet.org/docs_2.0/13-restlet/28-restlet/78-restlet.html "Eclipse Jetty extension"))
and return a simple string representation "hello, world" as plain text.
Note that the FirstServerResource class extends the base
org.restlet.resource.ServerResource class provided by the Restlet API:

~~~~ {.brush: .java}
public class FirstServerResource extends ServerResource {  

   public static void main(String[] args) throws Exception {  
      // Create the HTTP server and listen on port 8182  
      new Server(Protocol.HTTP, 8182, FirstServerResource.class).start();  
   }

   @Get  
   public String toString() {  
      return "hello, world";  
   }

}  
~~~~

If you run this code and launch your server, you can open a Web browser
and hit the
[http://localhost:8182](http://web.archive.org/web/20120120062702/http://localhost:8182/).
Actually, any URI will work, try also
[http://localhost:8182/test/tutorial](http://web.archive.org/web/20120120062702/http://localhost:8182/test/tutorial).
Note that if you test your server from a different machine, you need to
replace "localhost" by either the IP address of your server or its
domain name if it has one defined.

So far, we have mostly showed you the highest level of abstraction in
the Restlet API, with the ServerResource classes. But as we move
forward, you will discover that this class is supported by a broad Java
API,
[mapping](http://web.archive.org/web/20120120062702/http://wiki.restlet.org/docs_2.0/130-restlet.html "Mapping HTTP headers")all
REST and HTTP concepts to a set of Java classes, interfaces and
annotations.

[Let's now illustrate how to use this API on the
client-side](http://web.archive.org/web/20120120062702/http://wiki.restlet.org/docs_2.0/13-restlet/21-restlet/318-restlet/320-restlet.html "First client").

[Comments
(6)](http://web.archive.org/web/20120120062702/http://wiki.restlet.org/docs_2.0/13-restlet/21-restlet/318-restlet/319-restlet.html#)

Comments
[Hide](http://web.archive.org/web/20120120062702/http://wiki.restlet.org/docs_2.0/13-restlet/21-restlet/318-restlet/319-restlet.html#)
\

Created by Sam Cornwell on 7/6/10 9:22:07 PM

"Part03.class" should be changed to "FirstServerResource.class"

Created by Jerome Louvel on 7/6/10 9:50:08 PM

Thanks, just fixed!

Created by Mohammad Husain on 2/4/11 9:49:37 AM

I am having the following error:\
java.lang.NoClassDefFoundError: com/sun/syndication/feed/synd/SyndFeed\
 \
At this link
(http://restlet-discuss.1400322.n2.nabble.com/V2-RC-3-java-lang-NoSuchMethodError-when-POSTing-xml-td5114202.html)
I found that it is about too many jar files in the class path. I added
all the jars found in the lib directory (i.e. jars included in the
subdirectories of the lib directory) to the class path. Do I need to do
this? Or can I just add the jar files found at the top level of the lib
directory? Or is there a completely different reason for getting the
exception.

Created by Rick Dangerous on 2/4/11 9:57:26 PM

I have struggled with exactly the same problem. A library seems to be
missing - probably the ROME library (https://rome.dev.java.net/).\
 \
But you only need org.restlet.jar to run the example. Take a look at
this old FAQ:\
 \
"What are the other JAR files for?"\
http://www.restlet.org/documentation/1.0/faq\#05

Created by Mohammad Husain on 2/5/11 4:38:35 AM

Thanks Rick. Did adding the ROME library solve your problem? However,
the FAQ helped me a lot. I selected a minimal set of jar files by going
through the read me file in the lib directory and could run the server
successfully. Thanks again.

Created by Geoffrey Speedy on 1/3/12 3:39:15 AM

The firstSteps source code for Version 2.0 has recently changed. There
is too great a technological gap now between FirstServer and
FirstApplication. I can get FirstServer going but there's no way I could
get the new FirstApplication going. I am trying to get the previous
version of FirstApplication on Restlet 2.0 going in Restlet 2.1, but
again there is too much change. Would you please put up a
FirstApplication that shows all the required source code including the
imports that uses the necessary object stack eg
Component/Application/Restlet/Router/ServerResource/Representation,
rather than using default behaviour.

Add a comment

Please log in to be able to add comments.
