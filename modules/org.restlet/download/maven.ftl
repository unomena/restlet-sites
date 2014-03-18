<div class="clearBoth"></div>
<hr />
   <h3>Maven repository</h3>

   <h4>Introduction</h4>

   <p>
      The Maven repository for Restlet is accessible from
      <a href="http://maven.restlet.org">http://maven.restlet.org</a> and
      contains all Restlet JARs and third party dependencies that aren't
      available in the main public Maven repository. It is automatically
      refreshed once a day if the build succeeds.
   </p>

   <h4>Recommended configuration</h4>

   <p>
      Here are some instructions about how to configure Maven client to work with the
      online Maven repository.
   </p>

   <div>
      <ol>
         <li>
            Declare the repository for your project or for a
            parent project by updating the <i>pom.xml</i> file and adding
            the following code to the &lt;repositories&gt; section:
   <pre class="xml:nocontrols:nogutter">
&lt;repository&gt;
   &lt;id&gt;maven-restlet&lt;/id&gt;
   &lt;name&gt;Public online Restlet repository&lt;/name&gt;
   &lt;url&gt;http://maven.restlet.org&lt;/url&gt;
&lt;/repository&gt;
   </pre>
         </li>
         <li>
            As an alternative, you can also declare the repository for all of your
            projects. Go to the directory on the local computer where you just install Maven.
            Open and edit <i>conf/settings.xml</i> file. Add to the
            &lt;profiles&gt; section the following code:
   <pre class="xml:nocontrols:nogutter">
&lt;profile&gt;
  &lt;id&gt;restlet&lt;/id&gt;
  &lt;repositories&gt;
     &lt;repository&gt;
        &lt;id&gt;maven-restlet&lt;/id&gt;
        &lt;name&gt;Public online Restlet repository&lt;/name&gt;
        &lt;url&gt;http://maven.restlet.org&lt;/url&gt;
     &lt;/repository&gt;
  &lt;/repositories&gt;
&lt;/profile&gt;
   </pre>
            Just after the &lt;/profiles&gt; add the following:
   <pre class="xml:nocontrols:nogutter">
&lt;activeProfiles&gt;
    &lt;activeProfile&gt;restlet&lt;/activeProfile&gt;
