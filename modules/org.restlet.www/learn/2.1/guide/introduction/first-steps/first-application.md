First application
=================

Introduction
============

This first application illustrates how to develop a Restlet application
that combines several editions of the Restlet Framework : GAE, GWT,
Android and Java SE. It explains the benefits of annotated Restlet
interfaces and of the ConverterService that offers transparent
serialization between Restlet representations and Java objects, usable
between a server application and several kind of clients.

Table of contents
=================

1.  [Requirements](http://web.archive.org/web/20111013171010/http://wiki.restlet.org/docs_2.1/13-restlet/21-restlet/318-restlet/303-restlet.html#dsy303-restlet_requirements)
2.  [Scenario](http://web.archive.org/web/20111013171010/http://wiki.restlet.org/docs_2.1/13-restlet/21-restlet/318-restlet/303-restlet.html#dsy303-restlet_scenario)
3.  [Archive
    content](http://web.archive.org/web/20111013171010/http://wiki.restlet.org/docs_2.1/13-restlet/21-restlet/318-restlet/303-restlet.html#dsy303-restlet_archive-content)
4.  [Common
    classes](http://web.archive.org/web/20111013171010/http://wiki.restlet.org/docs_2.1/13-restlet/21-restlet/318-restlet/303-restlet.html#dsy303-restlet_common-classes)
5.  [GAE server
    part](http://web.archive.org/web/20111013171010/http://wiki.restlet.org/docs_2.1/13-restlet/21-restlet/318-restlet/303-restlet.html#dsy303-restlet_gae)
6.  [GWT
    client](http://web.archive.org/web/20111013171010/http://wiki.restlet.org/docs_2.1/13-restlet/21-restlet/318-restlet/303-restlet.html#dsy303-restlet_gwt)
7.  [Android
    client](http://web.archive.org/web/20111013171010/http://wiki.restlet.org/docs_2.1/13-restlet/21-restlet/318-restlet/303-restlet.html#dsy303-restlet_android)
8.  [Java SE
    client](http://web.archive.org/web/20111013171010/http://wiki.restlet.org/docs_2.1/13-restlet/21-restlet/318-restlet/303-restlet.html#dsy303-restlet_jse)

Requirements
============

It is based on the following editions of the Restlet Framework : Java SE
(JSE), Google App Engine (GAE), Google Web Toolkit (GWT) and Android
which must be downloaded separately from [this
page](http://web.archive.org/web/20111013171010/http://www.restlet.org/downloads/).
It has been tested with the following environments:

-   Restlet Framework 2.1 Milestone 4
-   Google App Engine (GAE) 1.4.3, 1.5.2
-   Google Web Toolkit (GWT) 2.2 and 2.3
-   Android 2.1, 2.2, 2.3.3, 3.0

GAE doesn't support HTTP chunked encoding, therefore serialized object
can't be sent (via POST or PUT) to a GAE server. In Restlet Framework
version 2.1 M4 we have a workaround available that buffers the HTTP
entity to prevent chunk encoding. To use it, call the
ClientResource\#setRequestEntityBuffering(boolean) method with a "true"
value. Note that this workaround isn't required for the GWT edition.

Scenario
========

The server application is hosted on the Google App Engine (GAE)
platform. For the sake of simplicity it serves only one resource named
"contact", with the following characteristics:

-   its relative URI is "/contacts/123"
-   it supports the GET, PUT and DELETE methods.
-   it represents a simple "contact" object.

The "contact" object has the following attributes:

-   firstname
-   lastname
-   age
-   home address (actually an instance of a "Address" class): line1,
    line2, zipcode, city and country

 This resource will be requested for several kind of clients:

-   GWT client page
-   Android application
-   Java SE client

Archive content
===============

The full source code (without the required archives) is available here:
[serializationFullSource.](http://web.archive.org/web/20111013171010/http://wiki.restlet.org/docs_2.1/304-restlet/version/default/part/AttachmentData/data/serializationFullSource.zip "serializationFullSource")
(application/zip, 1.4 MB,
[info](http://web.archive.org/web/20111013171010/http://wiki.restlet.org/docs_2.1/304-restlet.html))

It contains the full source code of three Eclipse projects with:

1.  Project that contains both the GAE server and the GWT client code
2.  Project that contains the source code of the Android client
3.  Project that contains the source code of the Java SE client

Common classes
==============

The following classes are available on the three project. They are used
by the server and the clients in order to produce the serialized
representation of the Contact object and to deserialize incoming
representations.

-   Contact
-   Address
-   ContactResource.

ContactResource is an interface annotated with Restlet annotations:

~~~~ {.brush: .java}
public interface ContactResource {
    @Get
    public Contact retrieve();

    @Put
    public void store(Contact contact);

    @Delete
    public void remove();
}
~~~~

It represents the contract passed between the client and the server.

When using collections of objects as method parameters, you need to use
concrete classes if you intend to have GWT clients. For example use
ArrayList\<Contact\> instead of List\<Contact\>.

GAE server part
===============

We propose to host the server application on the GAE platform. The
server project relies on the following JAR files:

-   org.restlet.jar: core archive (*GAE edition*)
-   org.restlet.ext.gwt.jar: GWT server-side extension to convert Java
    objects to a GWT-specific serialization format (*GAE edition*)
-   org.restlet.ext.servlet.jar: Servlet extension to deploy the Restlet
    application in GAE (*GAE edition*)
-   org.restlet.ext.jackson.jar: Jackson extension used to generate JSON
    representations of the contact resource (*GAE edition*)
-   org.codehaus.jackson.core.jar, org.codehaus.jackson.mapper.jar:
    archives of the Jackson libraries required by the Jackson extension,
    and available in the GAE edition.

The server-side resource implements the annotated interface.

~~~~ {.brush: .java}
/**
 * The server side implementation of the Restlet resource.
 */
public class ContactServerResource extends ServerResource implements ContactResource {

   private static volatile Contact contact = 
        new Contact("Scott", "Tiger", new Address("10 bd Google", null, "20010", "Mountain View", 
                    "USA"), 40);

    public void remove() {
        contact = null;
    }

    public Contact retrieve() {
        return contact;
    }

    public void store(Contact contact) {
        ContactServerResource.contact = contact;
    }
}
~~~~

This resource is then exposed by the server application:

~~~~ {.brush: .java}
    @Override
    public Restlet createInboundRoot() {
        Router router = new Router(getContext());

        // Serve the files generated by the GWT compilation step.
        router.attachDefault(new Directory(getContext(), "war:///"));
        router.attach("/contacts/123", ContactServerResource.class);

        return router;
    }
~~~~

GWT client part
===============

The GWT client relies only on the core Restlet JAR (org.restlet.jar)
provided in the GWT edition.

In order to get the Contact object, a proxy class is required. This is
an interface that inherits on a specific interface (delivered by the GWT
edition of the Restlet Framework):

~~~~ {.brush: .java}
public interface ContactResourceProxy extends ClientProxy {
    @Get
    public void retrieve(Result<Contact> callback);

   @Put
    public void store(Contact contact, Result<Void> callback);

    @Delete
    public void remove(Result<Void> callback);
}
~~~~

This interface looks like the ContactResource interface, expect that it
adds a callback to each declared methods, due to the asynchronous nature
of the GWT platform and the underlying AJAX mechanism offered by web
browsers.

The type of the callback is not limited to the Result interface of the
Restlet Framework, it can also be the usual AsyncCallback interface
provided by GWT. Thus it allows you to easily migrate an existing
GWT-RPC code base to GWT-REST with Restlet.

Then, the following code allows you to request and handle the Contact
resource:

~~~~ {.brush: .java}
ContactResourceProxy contactResource = GWT.create(ContactResourceProxy.class);

// Set up the contact resource
contactResource.getClientResource().setReference("/contacts/123");

// Retrieve the contact
contactResource.retrieve(new Result<Contact>() {
    public void onFailure(Throwable caught) {
        // Handle the error
    }

    public void onSuccess(Contact contact) {
        // Handle the contact, for example by updating the GWT interface
        // Contact fields
        cTbFirstName.setText(contact.getFirstName());
        cTbLastName.setText(contact.getLastName());
        cTbAge.setText(Integer.toString(contact.getAge()));
    }
});
~~~~

Here is a screenshot of the GWT client page once the user has clicked on
the GET button.

  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  [![serialization-gwt-screenshot](Firstapplication-303_files/data_002.html "serialization-gwt-screenshot")](http://web.archive.org/web/20111013171010/http://wiki.restlet.org/docs_2.1/305-restlet/version/default/part/ImageData/data)
  [Click to enlarge](http://web.archive.org/web/20111013171010/http://wiki.restlet.org/docs_2.1/305-restlet/version/default/part/ImageData/data)
  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

In order to update the contact, simply complete your contact object and
invoke the "store" method as specified by the proxy interface:

~~~~ {.brush: .java}
contactResource.store(contact, new Result<Void>() {
    public void onFailure(Throwable caught) {
        // Handle the error
    }

    public void onSuccess(Void v) {
        // Display a dialog box
        dialogBox.setText("Update contact");
        textToServerLabel.setText("Contact successfully updated");
        dialogBox.center();
        closeButton.setFocus(true);
    }
});
~~~~

Android client part
===================

The Android client project relies only on the core Restlet JAR
(org.restlet.jar) provided by the Android edition of the Restlet
Framework.

The contact object will be serialized between the GAE server and the
Android client (in both directions) using the standard Java
serialization process. No additional interface is required except the
ContactResource interface furnished by the server.

~~~~ {.brush: .java}
// Initialize the resource proxy.
ClientResource cr = new ClientResource("http://restlet-example-serialization.appspot.com/contacts/123");
// Workaround for GAE servers to prevent chunk encoding
cr.setRequestEntityBuffering(true);

ContactResource resource = cr.wrap(ContactResource.class);

// Get the remote contact
Contact contact = resource.retrieve();
~~~~

In order to update the contact, simply use this instruction:

~~~~ {.brush: .java}
// Update the remote contact
resource.store(contact);
~~~~

The internal HTTP client has been rewritten using the java.nio.package.
This may lead, on some android devices, to encounter this kind of
exception: **java.net.SocketException: Bad address family**. In this
case, you can turn off the IPv6 preference as follow:
System.setProperty("java.net.preferIPv6Addresses", "false");

Here is a screenshot of the Android user interface.

### ![serialization-android-screenshot](Firstapplication-303_files/data.html "serialization-android-screenshot")

Java SE client
==============

Get the full Contact object
---------------------------

The same code used on the Android application allows you to get the full
Contact object:

~~~~ {.brush: .java}
ClientResource cr = new ClientResource("http://restlet-example-serialization.appspot.com/contacts/123");
// Get the Contact object
ContactResource resource = cr.wrap(ContactResource.class);
Contact contact = resource.retrieve();

if (contact != null) {
    System.out.println("firstname: " + contact.getFirstName());
    System.out.println(" lastname: " + contact.getLastName());
    System.out.println("     nage: " + contact.getAge());
}
~~~~

  This code produces the following ouput on the console:

    firstname: Scott
     lastname: Tiger
          age: 40

Get a JSON representation
-------------------------

In case the Contact class is not available, you can still retrieve a
JSON representation by setting the client preferences when retrieving
the resource's representation:

~~~~ {.brush: .java}
cr.get(MediaType.APPLICATION_JSON).write(System.out);
~~~~

which produces the following output:

    {"age":40,"firstName":"Scott","homeAddress":{"country":"USA","city":"Mountain View","line1":"10 bd Google","line2":null,"zipCode":"20010"},
    "lastName":"Tiger"}

[Comments
(23)](http://web.archive.org/web/20111013171010/http://wiki.restlet.org/docs_2.1/13-restlet/21-restlet/318-restlet/303-restlet.html#)

Comments
[Hide](http://web.archive.org/web/20111013171010/http://wiki.restlet.org/docs_2.1/13-restlet/21-restlet/318-restlet/303-restlet.html#)
\

Created by Gino on 12/9/10 8:37:06 PM

Is there a way I could use Restlet as a backend for both Android and
iPhone clients?

Created by Jerome Louvel on 12/24/10 10:14:06 AM

Hi Gino,\
 \
Definitely, Restlet can use HTTP to communicate with any HTTP client or
server, including iPhone. Sharing the same Restlet backend between
Android and iPhone clients is therefore a perfect use case of the
technology.\
 \
Cheers,\
Jerome

Created by Diego Rodriguez on 4/27/11 7:58:47 AM

Hi,\
I am new using this framework, and i am testing the example.With one
contact all is ok, but when i have done a list of contacts the get
method didn´t return me well the data. I have spent some days with this
and i am now complety lost. I am using the android client .\
if someone can help me\
best regards

Created by Jerome Louvel on 4/29/11 8:12:52 PM

Hi Diego, \
 \
You need to use concrete collection class as parameters. I've added a
warning.\
 \
Cheers,\
Jerome

Created by Shaun Gerner on 5/17/11 5:50:08 PM

Hi,\
I am trying to walk through the GAE Server Part example and I am running
into a problem when attempting to deploy to GAE. I did notice that this
was tested with GAE 1.4.3 and I am running GAE (1.5.0.1 - 2011-05-16)
which is the only GAE SDK that I can find on the GAE download pages. I
guess they do not let you download old versions of it. If you know where
I can grab the 1.4.3 version of it, I can try that. Anyway, the two
console errors that I'm seeing are:\
 \
=======================================\
Compiling module org.restlet.example.serialization.gae.Serialization\
 [ERROR] Errors in
'file:/C:/Users/shroge/Desktop/Eclipse%20Workspace/workspace/org.restlet.example.serialization.gae-gwt/src/org/restlet/example/serialization/gae/client/Serialization.java'\
 [ERROR] Internal compiler error\
java.lang.NoSuchMethodError:
com.google.gwt.user.rebind.rpc.SerializableTypeOracleBuilder.\<init\>(Lcom/google/gwt/core/ext/TreeLogger;Lcom/google/gwt/core/ext/PropertyOracle;Lcom/google/gwt/core/ext/typeinfo/TypeOracle;)V\
 at
org.restlet.rebind.ClientProxyGenerator.generateSerializers(ClientProxyGenerator.java:528)\
...\
=======================================\
and\
=======================================\
ERROR] Unexpected\
java.lang.NoSuchMethodError:
com.google.gwt.user.rebind.rpc.SerializableTypeOracleBuilder.\<init\>(Lcom/google/gwt/core/ext/TreeLogger;Lcom/google/gwt/core/ext/PropertyOracle;Lcom/google/gwt/core/ext/typeinfo/TypeOracle;)V\
 at
org.restlet.rebind.ClientProxyGenerator.generateSerializers(ClientProxyGenerator.java:528)\
...\
=======================================\
 \
I have been searching to see if anyone else ran into this but have been
unable to find it anywhere. Any help would be greatly appreciated!\
Thanks...

Created by Jerome Louvel on 5/17/11 8:45:33 PM

Hi Shaun,\
 \
I guess you are using GWT 2.2 which is only supported in post-2.1 M4
snapshots:\
http://www.restlet.org/downloads/\
 \
Best regards,\
Jerome\

Created by Shaun Gerner on 5/17/11 10:02:34 PM

Hi Jerome,\
Thanks for the response. Here are the files that I had downloaded:\
restlet-gae-2.1m4.exe\
restlet-gwt-2.1m4.exe\
serializationFullSource.zip - link from this page.\
 \
I'm confused on which files should go with the serializationFullSource
example on this page. Should I use the 2.0 example and the 2.0 GAE/GWT
framework instead?\
Thanks,\
Shaun

Created by Jerome Louvel on 5/19/11 3:05:33 PM

Hi Shaun,\
 \
The fix was made after 2.1 M4 and will be part of 2.1 M5. \
 \
For now, you can use a 2.1 snapshot such as:\
http://www.restlet.org/downloads/2.1/restlet-gae-2.1snapshot.exe\
http://www.restlet.org/downloads/2.1/restlet-gwt-2.1snapshot.exe\
 \
Cheers,\
Jerome

Created by Shaun Gerner on 5/24/11 4:21:29 PM

Hi Jerome,\
I'm still seeing that same error when compiling the GWT project.\
I've re-downloaded the following today and installed:\
restlet-gae-2.1snapshot.exe\
restlet-gwt-2.1snapshot.exe\
serializationFullSource.zip - link from this page\
 \
Then using eclipse I imported the existing project:\
org.restlet.example.serialization.gae-gwt\
 \
On project creation it uses the following libraries by default:\
JRE System Library [jdk1.6.0\_20]\
App Engine SDK [1.5.0]\
GWT SDK [2.3.0]\
 \
I've checked the build paths to make sure that the RESTLET-GAE and
RESTLET-GWT variables are set to the correct 2.1 Snapshot path.\
I've set the appengine-web.xml application to my app engine name.\
 \
Then I tried to deploy the app engine project which resulted in the
compile fail. Is there something that I'm missing that I should do
differently?\
 \
Thanks for your help,\
Shaun

Created by Thierry Boileau on 6/16/11 11:06:05 AM

Hello,\
 \
I've just tried using :\
JRE System Library [jdk1.6.0\_24]\
App Engine SDK [1.5.0.1]\
GWT SDK [2.3.0]\
the current snapshot of the gae edition.\
 \
I'm running under ubuntu.\
Perhaps, there is a difference with Windows.\

Created by Andy Dennie on 6/23/11 5:28:49 PM

Hi,\
 \
I'm having trouble getting the serialization sample Android client
working. It works when I use the 2.0.8 version of Restlet for Android,
but not when I use the 2.1M5 (or M4) version, regardless of whether I
use\
 cr.setRequestEntityBuffering(true)\
or whether I comment it out. \
 \
When using the 2.1 version, the android client hangs in run(), at\
 contact = resource.retrieve();\
 \
As far as I can tell, the android client is not sending the request when
using 2.1, or if it is, it's not getting as far as
ContactServerResource.retrieve() in the GAE service, because I can put a
breakpoint there that gets hit when the android client uses 2.0, but
doesn't get hit when the android client uses 2.1.\
I've been beating my head against this for a day now. Any suggestions
would be greatly appreciated!\
 \
-Andy\

Created by Andy Dennie on 6/24/11 4:01:09 PM

Just a follow-up to my last post. Still having the same issue, but
thought I should point out that I'm running on Windows 7, and also that
I see the same behavior when running against the production sample at
http://restlet-example-serialization.appspot.com/contacts/123 (that is,
it works when built with 2.0, but hangs with 2.1). So this rules out my
local GAE sample service as a potential culprit.

Created by Jerome Louvel on 7/2/11 6:08:42 PM

Andy,\
 \
We still have an NIO related issue with the internal connector that we
are chasing. At it is related to NIO reselection, it is likely to occur
more often on Android.\
 \
For now, I would suggest to register an alternative HTTP client on
Android like "org.restlet.ext.httpclient". See Restlet/Android docs for
instructions.\
 \
Hope this helps,\
Jerome

Created by Andy Dennie on 7/7/11 8:39:49 PM

Thanks, Jerome, I'll give that a try and report back.

Created by Andy Dennie on 7/11/11 9:09:07 PM

Hi Jerome, just wanted to let you know that it worked -- thanks! I'll
try to keep an eye out for subsequent milestone builds to test whether
it has been fixed. Is there an issue \# for this that I can follow?

Created by Jerome Louvel on 8/3/11 10:21:24 AM

Hi Andy, thanks for the update! Yes there is issue 1290 to track:
http://restlet.tigris.org/issues/show\_bug.cgi?id=1290\

Created by Leonardo Nunes on 8/5/11 12:20:25 AM

I did all exactly as the sample. But I think I'm missing something or
the versions 2.1-M5 and snapshot have serious bugs.\
Here is my problem:\
public interface MyResourceProxy extends ClientProxy {\
 @Get void someMethod(Result\<String\> callback);\
 @Put void otherMethod(String param, Result\<Void\> result);\
}\
in the gwt client code if I call using the async format the get method
throws MediaType not supported exception. If I do:
myResource.getClientResource().getClientInfo().getAcceptedMediaTypes().add(new
Preference\<MediaType\>(MediaType.TEXT\_PLAIN));\
it starts to work.\
If I call myResource.get instead of the async way it works even without
setting the media type.\
But the put method always throws
org.restlet.client.resource.ResourceException: Unsupported Media Type.\
What should I missing in order to make it work ?\

Created by Leonardo Nunes on 8/5/11 12:25:49 AM

Here my dependencies:\
 \<dependency\>\
 \<groupId\>org.restlet.gwt\</groupId\>\
 \<artifactId\>org.restlet\</artifactId\>\
 \<version\>\${restlet.version}\</version\>\
 \<scope\>compile\</scope\>\
 \</dependency\>\
 \<dependency\>\
 \<groupId\>org.restlet.gae\</groupId\>\
 \<artifactId\>org.restlet\</artifactId\>\
 \<version\>\${restlet.version}\</version\>\
 \</dependency\>\
 \<dependency\>\
 \<groupId\>org.restlet.gae\</groupId\>\
 \<artifactId\>org.restlet.ext.gwt\</artifactId\>\
 \<version\>\${restlet.version}\</version\>\
 \</dependency\>\
Tried using \${restlet.version} = 2.1-M5 and 2.1-SNAPSHOT

Created by Leonardo Nunes on 8/5/11 12:39:58 AM

one more information: in the PUT problem, if I do the async call and
just after myResource.put("any string",
MediaType.APPLICATION\_JAVA\_OBJECT\_GWT); there is no exception in both
calls, but only one message is received in the server and with an empty
string as a parameter instead of the string I'm passing.

Created by Thierry Boileau on 8/24/11 2:20:57 PM

Hello Leonardo,\
 \
we have noticed your problem and think that we understand what happen.
It seems that on server side, you need the org.restlet.ext.gwt.jar file,
in order to make the GWT client discuss with a "GWT aware" server using
the default GWT serialization/deserialization format.\
 \
Having said that, we think that we can take into account your case. As
you simple handle String objects, the GWT client should talk with
servers that cope with plain text entities.\
 \
This won't be part of the next 2.1m7, but for the 2.1rc1.\
 \
Best regards,\
Thierry Boileau

Created by Vincent ROBERT on 10/5/11 10:09:09 PM

I downloaded and tested this demo in localhost. I noticed a difference
between my version and your version which is online on
http://restlet-example-serialization.appspot.com/contacts/123\
 \
When I put this URI : http://localhost:8888/contacts/123 on my web
browser (FireFox) , I got this response :\
//OK[8,7,0,6,5,4,3,2,40,1,["org.restlet.example.common.Contact/4147449050","Scott","org.restlet.example.common.Address/95356055","Mountain
View","USA","10 bd Google","20010","Tiger"],0,6]\
 \
When I put your URI :
http://restlet-example-serialization.appspot.com/contacts/123 on my web
browser (FireFox) , I got this response :\
{"age":40,"firstName":"Scott","homeAddress":{"country":"USA","city":"Mountain
View","line1":"10 bd
Google","line2":null,"zipCode":"20010"},"lastName":"Tiger"}\
 \
Why these differences ? What modifications have I to write on the server
to obtain the good response (like you) ?\

Created by Jerome Louvel on 10/6/11 7:32:14 PM

I think you need to change the classpath order of the
org.restlet.ext.gwt.jar to have it after the
org.restlet.ext.jackson/xstream.jar ones. \
 \
It is only used by the GWT client.

Created by Vincent ROBERT on 10/7/11 12:12:38 PM

I put org.restlet.ext.gwt.jar after org.restlet.ext.jackson.jar in the
classpath order, but nothing changed.\
 \
My order is :\
org.restlet.jar\
org.restlet.ext.servlet.jar\
org.restlet.ext.jackson.jar\
org.restlet.ext.jackson.core.jar\
org.restlet.ext.jackson.mapper.jar\
org.restlet.ext.gwt.jar

Add a comment

Please log in to be able to add comments.
