<div class="clearBoth"></div>
<hr />

   <h3>Eclipse update site</h3>

   <h4>Introduction</h4>

   <p>
      This update site is only intended for the Restlet edition for OSGi environments. 
      It allows the retrieval of Restlet modules and their dependencies as plain 
      bundles (including their dependencies) directly from the Eclipse 
      IDE. They are gathered by type of module: Core module, support of 
      standards, pluggable connectors and integrations with third-party 
      libraries. It is only available from the 2.1 release and beyond.
   </p>
   <p>
      The URL of the update site is based on the following pattern 
      <a href="http://p2.restlet.org/">http://p2.restlet.org/${branch}</a>. It provides all tagged 
      releases (such as current snapshot, or milestones, releases candidates, 
      etc). It is automatically refreshed once a day if the build succeeds.
   </p>

   <h4>Installation steps</h4>

   <p>
      Here are some instructions about how to configure your Eclipse IDE to 
      work with the online update site.
   </p>

   <p>
      Just go to the “help/Install New Software” menu. Enter the URL of the 
      desired repository in the upper field:
   </p>
 
   <img style="display:block;margin-left:auto;margin-right:auto" src="/images/downloads/eclipse-p2-1.png" width="600px" alt="Add the eclipse update site to the IDE." /> 

   <p>
      The available items are listed by category (Restlet Core, Pluggable connectors, 
      etc.). Just make your selection, and click on the “Next” button:
   </p>
   
   <img src="/images/downloads/eclipse-p2-2.png" style="display:block;margin-left:auto;margin-right:auto" width="600px" alt="Select plugins to be installed." />
      
   <p>
      After a few moments of calculation (required dependencies among others calculation), 
      you will be warned that you are on the way to install unsigned content. Just stick 
      to your choice and click the “OK” button:
   </p>
   
   <img src="/images/downloads/eclipse-p2-3.png" style="display:block;margin-left:auto;margin-right:auto" alt="Valid the warning message: the content is unsigned." /> 

   <p>
      The installation process goes on, and after a few moment you will be asked to restart 
      your Eclipse instance in order to finalize the installation.
   </p>