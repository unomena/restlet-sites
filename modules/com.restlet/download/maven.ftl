<div class="right-sidebar"></div>

   <h3><a name="#maven"></a>Maven configuration</h3>

   <p>
      The Maven repository for Restlet is accessible from
      <a href="http://maven.restlet.com">http://maven.restlet.com</a> and
      contains all Restlet JARs and third party dependencies that aren't
      available in the main public Maven repository. It is automatically
      refreshed once a day if the build succeeds.
   </p>

   <h4>Sample POM</h4>

   <p>
      Each Restlet Framework project needs at least one dependency: the 
      Restlet core module and we assume that you will use Jackson for JSON support. 
      You also need to configure your Maven client to point to the dedicated Restlet 
      repository. Just open and edit the pom.xml file for your project 
      and add the following lines of text:
   </p>
	   <pre class="xml:nocontrols:nogutter" id="maven-snippet">
&lt;repositories&gt;
    &lt;repository&gt;
        &lt;id&gt;maven-restlet&lt;/id&gt;
        &lt;name&gt;Restlet repository&lt;/name&gt;
        &lt;url&gt;http://maven.restlet.com&lt;/url&gt;
    &lt;/repository&gt;
&lt;/repositories&gt;
&lt;properties&gt;
    &lt;restlet-version&gt;${version}&lt;/restlet-version&gt;
&lt;/properties&gt;
&lt;dependencies&gt;
    &lt;dependency&gt;
        &lt;groupId&gt;org.restlet.jse&lt;/groupId&gt;
        &lt;artifactId&gt;org.restlet&lt;/artifactId&gt;
        &lt;version&gt;&#36;{restlet-version}&lt;/version&gt;
    &lt;/dependency&gt;
    &lt;dependency&gt;
        &lt;groupId&gt;org.restlet.jse&lt;/groupId&gt;
        &lt;artifactId&gt;org.restlet.ext.jackson&lt;/artifactId&gt;
        &lt;version&gt;&#36;{restlet-version}&lt;/version&gt;
    &lt;/dependency&gt;
