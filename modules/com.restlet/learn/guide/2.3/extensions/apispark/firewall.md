# Firewall

## Introduction

This module integrated in the [APISpark extension](/apispark.md) provides a tool to 

## Configuration

### Using Maven

### Manually 

You must add the following jars (provided in 
[Restlet Framework](http://restlet.com/download/current#release=unstable&edition=jse&distribution=zip) in the "/path/to/your/lib" folder or manually to the classpath.

*   org.restlet.jar (Restlet API)
* 	org.restlet.ext.apispark.jar (Restlet APISpark extension with firewall)


## Usage example

To use the firewall, first create an instance of FirewallFilter

~~~~{.java}
	FirewallFilter firewallFilter = new FirewallFilter();
~~~~

Then, on your firewall you can add multiple rules. For now, these rules are rate limiters.
There are two kinds of rate limiters : 
* Concurrent rate limiter : Limits the number of concurrent requests.
* Periodic rate limiter : Limits the number of requests on a given period.

These rate limiters count the number of requests made by identified users. So, to be efficient, the FirewallFilter need to be placed behind a ChallengeAuthenticator.

Different factories to create a Firewall rule.

* Create a periodic rate limiter.

  * With a default limit (for non authenticated users or for users without a role)  

~~~~{.java}
	// Create a map which will associate roles with limits.  
	Map&lt;String, Integer&gt; limitsPerRole = new HashMap&lt;String, Integer&gt;();

	// Associate the role "owner" to a limit of 100 requests.  
	limitsPerRole.put("owner", 100);

	// Associate the role "user" to a limit of 10 requests.  
	limitsPerRole.put("user", 10);

	// Create a period rate limitation rule with a period of 60 seconds, the defined limits per role and a default limit of 5 requests.  
	firewallFilter.addOnPeriodRateLimit(60, limitsPerRole, 5);
~~~~
  
  * Without a default limit (set by default at O)  

~~~~{.java}
	// Create a period rate limitation rule with a period of 60 seconds, the defined limits per role (defined in the example above).  
	firewallFilter.addOnPeriodRateLimit(60, limitsPerRole, 5);
~~~~

* Create a concurrent rate limiter.

  * With a default limit (for non authenticated users or for users without a role)  

~~~~{.java}
	// Create a map which will associate roles with limits.  
	Map&lt;String, Integer&gt; limitsPerRole = new HashMap&lt;String, Integer&gt;();

	// Associate the role "owner" to a limit of 10 requests.  
	limitsPerRole.put("owner", 10);

	// Associate the role "user" to a limit of 5 requests.  
	limitsPerRole.put("user", 5);
	
	// Create a period rate limitation rule the defined limits per role and a default limit of 2 requests.  
	firewallFilter.addConcurrentRateLimit(limitsPerRole, 2);
~~~~

  * Without a default limit (set by default at O)  

~~~~{.java}
	// Create a period rate limitation rule the defined limits per role.  
	firewallFilter.addConcurrentRateLimit(limitsPerRole);
~~~~

## Go further

It is also possible to customize the use of the Rate Limitation rules.

A Rate limitation rule is associated to several objects :   

* A CountingPolicy which gives an identifier to the request. Here are the existing Counting Policies (it's also possible to implement your own CountingPolicy):  
    * IpAddressCountingPolicy : The identifier is the IP Address of the client.  
    * UserCountingPolicy : The identifier is the identifier of the client (given by the ChallengeAuthenticator).  

* A (or several) ThresholdHandler which make a action when a limit is reached.   
  * A ThresholdHandler owns a LimitPolicy. This LimitPolicy associates an identifier (defined in the CountingPolicy) to a limit. Here are the existing Limit Policies (it's also possible to implement your own LimitPolicy):
    * RoleLimitPolicy : Associates the role of the client to a limit.
    * UniqueLimitPolicy : Attributes the same Limit Policy for all requests.
  * There are also different implementation of ThresholdHandler : 
    * BlockingHandler : Returns a 429 response (Too many requests) when the limit is reached. 
    * RateLimitationHandler : Returns a 429 response (Too many requests) when the limit is reached and adds some "Rate Limitation headers" :

~~~~
	X-RateLimit-Limit : 500 #Number of requests authorized for a period
    X-RateLimit-Remaining : 289 #Number of remaining requests authorized for the current period
    X-RateLimit-Reset : 123456789 #When the period will reset
~~~~

### Example 

* Create a Rate Limiter which counts 
    * IP Addresses
    * On a 1 minute period
    * With a unique limit : 10 calls
    * With a RateLimitationHandler (to return the "Rate limitationHeaders")

~~~~{.java}
	// Create the Firewall Filter
	FirewallFilter firewallFiler = new FirewallFilter();
	
	// Add a periodic rule with a period of 1 minute (60 seconds) and a IPCountingPolicy (to count the clients IP addresses) 
	FirewallCounterRule rule = new PeriodicFirewallCounterRule(60, new IpCountingPolicy());

	// Attach an RateLimitationHandler (with a UniqueLimitPolicy) to the FirewallRule
	rule.addHandler(new RateLimitationHandler(new UniqueLimitPolicy(10);

	// Attach the rule to the FirewallFilter
	firewallFilter.addCounter(rule);
~~~~
