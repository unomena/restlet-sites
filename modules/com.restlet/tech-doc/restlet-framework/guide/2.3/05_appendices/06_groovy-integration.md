# As a Java library

As Groovy works with any Java library, it can naturally leverage the
Restlet framework. For a detailled article explaining this usage, I
recommand this post from Arc90:

"Building RESTful Web Apps with Groovy and Restlet"

-   [Part 1: Up and
    Running](http://blog.arc90.com/2008/06/04/building-restful-web-apps-with-groovy-and-restlet-part-1-up-and-running/)
-   [Part 2:
    Resources](http://blog.arc90.com/2008/06/building_restful_web_apps_groovy_restlet_part_2.php)

# As a Domain Specific Language

Another strength of Groovy is its capacity to define new languages in a
very dynamic and flexible way. Qi Keke has develop a specific DSL for
Restlet in Groovy. It is now available as an [official Groovy
Module](http://docs.codehaus.org/display/GROOVY/GroovyRestlet).
For more information, check the [announce on Restlet's
blog](http://blog.restlet.com/2008/02/29/groovy-dsl-available-for-restlet/).

The [Kauri
project](http://kauriproject.org/),
based on Restlet, is also using its [own Groovy
DSL](http://docs.ngdata.com/kauri-docs-0_4/307-kauri.html#Routingandresourceclasses)
to configure the routing.
