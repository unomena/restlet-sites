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

Here is a simple example, the resources are empty so you will get a "404 Not 
found" error when accessing resources with authorized profile and "403 Forbidden"
otherwise.

The code is self-sufficient so you just need to copy-paste it in a MyAPI 
class and run it. Don't forget to add the JSE edition org.restlet.jar 
([download here](http://restlet.org/download/current#release=stable&edition=jse))
in your build path.

	public class MyApi extends Application {
	
	    //Define role names
	    public static final String ROLE_USER = "user";
	    public static final String ROLE_OWNER = "owner";
	
	    @Override
	    public Restlet createInboundRoot() {
	        //Create the authenticator, the authorizer and the router that will be protected
	        ChallengeAuthenticator authenticator = createAuthenticator();
	        RoleAuthorizer authorizer = createRoleAuthorizer();
	        Router router = createRouter();
	
	    	Router baseRouter = new Router(getContext());
	        
	        //Protect the router by enforcing authentication then authorization
	        authorizer.setNext(ServerResource.class);
	        authenticator.setNext(baseRouter);
	    	
	    	//Protect only the private resources with authorizer
	    	//You could use several different authorizers to authorize different roles
	    	baseRouter.attach("/resourceTypePrivate/", authorizer);
	    	baseRouter.attach("/resourceTypePublic/", router);
	        return authenticator;
	    }
	    
	    private ChallengeAuthenticator createAuthenticator() {
	        ChallengeAuthenticator guard = new ChallengeAuthenticator(
	                getContext(), ChallengeScheme.HTTP_BASIC, "realm");
	
	        //Create in-memory users with roles
	        MemoryRealm realm = new MemoryRealm();
	        User user = new User("user", "user");
	        realm.getUsers().add(user);
	        realm.map(user, Role.get(this, ROLE_USER));
	        User owner = new User("owner", "owner");
	        realm.getUsers().add(owner);
	        realm.map(owner, Role.get(this, ROLE_OWNER));
	        
	
	        //Attach verifier to check authentication and enroler to determine roles
	        guard.setVerifier(realm.getVerifier());
	        guard.setEnroler(realm.getEnroler());
	
	        //You can also create your own authentication system by creating 
	        //classes extending SecretVerifier or LocalVerifier
	        return guard;
	    }
	    
	    private RoleAuthorizer createRoleAuthorizer() {
	    	//Only authorize owners on roleAuth's children
	    	RoleAuthorizer roleAuth = new RoleAuthorizer();
	    	roleAuth.getAuthorizedRoles().add(Role.get(this, ROLE_OWNER));
	    	roleAuth.getForbiddenRoles().add(Role.get(this, ROLE_USER));
	    	return roleAuth;
	    }
	
	    private Router createRouter() {
	        //Attach Server Resources to given URL
	        Router router = new Router(getContext());
	        router.attach("resource1/", ServerResource.class);
	        router.attach("resource2/", ServerResource.class);
	        return router;
	    }
	    
	    public static void main(String[] args) throws Exception {
	        //Attach application to http://localhost:9000/v1
	        Component c = new Component();
	        c.getServers().add(Protocol.HTTP, 9000);
	        c.getDefaultHost().attach("/v1", new MyApi());
	        c.start();
	    }
	}

Fine-grained authorization
==========================

If your authorization rules tend to be very specific to each resource
class, or more complexly specific to resource instances, then it is
better to handle them at the resource level manually. As an help, you
can leverage the ServerResource\#isInRole() method.

In the previous code, you can just attach Resource1ServerResource.class
instead of ServerResource.class to "resource1/" and create 
Resource1ServerResource with a copy-paste of the code below.

	public class Resource1ServerResource extends ServerResource {

		@Get
		public String represent() throws ResourceException {
	        if (!isInRole(MyApi.ROLE_OWNER)) {
	            getLogger().info("Unauthorized user");
	            throw new ResourceException(Status.CLIENT_ERROR_FORBIDDEN);
	        }
	        return "Hello world !";
		}
	}

You will need to use the owner profile for every GET call to 
[http://localhost:9000/v1/resourceTypePublic/resource1/](http://localhost:9000/v1/resourceTypePublic/resource1/)
to see the API's greeting and will get a "403 Forbidden" otherwise.

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

