APISpark extension
==============

Introduction
============

This extension provides a tool to import the contract of your Restlet web API in 
the [APISpark](https://apispark.com/) full-stack PaaS for web APIs.

This will allow you to: 
 - Introspect you Restlet-based web API to retrieve documentation
 - Display and edit this documentation within APISpark
 - Synchronize web API changes initiated from Restlet


Description
===========

Launch the application
----------------------

The Restlet extension for APISpark provides a code introspector that takes 
a class (your Restlet class extending the class Application) as a parameter 
and instantiates its components to retrieve the contract of your API.

You will need to [sign up to APISpark](https://apispark.com/signin) and 
launch the introspector, here is its commande line help: 

    SYNOPSIS
       org.restlet.ext.apispark.Introspector [options] APPLICATION
    DESCRIPTION
           Publish to the APISpark platform the description of your Web API,
           represented by APPLICATION, the full canonical name of your Restlet
           application class.
           If the whole process is successfull, it displays the url of the
           corresponding documentation.
    OPTIONS
           -h
                  Prints this help.
           -u
                  The mandatory APISpark user login.
           -p
                  The mandatory APISpark user security token.
           -s
                  The optional APISpark platform URL (by default
                  https://apispark.com).
           -c
                  The optional full canonical name of your Restlet Component class.
                  This allows to collect some other data, such as the endpoint.
           -d
                  The optional id of an existing definition hosted by APISpark you
                  want to update with this new documentation.
    
The parameters u and p are mandatory, you should use them to provide your APISpark 
user login and token. You can get those [here](https://apispark.com/account/overview)
under the tab "tokens", you will need to be authenticated.

You can find the parameter d in two ways. It is in the URL you will get when creating
the definition: "https://apispark.com/apis/parameter_d/versions/1/". If you didn't write
it down, you can go to your [dashboard](https://apispark.com/dashboard), click on the 
Web API Contract you want to update and get the same URL.

Information injected in APISpark
--------------------------------

![](./injectedOverview.png)

You can find a complete example of documentation generated via this extension 
[here](https://apispark.com/apis/1255/versions/1/overview/). 
<!-- we should provide a valid cell here (I created cell 1255 by hand).-->
