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

	FirewallFilter firewallFilter = new FirewallFilter();

Then, on your firewall you can add multiple rules. For now, these rules are rate limiters.
There are two kinds of rate limiters : 
* Concurrent rate limiter : Limits the number of concurrent requests.
* Periodic rate limiter : Limits the number of requests on a given period.

These rate limiters count the number of requests made by identified users. So, to be efficient, the FirewallFilter need to be placed behind a ChallengeAuthenticator.

Different factories to create a Firewall rule.

* Create a periodic rate limiter.

  * With a default limit (for non authenticated users or for users without a role)  


	// Create a map which will associate roles with limits.  
	Map&lt;String, Integer&gt; limitsPerRole = new HashMap&lt;String, Integer&gt;();

	// Associate the role "owner" to a limit of 100 requests.  
	limitsPerRole.put("owner", 100);

	// Associate the role "user" to a limit of 10 requests.  
	limitsPerRole.put("user", 10);

	// Create a period rate limitation rule with a period of 60 seconds, the defined limits per role and a default limit of 5 requests.  
	firewallFilter.addOnPeriodRateLimit(60, limitsPerRole, 5);

  
  * Without a default limit (set by default at O)  

	// Create a period rate limitation rule with a period of 60 seconds, the defined limits per role (defined in the example above).  
	firewallFilter.addOnPeriodRateLimit(60, limitsPerRole, 5);

* Create a concurrent rate limiter.

  * With a default limit (for non authenticated users or for users without a role)  

	// Create a map which will associate roles with limits.  
	Map&lt;String, Integer&gt; limitsPerRole = new HashMap&lt;String, Integer&gt;();

	// Associate the role "owner" to a limit of 10 requests.  
	limitsPerRole.put("owner", 10);

	// Associate the role "user" to a limit of 5 requests.  
	limitsPerRole.put("user", 5);
	
	// Create a period rate limitation rule the defined limits per role and a default limit of 2 requests.  
	firewallFilter.addConcurrentRateLimit(limitsPerRole, 2);

  * Without a default limit (set by default at O)  

	// Create a period rate limitation rule the defined limits per role.  
	firewallFilter.addConcurrentRateLimit(limitsPerRole);
