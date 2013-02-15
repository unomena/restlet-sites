Status service
==============

Introduction
============

This service handles error statuses. If an exception is thrown within
your application or Restlet code, it will be intercepted by this service
if it is enabled.

When an exception or an error is caught, the [getStatus(Throwable,
Request,
Response)](http://web.archive.org/web/20100920234747/http://www.restlet.org/documentation/1.1/api/org/restlet/service/StatusService.html#getStatus%28java.lang.Throwable,%20org.restlet.data.Request,%20org.restlet.data.Response%29)
method is first invoked to obtain the status that you want to set on the
response. If this method isn't overridden or returns null, the
[Status.SERVER\_ERROR\_INTERNAL](http://web.archive.org/web/20100920234747/http://www.restlet.org/documentation/1.1/api/org/restlet/data/Status.html#SERVER_ERROR_INTERNAL)
constant will be set by default.

Also, when the status of a response returned is an error status (see
[Status.isError()](http://web.archive.org/web/20100920234747/http://www.restlet.org/documentation/1.1/api/org/restlet/data/Status.html#isError%28%29),
the [getRepresentation(Status, Request,
Response)](http://web.archive.org/web/20100920234747/http://www.restlet.org/documentation/1.1/api/org/restlet/service/StatusService.html#getRepresentation%28org.restlet.data.Status,%20org.restlet.data.Request,%20org.restlet.data.Response%29)
method is then invoked to give your service a chance to override the
default error page.

If you want to customize the default behavior, you need to create a
subclass of StatusService that overrides some or all of the methods
mentioned above. Then, just create a instance of your class and set it
on your Component or Application via the setStatusService() methods.

