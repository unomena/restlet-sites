JavaMail extension
==================

Introduction
============

This connector is based on
[JavaMail](http://java.sun.com/products/javamail/)
that provides a platform-independent and protocol-independent framework
to build mail and messaging applications.

Description
===========

This connector supports the following protocols: SMTP, SMTPS.

The list of supported specific parameters is available in the javadocs:

-   [JavaMail client
    javadocs](http://www.restlet.org/documentation/1.1/ext/com/noelios/restlet/ext/javamail/JavaMailClientHelper.html)

The mail and its properties (sender, recipient, subject, content, etc)
have to be specified as an XML representation. Please refer to the
[JavaMail client
javadocs](http://www.restlet.org/documentation/1.1/ext/com/noelios/restlet/ext/javamail/JavaMailClientHelper.html)
for more details.

Here is the list of dependencies of this connector:

-   [JavaMail API
    1.4](http://java.sun.com/products/javamail/)
-   [JavaBeans Activation Framework
    1.1](http://java.sun.com/products/javabeans/jaf/)

