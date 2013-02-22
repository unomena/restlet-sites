Net extension
=============

Introduction
============

This connector is fully based on the JDK and more precisely on
[java.net.HttpURLConnection
class](http://java.sun.com/j2se/1.5.0/docs/api/index.html?java/net/HttpURLConnection.html).

Description
===========

This connector supports the following protocols: HTTP, HTTPS, FTP and
FTPS.

The list of supported specific parameters is available in the Javadocs:

-   [Client
    parameters](http://www.restlet.org/documentation/2.0/jse/ext/org/restlet/ext/net/HttpClientHelper.html)

For additional details, please consult the
[Javadocs](http://www.restlet.org/documentation/2.0/jse/ext/org/restlet/ext/net/package-summary.html).

Proxy
=====

If you are running behind a HTTP proxy, you can add the following
parameters when launching your Restlet application:

    -Dhttp.proxyHost=myproxyhost -Dhttp.proxyPort=3128

You can also set this information programmatically

    System.setProperty("http.proxyHost", "myproxyhost");
    System.setProperty("http.proxyPort", "3128");

