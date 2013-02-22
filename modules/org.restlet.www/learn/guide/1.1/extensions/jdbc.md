JDBC extension
==============

Introduction
============

This connector is a client to a JDBC database. It is based on the [JDBC
Api](http://java.sun.com/products/jdbc/)
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
javadocs](http://restlet.org/learn/javadocs/1.1/ext/com/noelios/restlet/ext/jdbc/JdbcClientHelper.html)
for more details. \
 The Response provides the result of the SQL request as a
RowSetRepresentation which is a kind of XML representation of the
ResultSet instance wrapped either in a JdbcResult or in a WebRowSet
instance. See the
[RowSetRepresentation](http://restlet.org/learn/javadocs/1.1/ext/com/noelios/restlet/ext/jdbc/RowSetRepresentation.html)
for more details.

Here is the list of dependencies of this connector:

-   [Apache Commons DBCP
    1.2](http://jakarta.apache.org/commons/dbcp/)
-   [Apache Commons Pool
    1.3](http://jakarta.apache.org/commons/pool/)
-   [Apache Commons Logging
    1.1](http://jakarta.apache.org/commons/logging/)

