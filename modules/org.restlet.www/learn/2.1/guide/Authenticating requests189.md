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
    ClientResource r = new ClientResource("/guarded");
    r.setChallengeResponse(ChallengeScheme.HTTP_BASIC, "scott", "tiger");

    // Set the callback object invoked when the response is received.
    r.setOnResponse(new Uniform() {
        public void handle(Request request, Response response) {
            if (response.getStatus().isSuccess()) {
                try {
                    tb.setText(response.getEntity().getText());
                } catch (IOException e) {
                    tb.setText("IOException: " + e.getMessage());
                }
            } else {
                tb.setText(response.getStatus().getName());
            }
        }
    });
    r.get();

Authentication with the HTTP\_DIGEST scheme
===========================================

The following snippet of code illustrates pre-emptive authentication
with the HTTP\_DIGEST scheme. It simply instantiates a new
ChallengeResponse object with the authentication scheme, the login and
password.

    // Send an authenticated request using the DIGEST authentication scheme.
    ClientResource r = new ClientResource("/guarded_digest");
    r.setChallengeResponse(ChallengeScheme.HTTP_DIGEST, "login", "secret");

    // Set the callback object invoked when the response is received.
    r.setOnResponse(new Uniform() {
        public void handle(Request request, Response response) {
            if (response.getStatus().isSuccess()) {
                try {
                    tbDigest.setText(response.getEntity().getText());
                } catch (IOException e) {
                    tbDigest.setText("IOException: " + e.getMessage());
                }
            } else {
                tbDigest.setText(response.getStatus().getName());
            }
        }
    });
    r.get();

[Comments
(0)](http://web.archive.org/web/20111106194209/http://wiki.restlet.org/docs_2.1/13-restlet/275-restlet/144-restlet/189-restlet.html#)

Comments
[Hide](http://web.archive.org/web/20111106194209/http://wiki.restlet.org/docs_2.1/13-restlet/275-restlet/144-restlet/189-restlet.html#)
\
There are no comments.

Add a comment

Please log in to be able to add comments.