&lt;/activeProfiles&gt;
   </pre>
         </li>
      </ol>
   </div>

   <h4>Available artifacts</h4>

   <div>
      The following table lists the available artifacts and their group and 
      artifact ids. With the introduction of the 
      <a href="/learn/guide/2.2/editions/" title="Editions of the Restlet framework">editions</a> 
      for the Restlet framework, it is necessary to make a distinction between
      an extension for a given edition and the same extension for another extension 
      simply because the code of the extension may change between each edition. 
      This distinction is reflected in the group id of each artifacts which 
      contains a reference to an edition. They are all set on the same pattern: 
      "org.restlet.&lt;edition&gt;" where "&lt;edition&gt;" is three-letters 
      code of an edition among: jse (Java SE edition), jee (Java EE edition), 
      gae (Google App Engine edition), android (Android edition) and 
      gwt (google Web Toolkit edition). You can find 
      <a href="/learn/guide/2.2/extensions/editions-matrix" title="Extensions per edition.">here</a>  
      a full view of the list of extensions and the editions that ship them. 
   </div>

   <div style="margin-top: 15px;margin-bottom: 10px; text-align:center">
      <table class="classic" style="display:inline" cellspacing="2" cellpadding="3">
         <tr>
            <th>Group id</th>
            <th>Artifact id</th>
            <th>Description</th>
         </tr>
         <tr>
            <td>org.restlet.&lt;edition&gt;</td>
            <td>org.restlet</td>
            <td>Restlet API</td>
         </tr>
         <tr>
            <td>org.restlet.&lt;edition&gt;</td>
            <td>org.restlet.example</td>
            <td>Examples</td>
         </tr>
         <tr>
            <td>org.restlet.&lt;edition&gt;</td>
            <td>org.restlet.ext.atom</td>
            <td>Atom extension</td>
         </tr>
         <tr>
            <td>org.restlet.&lt;edition&gt;</td>
            <td>org.restlet.ext.crypto</td>
            <td>Cryptography extension including Amazon S3 and Windows Azure
            client authentication.</td>
         </tr>
         <tr>
            <td>org.restlet.&lt;edition&gt;</td>
            <td>org.restlet.ext.fileupload</td>
            <td>Integration with Apache FileUpload.</td>
         </tr>
         <tr>
            <td>org.restlet.&lt;edition&gt;</td>
            <td>org.restlet.ext.freemarker</td>
            <td>Integration with FreeMarker.</td>
         </tr>
         <tr>
            <td>org.restlet.&lt;edition&gt;</td>
            <td>org.restlet.ext.grizzly</td>
            <td>Integration with Grizzly NIO framework.</td>
         </tr>
         <tr>
            <td>org.restlet.&lt;edition&gt;</td>
            <td>org.restlet.ext.gwt</td>
            <td>Server-side integration with GWT.</td>
         </tr>
         <tr>
            <td>org.restlet.&lt;edition&gt;</td>
            <td>org.restlet.ext.httpclient</td>
            <td>Integration with Apache HTTP Client.</td>
         </tr>
         <tr>
            <td>org.restlet.&lt;edition&gt;</td>
            <td>org.restlet.ext.jaas</td>
            <td>Support for JAAS authentication and authorization framework.</td>
         </tr>
         <tr>
            <td>org.restlet.&lt;edition&gt;</td>
            <td>org.restlet.ext.jackson</td>
            <td>Integration with Jackson.</td>
         </tr>
         <tr>
            <td>org.restlet.&lt;edition&gt;</td>
            <td>org.restlet.ext.javamail</td>
            <td>Integration with JavaMail (POP3 and SMTP clients)</td>
         </tr>
         <tr>
            <td>org.restlet.&lt;edition&gt;</td>
            <td>org.restlet.ext.jaxb</td>
            <td>Integration with Java XML Binding (JAXB).</td>
         </tr>
         <tr>
            <td>org.restlet.&lt;edition&gt;</td>
            <td>org.restlet.ext.jaxrs</td>
            <td>Implementation of JAX-RS.</td>
         </tr>
         <tr>
            <td>org.restlet.&lt;edition&gt;</td>
            <td>org.restlet.ext.jdbc</td>
            <td>Integration with Java DataBase Connectivity (JDBC).</td>
         </tr>
         <tr>
            <td>org.restlet.&lt;edition&gt;</td>
            <td>org.restlet.ext.jetty</td>
            <td>Integration with Jetty.</td>
         </tr>
         <tr>
            <td>org.restlet.&lt;edition&gt;</td>
            <td>org.restlet.ext.jibx</td>
            <td>Integration with JiBX.</td>
         </tr>
         <tr>
            <td>org.restlet.&lt;edition&gt;</td>
            <td>org.restlet.ext.json</td>
            <td>Support for JSON representations.</td>
         </tr>
         <tr>
            <td>org.restlet.&lt;edition&gt;</td>
            <td>org.restlet.ext.lucene</td>
            <td>Integration with Apache Lucene.</td>
         </tr>
         <tr>
            <td>org.restlet.&lt;edition&gt;</td>
            <td>org.restlet.ext.net</td>
            <td>Integration with Java URLConnection class.</td>
         </tr>
         <tr>
            <td>org.restlet.&lt;edition&gt;</td>
            <td>org.restlet.ext.netty</td>
            <td>Integration with Netty.</td>
         </tr>
         <tr>
            <td>org.restlet.&lt;edition&gt;</td>
            <td>org.restlet.ext.odata</td>
            <td>Support for the OData Services.</td>
         </tr>
         <tr>
            <td>org.restlet.&lt;edition&gt;</td>
            <td>org.restlet.ext.rdf</td>
            <td>Support for the RDF parsing and generation.</td>
         </tr>
         <tr>
            <td>org.restlet.&lt;edition&gt;</td>
            <td>org.restlet.ext.rome</td>
            <td>Integration with ROME.</td>
         </tr>
         <tr>
            <td>org.restlet.&lt;edition&gt;</td>
            <td>org.restlet.ext.servlet</td>
            <td>Integration with Servlet API.</td>
         </tr>
         <tr>
            <td>org.restlet.&lt;edition&gt;</td>
            <td>org.restlet.ext.simple</td>
            <td>Integration with Simple Framework.</td>
         </tr>
         <tr>
            <td>org.restlet.&lt;edition&gt;</td>
            <td>org.restlet.ext.slf4j</td>
            <td>Integration with SLF4J.</td>
         </tr>
         <tr>
            <td>org.restlet.&lt;edition&gt;</td>
            <td>org.restlet.ext.spring</td>
            <td>Integration with Spring Framework.</td>
         </tr>
         <tr>
            <td>org.restlet.&lt;edition&gt;</td>
            <td>org.restlet.ext.ssl</td>
            <td>Support for SSL utilities and integration with jSSLutils library.
        </td>
         </tr>
         <tr>
            <td>org.restlet.&lt;edition&gt;</td>
            <td>org.restlet.ext.velocity</td>
            <td>Integration with Apache Velocity.</td>
         </tr>
         <tr>
            <td>org.restlet.&lt;edition&gt;</td>
            <td>org.restlet.ext.wadl</td>
            <td>Support the WADL specification.</td>
         </tr>
         <tr>
            <td>org.restlet.&lt;edition&gt;</td>
            <td>org.restlet.ext.xdb</td>
            <td>Integration with Oracle 11g XML DB feature.</td>
         </tr>
         <tr>
            <td>org.restlet.&lt;edition&gt;</td>
            <td>org.restlet.ext.xml</td>
            <td>Support for XML and XSLT representations.</td>
         </tr>
         <tr>
            <td>org.restlet.&lt;edition&gt;</td>
            <td>org.restlet.ext.xstream</td>
            <td>Integration with XStream.</td>
         </tr>
         <tr>
            <td>org.restlet.&lt;edition&gt;</td>
            <td>org.restlet.test</td>
            <td>Test module.</td>
         </tr>
      </table>
   </div>

   <h4>Sample dependencies declaration</h4>

   <p>
      Each project based on the Restlet framework needs to declare at least one
      dependency: the Restlet core module. According to your needs, you should 
      complete the list of dependencies with the required extensions and 
      connectors. For example, assuming your project is a Web server delivering 
      static files, you need one HTTP server connector such as Simple. Since 
      your Maven client correctly references the Restlet online repository, just 
      open and edit the <i>pom.xml</i> file for your project and add the following
      lines of text into the &lt;dependencies&gt; section.
   </p>

   <pre class="xml:nocontrols:nogutter">
&lt;dependency&gt;
   &lt;groupId&gt;org.restlet.jse&lt;/groupId&gt;
   &lt;artifactId&gt;org.restlet&lt;/artifactId&gt;
   &lt;version&gt;${version}&lt;/version&gt;
&lt;/dependency&gt;
&lt;dependency&gt;
   &lt;groupId&gt;org.restlet.jse&lt;/groupId&gt;
   &lt;artifactId&gt;org.restlet.ext.simple&lt;/artifactId&gt;
   &lt;version&gt;${version}&lt;/version&gt;
&lt;/dependency&gt;
   </pre>