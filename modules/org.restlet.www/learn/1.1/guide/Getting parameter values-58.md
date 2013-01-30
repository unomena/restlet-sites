Getting parameter values
========================

Introduction
============

This is a common need to retrieve data, especially "key=value" pairs
from the query, the entity, or the cookies.\
 Here are some sample lines of code wich illustrate this feature.

Getting values from a web form
==============================

The web form is in fact the entity of the POST request sent to the
server, thus you have access to it via request.getEntity().\
 There is a shortcut which allows to have a list of all input fields :

`Form form = request.getEntityAsForm(); for (Parameter parameter : form) {   System.out.print("parameter " + parameter.getName());   System.out.println("/" + parameter.getValue()); }`

Getting values from a query
===========================

The query is a part of the identifier (the URI) of the request resource.
Thus, you have access to it via request.getResourceRef().getQuery().\
 There is a shortcut which allows to have a list of all "key=value"
pairs :\

`Form form = request.getResourceRef().getQueryAsForm(); for (Parameter parameter : form) {   System.out.print("parameter " + parameter.getName());   System.out.println("/" + parameter.getValue()); }`

Getting values from the cookies
===============================

Cookies are directly available from the request via the
request.getCookies() method. It returns a collection of "Cookie" objects
which extends the Parameter class and contains additional attributes:\

`  for (Cookie cookie : request.getCookies()) {     System.out.println("name = " + cookie.getName());     System.out.println("value = " + cookie.getValue());     System.out.println("domain = " + cookie.getDomain());     System.out.println("path = " + cookie.getPath());     System.out.println("version = " + cookie.getVersion()); }`

Extracting values from query, entity, cookies into the request's attributes
===========================================================================

When routing URIs to Resource instances or Restlet, you can decide to
transfer data from query, entity or cookies into the request's list of
attributes.\
 This mechanism is declarative and has been implemented at the level of
the "routes" (see the code below for more information). This mechanism
transfers data from the query (method "extractQuery"), the entity
(method "extractEntity") or the Cookies (method "extractCookies") to the
request's list of parameters.

For example, when implementing your Application, assuming that the
posted web form contains a select input field (called "selectField") and
a text field ( called "textField"). If you want to transfer them
respectively to attributes named "selectAttribute" and "textAttribute",
just proceed as follow.

`@Override public Restlet createRoot() {      Router router = new Router(getContext());      Route route = router.attachDefault(MyResource.class);      route.extractEntity("selectAttribute", "selectField", true);      route.extractEntity("textAttribute", "textField", false);      return router; }`

You will get a String value in the "selectAttribute" (the selected
option), and a Form object which is a collection of key/value pairs
(key="textField" in this case) with the "textAttribute" attribute.\
 Here is sample code which helps to retrieve some attributes:

`// Get the map of request attributes Map<String, Object> map = request.getAttributes();`

`// Retrieve the "selectAttribute" value String stringValue = (String) map.get("selectAttribute"); System.out.println(" value => " + stringValue);`

`// Retrieve the "textAttribute" collection of parameters Object object = map.get("textAttribute"); if(object != null){     if(object instanceof Form){         Form form = (Form) object;         for (Parameter parameter : form) {             System.out.print("parameter " + parameter.getName());             System.out.println("/ " + parameter.getValue());         }     } }`

[Comments
(0)](http://web.archive.org/web/20101016100743/http://wiki.restlet.org/docs_1.1/13-restlet/27-restlet/58-restlet.html#)

Comments
[Hide](http://web.archive.org/web/20101016100743/http://wiki.restlet.org/docs_1.1/13-restlet/27-restlet/58-restlet.html#)
\
There are no comments.

Add a comment

Please log in to be able to add comments.
