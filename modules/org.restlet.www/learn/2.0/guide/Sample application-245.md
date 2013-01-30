Sample application
==================

Introduction
============

This sample application aims at illustrating the useage of the RDF
extension of the Restlet framework.

It basically manages a list of users, each of them having a simple list
of contacts.

A user is simply characterized by its first name, last name, and the URI
of an image that depicts him.

A contact is a kind of user (he shares the same attributes), but in
addition he has a nickname.

Resources
---------

After a short analysis, let's define four Resources:

Name of the resource

Description

users

Resource that identifies the collection of all available users

user

Resource that identifies a single user

contacts

Resource that identifies the collection of all available contacts of a
user

contact

Resource that identifies a single contact of a single user

URIs of the Resources
---------------------

Now, let's define the URIs that will identify the resources. Assuming
that our application is hosted on your own computer known as "localhost"
and is listening to port 8585:

URI

Description

http://localhost:8585/foaf/users

URI of the "users" resource

http://localhost:8585/foaf/users/{userId}

URI of the "user" resource where {userId} represents the identifier of a
user

http://localhost:8585/foaf/users/{userId}/contacts

URI of the "contacts" resource

http://localhost:8585/foaf/users/{userId}/contacts/{contactId}

URI of the "contact" resource where {contactId} represents the
identifier of a contact

Methods applied on each resource
--------------------------------

Resource

Method

Description

users

GET

Lists the current users in a HTML document. Provides an HTML form to add
a new user.

POST

Creates a new user according to the data sent in the provided HTML form.

user

GET

Provides two representations of the currrent user and its list of
contacts : an HTML one, and a RDF one according to the FOAF ontology.

PUT

Updates the current user with a new set of properties.

DELETE

Deletes the current user.

contacts

GET

Provides an HTML representation of the current list of contacts of the
current user.

POST

Adds a new contact to the current user according to the data sent in the
provided HTML form.

contact

GET

Provides two representations of the currrent contact : an HTML one, and
a RDF one according to the FOAF ontology.

PUT

Updates the current contact with a new set of properties.

DELETE

Deletes the current contact.

