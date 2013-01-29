Routing package
===============

Introduction
============

The **org.restlet.routing**package contains classes related to call
routing.

Another advantage of the Restlet Framework is the built-in support for
[cool
URIs](http://web.archive.org/web/20110805003817/http://www.w3.org/Provider/Style/URI).
A good description of the importance of proper URI design is given by
Jacob Nielsen in his
[AlertBox](http://web.archive.org/web/20110805003817/http://www.useit.com/alertbox/990321.html).
This document aims at showing the several configuration options of the
routing process. From a high level point of view, routing is mainly
based on the Router, Route, Template and Variable classes.

A Router is a Restlet that helps to associate a URI to the Restlet or
the Resource that will handle all requests made to this URI.\
 The association of a URI and the target Restlet or Resource is
personified by an instance of the Route class. In general, people wants
to express generic associations such as: all URIs
"/my/base/uri/{variable part}" target this Restlet or Resource. Hence,
the Restlet framework allows to define routes based on the [URI template
specification](http://web.archive.org/web/20110805003817/http://bitworking.org/projects/URI-Templates/).

URI Templates allows to defines pattern of URIs that contain embedded
variables. These concepts have been formalised in the Restlet famework
under the form of Template and Variable classes.

Here is a sample code illustrating the default usage of a Router:

    // Create a router Restlet that defines routes.
    Router router = new Router(getContext());

    // Defines a route for the resource "list of items"
    router.attach("/items", ItemsResource.class);
    // Defines a route for the resource "item"
    router.attach("/items/{itemName}", ItemResource.class);

It declares two routes, the first one associates the URI "/items" to the
Resource "ItemsResource" and the second one "/items/itemName" to the
Resource "ItemResource". The latter defines a variable called "itemName"
which can be retrieved by the Resource.

Please note that a route attaches instances of a Restlet subclass
whereas resources are attached via their class. The reason is that
Resources have been designed to handle a single request, thus instances
are generated at run-time via their class.

Default configuration
=====================

This chapter covers either the default behavior of instances of the
Router, Route, Template and Variable classes or the behavior that
applies by default during the process of routing a URI.

Matching mode
-------------

The default behavior of a Router is to match the whole request URI path
(actually the remaining part, left by previous Routers) with the
attached URI patterns, therefore excluding the query string. If the
request URI indeed is equal to one of the URI patterns, the target
ServerResource or Restlet is invoked. The way the matching is done by
default is based on the Template\#MODE\_EQUALS constant, meaning that
the URI template must exactly match the remaining part. This is a strict
mode that forbids subsequent routing.

This default routing mode can be changed by calling the
Router\#setDefaultMatchingMode() method with the
Template\#MODE\_STARTS\_WITH constant meaning that if the URI template
associated to the route matches the start of the target resource URI,
then it is accepted. This is a flexible mode that allows a hierarchy of
routers to be used.

Here is a sample code:

    router.setMatchingMode(Template.MODE_STARTS_WITH);

Routing and queries
-------------------

With the default matching mode to Template\#MODE\_EQUALS, your Router
won't accept URI with query strings going to this ServerResource. To
solve this, you can indicate at the router level, that the matching
process does take into account the query part of the reference:

    router.setDefaultMatchQuery(true);

You can also change this configuration only for a specific route:

    TemplateRoute route = router.attach("/users/{user}?{query}", UserResource.class);
    route.setMatchingQuery(true);

If you are migrating from version 1.1 to 2.0, we recommend reading the
[migration
guide](http://web.archive.org/web/20110805003817/http://wiki.restlet.org/docs_2.0/13-restlet/21-restlet/171-restlet/155-restlet.html "Migration guide from version 1.1 to 2.0")as
well.

Routing mode
------------

In addition to the matching mode, a Router supports several routing
modes, implementing various algorithms:

-   Best match
-   First match (default since Restlet 2.0)
-   Last match
-   Random match
-   Round robin
-   Custom

By default, when considering a URI to route, a Router computes an
affinity score for each declared route then choose the one that have the
best affinity score. You can update this behavior by setting the routing
mode property with one of the listed modes declared above.

    router.setRoutingMode(Router.FIRST);

Matching of template variables
------------------------------

As said in the introduction, the routing feature relies on the
declaration of templates of URI. In the sample code below, the URI
template declares one variable called "user":

    TemplateRoute route = router.attach("/users/{user}", UserResource.class);

At run-time, the value of this variable is put into the attributes of
the request:

    this.userId = (String) getRequest().getAttributes().get("user");

By default a variable is meant to match a URI segment as defined in the
URI specification document. That is to say a sequence of characters
between two "/".

A variable holds several other attributes:

-   defaultValue: default value to use if the key couldn't be found in
    the model ("" by default)..
-   required: indicates if the variable is required or optional (true by
    default).
-   fixed: indicates if the value is fixed, in which case, the default
    property is always used (false by default).
-   decodedOnParse: indicates if the parsed value must be decoded (false
    by default).

Tuning the routing
==================

Sometimes the same matching mode can't be used for all routes. For
example one delegates the routing to a child router while others routes
are resources directly processing the request. In this case, it is
possible to specify a different routing mode for each route using the
Route\#setMatchingMode() method. The Route instance is returned by the
Router\#attach() methods.

Matching mode
-------------

Regarding the matching mode, the default behavior of a Router can be
customized for a single route by updating its underlying template
instance as follow:

    TemplateRoute route = router.attach("/users/{user}", UserResource.class);
    route.getTemplate().setMatchingMode(Template.MODE_STARTS_WITH);

Routing and queries
-------------------

If you want to route URIs according to some values located in the query
part of the URI, and since those values have no determined place within
the query, you cannot rely on the URI template based routing feature
which is more or less based on regular expressions.\
 In this case you have to define your own router (with the "CUSTOM"
routing mode). A sample implementation is available
[here](http://web.archive.org/web/20110805003817/http://wiki.restlet.org/docs_2.0/211-restlet/version/default/part/AttachmentData/data "queryRouter")
(application/zip, 1.8 kB,
[info](http://web.archive.org/web/20110805003817/http://wiki.restlet.org/docs_2.0/211-restlet.html)).

Matching of template variables
------------------------------

By default, as said above, a template variable is meant to match a URI
segment. Here is the list of all available type of variables:

Value

Description

**TYPE\_ALL**

Matches all characters.

**TYPE\_ALPHA**

Matches all alphabetical characters.

**TYPE\_ALPHA\_DIGIT**

Matches all alphabetical and digital characters.

**TYPE\_COMMENT**

Matches any TEXT excluding "(" and ")".

**TYPE\_COMMENT\_ATTRIBUTE**

Matches any TEXT inside a comment excluding ";".

**TYPE\_DIGIT**

Matches all digital characters.

**TYPE\_TOKEN**

Matches any CHAR except CTLs or separators.

**TYPE\_URI\_ALL**

Matches all URI characters.

**TYPE\_URI\_FRAGMENT**

Matches URI fragment characters.

**TYPE\_URI\_PATH**

Matches URI path characters (not the query or the fragment parts).

**TYPE\_URI\_QUERY**

Matches URI query characters.

**TYPE\_URI\_SCHEME**

Matches URI scheme characters.

**TYPE\_URI\_SEGMENT**

Matches URI segment characters.

**TYPE\_URI\_UNRESERVED**

Matches unreserved URI characters.

**TYPE\_WORD**

Matches all alphabetical and digital characters plus the underscore.

Here is the way to change the type of a variable:

    TemplateRoute route = router.attach("/items/{itemName}", ItemResource);
    Map<String, Variable> routeVariables = route.getTemplate().getVariables();
    routeVariables.put("itemName", new Variable(Variable.TYPE_URI_WORD)); 

[Comments
(2)](http://web.archive.org/web/20110805003817/http://wiki.restlet.org/docs_2.0/13-restlet/27-restlet/326-restlet.html#)

Comments
[Hide](http://web.archive.org/web/20110805003817/http://wiki.restlet.org/docs_2.0/13-restlet/27-restlet/326-restlet.html#)
\

Created by beeftime on 11/27/10 8:09:47 AM

The "Matching Mode" paragraph and example uses: MODE\_STARTING\_WITH
MODE\_START\_WITH, when the actual value is MODE\_STARTS\_WITH

Created by Jerome Louvel on 11/27/10 2:18:19 PM

Thansk, just fixed!

Add a comment

Please log in to be able to add comments.
