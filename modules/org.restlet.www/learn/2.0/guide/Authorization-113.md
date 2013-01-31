Authorization
=============

Introduction
============

Authorization should happen after authentication to define what an
authenticated user is effectively authorized to do in your application.
We identified four approaches to authorization in a Restlet application.
When we mentionned LDAP as a permission store, it could be replaced by a
static file or by any sort of database.

Coarse-grained authorization
============================

You configure some Authorizer instances (or subclasses like
RoleAuthorizer, MethodAuthorizer, etc.) and attach them at key points in
the call routing graph (before a Router, for a single route, etc.).

This works nicely if you can assign roles to your authenticated users
and if your authorization rules don't vary too much between resources.
Otherwise, you might end-up with an Authorizer instance in front of each
resource class...

Fine-grained authorization
==========================

If your authorization rules tend to be very specific to each resource
class, or more complexly specific to resource instances, then it is
better to handle them at the resource level manually. As an help, you
can leverage the ServerResource\#isInRole() method.

Middle-grained authorization
============================

The idea would be to define several URI subspaces in your overall
application URI space. Each subspace would have a unique name, like a
role name. Then in LDAP you could assign permissions to roles for each
subspace, not duplicating URI information in LDAP.

Then, in your Restlet application, you could have a SubspaceAuthorizer
class that would retrieve the rules from LDAP based on a given subspace
name. An instance of SubspaceAuthorizer for each identified subspace
should be created and attached to the proper place in the routing graph.

Therefore you wouldn't need to duplicate URI matching code or have to
maintain URI templates in several places.

