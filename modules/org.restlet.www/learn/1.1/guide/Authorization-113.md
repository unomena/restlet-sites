Authorization
=============

Description
===========

By default, the Guard class autorizes all the sucessfully authenticated
requests to go to the next Restlet attached to it. However it is very
easy to change this behavior to introduce any sort of authorization
mechanism.

The method to override is
Guard\#**authorize**([Request](http://web.archive.org/web/20120411173131/http://www.restlet.org/documentation/snapshot/api/org/restlet/data/Request.html) request)
which returns true by default.