Snapshots
---------

  ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  [![foaf-sample-app-users](Sample%20application-245_files/data_005.html "foaf-sample-app-users")](http://web.archive.org/web/20111130020450/http://wiki.restlet.org/docs_2.0/249-restlet/version/default/part/ImageData/data)
  [Click to enlarge](http://web.archive.org/web/20111130020450/http://wiki.restlet.org/docs_2.0/249-restlet/version/default/part/ImageData/data)
  ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Snapshot of the users Resource

  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  [![foaf-sample-app-user](Sample%20application-245_files/data_002.html "foaf-sample-app-user")](http://web.archive.org/web/20111130020450/http://wiki.restlet.org/docs_2.0/246-restlet/version/default/part/ImageData/data)
  [Click to enlarge](http://web.archive.org/web/20111130020450/http://wiki.restlet.org/docs_2.0/246-restlet/version/default/part/ImageData/data)
  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Snapshot of the user Resource

  --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  [![foaf-sample-app-contacts](Sample%20application-245_files/data.html "foaf-sample-app-contacts")](http://web.archive.org/web/20111130020450/http://wiki.restlet.org/docs_2.0/247-restlet/version/default/part/ImageData/data)
  [Click to enlarge](http://web.archive.org/web/20111130020450/http://wiki.restlet.org/docs_2.0/247-restlet/version/default/part/ImageData/data)
  --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Snapshot of the contacts Resource

  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  [![foaf-sample-app-contact](Sample%20application-245_files/data_003.html "foaf-sample-app-contact")](http://web.archive.org/web/20111130020450/http://wiki.restlet.org/docs_2.0/248-restlet/version/default/part/ImageData/data)
  [Click to enlarge](http://web.archive.org/web/20111130020450/http://wiki.restlet.org/docs_2.0/248-restlet/version/default/part/ImageData/data)
  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Snapshot of the contact Resource

Source code
===========

This example is available in the Restlet distributions under the
"src/org.restlet.example/rdf/foaf" directory. You can download it
[here](http://web.archive.org/web/20111130020450/http://wiki.restlet.org/docs_2.0/256-restlet/version/default/part/AttachmentData/data "foaf-sample")
(application/x-zip-compressed, 41.6 kB,
[info](http://web.archive.org/web/20111130020450/http://wiki.restlet.org/docs_2.0/256-restlet.html)).

The "User" Resource.
--------------------

As previously analysed, this resource is in charge to handle "GET",
"PUT" and "DELETE" requests. It is able to generate two kinds of
representation: HTML (more precisely XHTML) and FOAF ontology.

In a matter of simplicity, we choose to implement these behaviours with
annotated resources (i.e. sub classes of ServerResource). So, basically,
we will find 4 methods that handle each of these aspects of the
resource.

The other kind of resource (i.e. sub classes of Resource) allows you to
have a finer control of the Resource.

GET methods
-----------

### RDF representation

    @Get("rdf")
    public Representation toFoaf() throws ResourceException {
        return getFoafRepresentation(this.user, getRequest().getResourceRef());
    }

Whatever the name of the method is, the imortant thing to remind is the
annotation @Get("rdf"). It says quite clearly that this Java method
handles GET requests, especially the ones where the client prefers the
"rdf" representations (in this case, "rdf" representations following the
"foaf" ontlotogy). These preference is simply set at the level of the
URI of the target resource, by suffixing it with ".rdf".\
  For example, let's say the user resource is
"http://www.example.org/foaf/iusers/12345", then
"http://www.example.org/foaf/users/12345.rdf" will target precisely the
FOAF representation.

The way the RDF representations are handled is via a
org.restlet.ext.rdf.Graph object. It helps adding statements via methods
such as "add(source, type, target)". That's why you can find
instructions such as (see the 
org.restlet.example.ext.rdf.foaf.resources.BaseResource class):

    graph.add(subject, new Reference(FOAF_NS + predicate), new Literal(object));

### HTML Representation

    @Get("html")
    public Representation toHtml() throws ResourceException {
        final Map<String, Object> dataModel = new TreeMap<String, Object>();
        dataModel.put("user", this.user);
        dataModel.put("contacts", this.contacts);
        dataModel.put("resourceRef", getRequest().getResourceRef());
        dataModel.put("rootRef", getRequest().getRootRef());

        return getTemplateRepresentation("user.html", dataModel, MediaType.TEXT_HTML);
    }

The HTML representation is basically generated with the Freemarker
extension. It helps developping a template of the future HTML page wich
is simply viewed as text file.

### DELETE method

    @Delete
    public void removeUser() throws ResourceException {
        getObjectsFacade().deleteUser(this.user);
        getResponse().redirectSeeOther(getRequest().getResourceRef().getParentRef());
    }

This method is in charge of deleting a specific user. It simply deletes
it from the data storage, and redirects the client to the "users"
resource.

### PUT method

    @Put
    public void storeUser(Representation entity) throws ResourceException {
        final Form form = new Form(entity);
        this.user.setFirstName(form.getFirstValue("firstName"));
        this.user.setLastName(form.getFirstValue("lastName"));
        this.user.setImage(form.getFirstValue("image"));
        getObjectsFacade().updateUser(this.user);
        getResponse().redirectSeeOther(getRequest().getResourceRef());
    }

This method is in charge of updating a specific user. It simply parses
the sent representation as a Web form, update the current user object
then the data storage. Eventually, it redirects the client to the "user"
resource again.

FOAF Representation
===================

FOAF is a vocabulary able to describe a person, its activities, its
relations to the list persons he knows.

FOAF is expressed using RDF (Resource Description Framework).

The FOAF representation of a user of contact is directly available at
the URI of the user of contact suffixed by ".rdf".

If your navigator is able to handle alternates views of an HTML page,
such as Firefox with the
[Tabulator](http://web.archive.org/web/20111130020450/http://www.w3.org/2005/ajar/tab)
extension, the URI of the user and contact is sufficient.

  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  [![foaf-sample-app-foaf](Sample%20application-245_files/data_004.html "foaf-sample-app-foaf")](http://web.archive.org/web/20111130020450/http://wiki.restlet.org/docs_2.0/250-restlet/version/default/part/ImageData/data)
  [Click to enlarge](http://web.archive.org/web/20111130020450/http://wiki.restlet.org/docs_2.0/250-restlet/version/default/part/ImageData/data)
  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Snapshot of the tabulator extension for Firefox in action.

[Comments
(0)](http://web.archive.org/web/20111130020450/http://wiki.restlet.org/docs_2.0/13-restlet/28-restlet/270-restlet/245-restlet.html#)

Comments
[Hide](http://web.archive.org/web/20111130020450/http://wiki.restlet.org/docs_2.0/13-restlet/28-restlet/270-restlet/245-restlet.html#)
\
There are no comments.

Add a comment

Please log in to be able to add comments.
