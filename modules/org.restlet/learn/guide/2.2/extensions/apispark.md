APISpark extension
==============

Introduction
============

This extension provides a tool to import the contract of your RF web API in 
our platform for web APIs [APISpark](https://apispark.com/) (AS).

This will allow you to: 
 - Document an existing RF-based web API within APISpark
 - Synchronize web API changes initiated from both RF and AS sides
 - Generate client SDK and server skeleton
 - Manage RF based web APIs from APISpark in proxy mode and indirect mode
by pushing API access log data back to AS and supporting users provisioning 
from AS
 - Push server skeleton generation from AS into GitHub repository
 - Pull API definition changes from GitHub repository into AS
 - Get an embeddable API documentation served by APISpark
 - Host RF based web API in APISpark with proper containerization



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
under the tab "tokens".

Information injected in APISpark
--------------------------------

You can find an example of documentation generated via this extension 
[here](https://apispark.com/apis/1255/versions/1/overview/). 
<!-- we should provide a valid cell here (I created cell 1255 by hand).-->