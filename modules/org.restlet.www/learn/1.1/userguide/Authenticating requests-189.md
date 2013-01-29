Authenticating requests
=======================

Introduction
============

If you want to access to securized resources, the request must be
completed with authentication credentials via the ChallengeResponse
object. Such object is instantiated with the required challenge scheme
(such as HTTP\_BASIC, HTTP\_DIGEST) and the credentials.

You must be aware that at the end the relation between your browser and
the server is handled by the XmlHttpRequest javascript object. According
to the provided data (scheme, credentials), it generates the right
request and in case the credentials are not sufficient, you will be
prompted to enter your credentials in a pop-up window.

Authentication with the HTTP\_BASIC scheme
==========================================

The following snippet of code illustrates pre-emptive authentication
with the HTTP\_BASIC scheme. It simply instantiates a new
ChallengeResponse object with the authentication scheme, the login and
password.

    // Send an authenticated request using the Basic authentication scheme.
    Request request = new Request(Method.GET, "http://localhost:8888/guarded");
    ChallengeResponse authentication = new ChallengeResponse(ChallengeScheme.HTTP_BASIC, "scott", "tiger");
    request.setChallengeResponse(authentication);
    new Client(Protocol.HTTP).handle(request, new Callback() {
        @Override
        public void onEvent(Request request, Response response) {
            tb.setText(response.getEntity().getText());
        }
    });

Authentication with the HTTP\_DIGEST scheme
===========================================

The following snippet of code illustrates pre-emptive authentication
with the HTTP\_DIGEST scheme. It simply instantiates a new
ChallengeResponse object with the authentication scheme, the login and
password.

    // Send an authenticated request using the DIGEST authentication scheme.
    final Reference reference = new Reference("http://localhost:8888/guarded_digest");
    final Client client = new Client(Protocol.HTTP);
    Request request = new Request(Method.GET, reference);
    ChallengeResponse authentication = new ChallengeResponse(ChallengeScheme.HTTP_DIGEST, "login", "secret");
    request.setChallengeResponse(authentication);
    // Send the request
    client.handle(request, new Callback() {
        @Override
        public void onEvent(Request request, Response response) {
            if (response.getStatus().isSuccess()) {
                tbDigest.setText(response.getEntity().getText());
            }
        }
    });

[Comments
(0)](http://web.archive.org/web/20101124065528/http://wiki.restlet.org/docs_1.1/13-restlet/144-restlet/189-restlet.html#)

Comments
[Hide](http://web.archive.org/web/20101124065528/http://wiki.restlet.org/docs_1.1/13-restlet/144-restlet/189-restlet.html#)
\
There are no comments.

Add a comment

Please log in to be able to add comments.