&lt;/dependencies&gt;
	   </pre>

   <h4>Available artifacts</h4>

   <p>
      According to your needs, you might have to complete the list of dependencies. 
      The table table lists available artifacts and their group and artifact ids.
   </p>
   
   <p>
      As Restlet Framework supports multiple editions, it is necessary to make a 
      distinction between an extension for a given edition and the same extension 
      for another one. This is reflected in the group id of each artifact. They 
      folllow the same pattern: <code>org.restlet.&lt;edition&gt;</code> 
      where <code>&lt;edition&gt;</code> is three-letters 
      code of an edition among: jse (Java SE edition), jee (Java EE edition), gae 
      (Google App Engine edition), android (Android edition) and gwt (google Web Toolkit edition).
   </p>
   
   <p>
        Please find below the list of extensions available for the release and edition that you selected.
   </p>

      <table class="classic" style="display:inline margin-top: 15px;margin-bottom: 10px; text-align:left" cellspacing="2" cellpadding="3">
         <tr>
            <th>artifactId</th>
            <th>Description</th>
         </tr>
         <tr>
            <td>org.restlet</td>
            <td>Restlet API</td>
         </tr>
         <tr>
            <td>org.restlet.example</td>
            <td>Examples</td>
         </tr>
         <tr>
            <td>org.restlet.ext.atom</td>
            <td>Atom extension</td>
         </tr>
         <tr>
            <td>org.restlet.ext.crypto</td>
            <td>Cryptography extension including Amazon S3 and Windows Azure
            client authentication.</td>
         </tr>
         <tr>
            <td>org.restlet.ext.fileupload</td>
            <td>Integration with Apache FileUpload.</td>
         </tr>
         <tr>
            <td>org.restlet.ext.freemarker</td>
            <td>Integration with FreeMarker.</td>
         </tr>
         <tr>

            <td>org.restlet.ext.grizzly</td>
            <td>Integration with Grizzly NIO framework.</td>
         </tr>
         <tr>
            <td>org.restlet.ext.gwt</td>
            <td>Server-side integration with GWT.</td>
         </tr>
         <tr>
            <td>org.restlet.ext.httpclient</td>
            <td>Integration with Apache HTTP Client.</td>
         </tr>
         <tr>

            <td>org.restlet.ext.jaas</td>
            <td>Support for JAAS authentication and authorization framework.</td>
         </tr>
         <tr>
            <td>org.restlet.ext.jackson</td>
            <td>Integration with Jackson.</td>
         </tr>
         <tr>
            <td>org.restlet.ext.javamail</td>
            <td>Integration with JavaMail (POP3 and SMTP clients)</td>
         </tr>
         <tr>
            <td>org.restlet.ext.jaxb</td>
            <td>Integration with Java XML Binding (JAXB).</td>
         </tr>
         <tr>
            <td>org.restlet.ext.jaxrs</td>
            <td>Implementation of JAX-RS.</td>
         </tr>
         <tr>
            <td>org.restlet.ext.jdbc</td>
            <td>Integration with Java DataBase Connectivity (JDBC).</td>
         </tr>
         <tr>
            <td>org.restlet.ext.jetty</td>
            <td>Integration with Jetty.</td>
         </tr>
         <tr>
            <td>org.restlet.ext.jibx</td>
            <td>Integration with JiBX.</td>
         </tr>
         <tr>
            <td>org.restlet.ext.json</td>
            <td>Support for JSON representations.</td>
         </tr>
         <tr>
            <td>org.restlet.ext.lucene</td>
            <td>Integration with Apache Lucene.</td>
         </tr>
         <tr>
            <td>org.restlet.ext.net</td>
            <td>Integration with Java URLConnection class.</td>
         </tr>
         <tr>
            <td>org.restlet.ext.netty</td>
            <td>Integration with Netty.</td>
         </tr>
         <tr>
            <td>org.restlet.ext.odata</td>
            <td>Support for the OData Services.</td>
         </tr>
         <tr>
            <td>org.restlet.ext.rdf</td>
            <td>Support for the RDF parsing and generation.</td>
         </tr>
         <tr>
            <td>org.restlet.ext.rome</td>
            <td>Integration with ROME.</td>
         </tr>
         <tr>
            <td>org.restlet.ext.servlet</td>
            <td>Integration with Servlet API.</td>
         </tr>
         <tr>
            <td>org.restlet.ext.simple</td>
            <td>Integration with Simple Framework.</td>
         </tr>
         <tr>
            <td>org.restlet.ext.slf4j</td>
            <td>Integration with SLF4J.</td>
         </tr>
         <tr>
            <td>org.restlet.ext.spring</td>
            <td>Integration with Spring Framework.</td>
         </tr>
         <tr>
            <td>org.restlet.ext.ssl</td>
            <td>Support for SSL utilities and integration with jSSLutils library.
        </td>
         </tr>
         <tr>
            <td>org.restlet.ext.velocity</td>
            <td>Integration with Apache Velocity.</td>
         </tr>
         <tr>
            <td>org.restlet.ext.wadl</td>
            <td>Support the WADL specification.</td>
         </tr>
         <tr>
            <td>org.restlet.ext.xdb</td>
            <td>Integration with Oracle 11g XML DB feature.</td>
         </tr>
         <tr>
            <td>org.restlet.ext.xml</td>
            <td>Support for XML and XSLT representations.</td>
         </tr>
         <tr>
            <td>org.restlet.ext.xstream</td>
            <td>Integration with XStream.</td>
         </tr>
         <tr>
            <td>org.restlet.test</td>
            <td>Test module.</td>
         </tr>
      </table>

   <h4>Alternative repository configuration</h4>

   <p>
      Sometimes its more convenient to declare the same repository for all of your projects. 
      To do so, just go to your Maven installation directory. Open and edit <code>conf/settings.xml</code> 
      file and add to the <code>&lt;profiles&gt;</code> section the following code:
   </p>
	
   <pre class="xml:nocontrols:nogutter">
&lt;profile&gt;
  &lt;id&gt;restlet&lt;/id&gt;
  &lt;repositories&gt;
     &lt;repository&gt;
        &lt;id&gt;maven-restlet&lt;/id&gt;
        &lt;name&gt;Restlet Framework repository&lt;/name&gt;
        &lt;url&gt;http://maven.restlet.com&lt;/url&gt;
     &lt;/repository&gt;
  &lt;/repositories&gt;
&lt;/profile&gt;
   </pre>
   
   <p>
      Just after the <code>&lt;/profiles&gt;</code> add the following:
   </p>
   <pre class="xml:nocontrols:nogutter">
&lt;activeProfiles&gt;
    &lt;activeProfile&gt;restlet&lt;/activeProfile&gt;
&lt;/activeProfiles&gt;
   </pre>
