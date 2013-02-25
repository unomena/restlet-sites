Authentication
==============

Introduction
============

There are two commons ways to authenticate your users with your Restlet
application. The first is to use is to leverage the standard HTTP
authentication mechanism, either the Basic or the Digest authentication.
The Basic mechanism is sending the password in clear and should only be
used over a secure HTTPS channel. The second mechanism is to use a
custom authentication form and some cookie set by the server.

HTTP Basic authentication
=========================

Introduction
------------

Here is a very simple code illustrating a component that guards its
applications with the BASIC authentication scheme.

The whole code can be downloaded
[here](/learn/guide/1.1#/177-restlet/version/default/part/AttachmentData/data/testHttpBasic.zip "testHttpBasic")
(application/x-zip-compressed, 1.1 kB,
[info](/learn/guide/1.1#/177-restlet.html)).

Description of the server side
------------------------------

### Component

The sample component is composed of two parts. The first one is a very
simple Restlet that answers to request with a "hello, word" text
representation. Of course this Restlet is a very simple representation
of your own complex application.

    // Restlet that simply replies to requests with an "hello, world" text message
    Restlet restlet = new Restlet() {
        @Override
        public void handle(Request request, Response response) {
            response.setEntity(new StringRepresentation("hello, world", MediaType.TEXT_PLAIN));
        }
    };

Then, the component protects this Restlet with a Guard instance based on
the BASIC authentication scheme. Once the instance is created, it is
given the list of known (login/password) pairs via the "secrets"
attibute.

    // Guard the restlet with BASIC authentication.
    // Use the basic guard
    Guard guard = new Guard(component.getContext().createChildContext(), ChallengeScheme.HTTP_BASIC, "Sample application.");

    // Load a single static login/secret pair.
    guard.getSecrets().put("login", "secret".toCharArray());

    guard.setNext(restlet);

    component.getDefaultHost().attachDefault(guard);

### Customization

The authentication and authorization decisions are fully customizable
via the authenticate() and authorize() methods of the Guard class. Any
custom mechanism can be used to check whether the given credentials are
valid and whether the authenticated user is authorized to continue to
the attached Restlet.

### Description of the client side

The credentials are transmitted to the request via a ChallengeResponse
object as follow:

    Reference reference = new Reference("http://localhost:8182/");
    Client client = new Client(Protocol.HTTP);
    Request request = new Request(Method.GET, reference);

    // Send an authenticated request using the Basic authentication scheme.
    ChallengeResponse authentication = new ChallengeResponse(ChallengeScheme.HTTP_BASIC, "login", "secret");
    request.setChallengeResponse(authentication);

    // Send the request
    Response response = client.handle(request);
    // Should be 200
    System.out.println(response.getStatus());

HTTP Digest authentication
==========================

Introduction
------------

Here is a very simple code illustrating a component that guards its
applications with the DIGEST authentication scheme.

The whole code can be downloaded
[here](/learn/guide/1.1#/176-restlet/version/default/part/AttachmentData/data/testHttpDigest.zip "Sample code illustrating Digest authentication")
(application/force-download, 2.0 kB,
[info](/learn/guide/1.1#/176-restlet.html)).

Description of the server side
------------------------------

### Component

The sample component is composed of two parts. The first one is a very
simple Restlet that answers to request with a "hello, word" text
representation. Of course this Restlet is a very simple representation
of your own complex application.

    // Restlet that simply replies to requests with an "hello, world" text message
    Restlet restlet = new Restlet() {
        @Override
        public void handle(Request request, Response response) {
            response.setEntity(new StringRepresentation("hello, world", MediaType.TEXT_PLAIN));
        }
    };

Then, the component protects this Restlet with a Guard instance based on
the DIGEST authentication scheme. A dedicated constructor of the Guard
class of the Restlet API allows you to create such instance. Once the
instance is created, it is given the list of known (login/password)
pairs via the "secrets" attibute.

    // Guard the restlet with DIGEST authentication.
    Collection<String> baseUris = new ArrayList<String>();

    // Use the basic guard
    Guard guard = new Guard(component.getContext().createChildContext(), "realm", baseUris, "serverKey");

    // Load a single static login/secret pair.
    guard.getSecrets().put("login", "secret".toCharArray());

    guard.setNext(restlet);

    component.getDefaultHost().attachDefault(guard);

### Customization

As you may have noticed earlier, the list of known login/password pairs
are loaded by the basic Guard via the "secrets" attribute. This is
useful when the list of pairs are known by advance and is not
susceptible to change. In a more realistic case, the credentials are
hosted in a database or a LDAP directory, etc. In this case, the
responsibility to resolve the password according to the login is
deported to a dedicated sub class of the org.restlet.util.Resolver. An
instance of this sub class will be finally passed to the Guard as
follow:

    // Guard the restlet with DIGEST authentication.
    Collection<String> baseUris = new ArrayList<String>();

    // Use the basic guard
    Guard guard = new Guard(component.getContext().createChildContext(), "realm", baseUris, "serverKey");

    // Use the customized resolver
    guard.setSecretResolver(new TestResolver());

    guard.setNext(restlet);

    component.getDefaultHost().attachDefault(guard);

The contract of the Resolver is a simple method which aims at returning
the given password according to the given login:

    import org.restlet.util.Resolver;

    /**
     * Customized resolver.
     */
    public class TestResolver extends Resolver<char[]> {

        /**
         * Returns the value that corresponds to the given name.
         */
        @Override
        public char[] resolve(String name) {
            // Could have a look into a database, LDAP directory, etc.
            if ("login".equals(name)) {
                return "secret".toCharArray();
            }

            return null;
        }
    }

Description of the client side
------------------------------

The authentication with the DIGEST scheme is bit more difficult than the
one for the BASIC scheme. The credentials provided by the client is the
result of computation of data given by the client on one side (login and
password) and by the server on the other side ("nonce" value, "realm"
value, etc.). Unless these pieces of data are known in advance by the
client, it appears that in general a first request is required in order
to collect them.

    Reference reference = new Reference("http://localhost:8182/");

    Client client = new Client(Protocol.HTTP);
    Request request = new Request(Method.GET, reference);

    // Create the Challenge response used by the client to authenticate its requests.
    ChallengeResponse challengeResponse = new ChallengeResponse(ChallengeScheme.HTTP_DIGEST, "login", "secret");
    request.setChallengeResponse(challengeResponse);

    // Send the first request
    Response response = client.handle(request);
    // Should be 401, since the client needs some data sent by the server in
    // order to complete the ChallengeResponse.
    System.out.println(response.getStatus());

Then, the second step allows to get the required data for the final
computation:

    // Complete the challengeResponse object according to the server's data
    Form form = new Form();
    form.add("username", "login");
    form.add("uri", reference.getPath());

    // Loop over the challengeRequest objects sent by the server.
    for (ChallengeRequest challengeRequest : response.getChallengeRequests()) {
        // Get the data from the server's response.
        if (ChallengeScheme.HTTP_DIGEST.equals(challengeRequest.getScheme())) {
            Series<Parameter> params = challengeRequest.getParameters();
            form.add(params.getFirst("nonce"));
            form.add(params.getFirst("realm"));
            form.add(params.getFirst("domain"));
            form.add(params.getFirst("algorithm"));
            form.add(params.getFirst("qop"));
        }
    }

Then, we compute the data into the final digested value:

    // Compute the required data
    String a1 = Engine.getInstance().toMd5("login" + ":" + form.getFirstValue("realm") + ":" + "secret");
    String a2 = Engine.getInstance().toMd5(request.getMethod() + ":" + form.getFirstValue("uri"));
    form.add("response", Engine.getInstance().toMd5(a1 + ":" + form.getFirstValue("nonce") + ":" + a2));
    challengeResponse.setCredentialComponents(form);

Finally, the request is completed with the updated ChallengeResponse
instance

    // Send the completed request
    request.setChallengeResponse(challengeResponse);
    response = client.handle(request);

    // Should be 200.
    System.out.println(response.getStatus());

