JDBC extension
==============

Introduction
============

This connector is a client to a JDBC database. It is based on the [JDBC
API](http://web.archive.org/web/20111014103202/http://java.sun.com/products/jdbc/)
developed by Sun Microsystems and shipped with the JDK. Database
connections are optionally pooled using Apache Commons DBCP. In this
case, a different connection pool is created for each unique combination
of JDBC URI and connection properties.

Description
===========

This connector supports the following protocol: JDBC.

The SQL request and other kinds of parameters (such as pooling) are
passed to the client connector via an XML representation. Please refer
to the [JDBC client
Javadocs](http://web.archive.org/web/20111014103202/http://www.restlet.org/documentation/2.1/jse/ext/org/restlet/ext/jdbc/JdbcClientHelper.html)
for more details.

The Response provides the result of the SQL request as a
RowSetRepresentation which is a kind of XML representation of the
ResultSet instance wrapped either in a JdbcResult or in a WebRowSet
instance. See the
[RowSetRepresentation](http://web.archive.org/web/20111014103202/http://www.restlet.org/documentation/2.1/jse/ext/org/restlet/ext/jdbc/RowSetRepresentation.html)
for more details.

Here is the list of dependencies of this connector:

-   [Apache Commons
    DBCP](http://web.archive.org/web/20111014103202/http://jakarta.apache.org/commons/dbcp/)
-   [Apache Commons
    Pool](http://web.archive.org/web/20111014103202/http://jakarta.apache.org/commons/pool/)
-   [Apache Commons
    Logging](http://web.archive.org/web/20111014103202/http://jakarta.apache.org/commons/logging/)

For additional details, please consult the
[Javadocs](http://web.archive.org/web/20111014103202/http://www.restlet.org/documentation/2.1/jse/ext/org/restlet/ext/jdbc/package-summary.html).

[Comments
(0)](http://web.archive.org/web/20111014103202/http://wiki.restlet.org/docs_2.1/13-restlet/28-restlet/77-restlet.html#)

Comments
[Hide](http://web.archive.org/web/20111014103202/http://wiki.restlet.org/docs_2.1/13-restlet/28-restlet/77-restlet.html#)
\
There are no comments.

Add a comment

Please log in to be able to add comments.
