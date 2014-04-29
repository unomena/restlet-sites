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

You will need to [sign up to AS](https://apispark.com/) and 
launch the introspector: 

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
              Prints this help
       -u
              The mandatory APISpark userName
       -p
              The mandatory APISpark user secret key
       -s
              The optional APISpark platform URL (by default
              https://apispark.com)
    
The parameters u and p are mandatory, you should use them to provide your AS 
user login and token. You can get those [here](https://apispark.com/account/overview)
under the tab "tokens", you will need to be authenticated.

Information injected in APISpark
--------------------------------

You can find an example of documentation generated via this extension 
[here](https://apispark.com/apis/1255/versions/1/overview/). 
<!-- we should provide a valid cell here (I created cell 1255 by hand).-->
