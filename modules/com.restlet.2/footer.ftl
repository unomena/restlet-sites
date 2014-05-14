<#if !(title?has_content)>
<#list sections.section as section>
      <#if section.@id == currentSection>
         <#assign title="Restlet - ${section.label['${language}']?trim}"  />
         <#list section.sections.section as subsection>
            <#if subsection.@id == currentSubSection>
               <#assign title="Restlet - ${section.label['${language}']?trim} - ${subsection.label['${language}']?trim}"  />
            </#if>
         </#list>
      </#if>
</#list>
</#if>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
   <head>
      <title>${title}</title>
      <link sizes="32x32" href="/images/favicon-restlet-com.gif" rel="icon">
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
      <meta name="keywords"  content="Web API, Restlet, APISpark, REST, Java, framework, toolkit, HTTP, GWT, Android, GAE, OSGi, SPDY" />
      <link rel="alternate"  type="application/atom+xml" href="/feeds/summary" title="${labels.summaryNoeliosBlog['${language}']?trim}" />
      <link rel="alternate"  type="application/rss+xml" href="http://blog.restlet.com/feed" title="${labels.noeliosBlog['${language}']?trim}" />
      <link rel="stylesheet" type="text/css" media="screen" href="/stylesheets/bootstrap.css" />
      <link rel="stylesheet" type="text/css" media="screen" href="/stylesheets/styles.css" />
      <link rel="stylesheet" type="text/css" media="screen" href="/stylesheets/bootstrap-responsive.css" />
      <link rel="stylesheet" type="text/css" media="print"  href="/stylesheets/main-print.css" />
<#list stylesheet_files as stylesheet_file>
      <link rel="stylesheet" type="text/css"            href="${stylesheet_file}" />
</#list>
<#if stylesheet??>
      <style>
      ${stylesheet}
      </style>
</#if>
<#if atomRepresentation??>
   <#noparse>
      ${atomRepresentation}
   </#noparse>
</#if>
<#list javascript_files as javascript_file>
      <script                type="text/javascript"     src="${javascript_file}"></script>
</#list>
<#if javascript??>
      <script type="text/javascript">
      ${javascript}
      </script>
</#if>
<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-32616835-1', 'restlet.com');
  ga('send', 'pageview');

</script>

   </head>

  <body<#if body_event_managers?has_content>${body_event_managers}</#if>>
    <div class="globalBg">
      <div class='topbar'>
        <div class='header'>
          <div class='container'>
            <a class='brand' href="/" title='Restlet'><img alt='Restlet Logo' height='136' src='/images/logo-restlet-sas.png' width='129' /></a>
            <ul class='nav'>
<#list sections.section as section>
   <#if (section.@hidden[0]!'false') != 'true'>
              <li><a<#if section.@id == currentSection> class="active"</#if> href="/${section.@id}" title="${section.label['${language}']?trim}" id="${section.@id}">${section.label['${language}']?trim}</a></li>
   </#if>
</#list>
            </ul>
          </div>
        </div>
      </div>

	  <div class="clearBoth"></div>
      <div class='hero ac header <#if !("-"=currentSection)>${currentSection} section</#if>'>
        <div class='container'>
  <#if header?has_content>${header}
  <#elseif "error"=currentSection><h3>${errorPageTitle!"Error page"} <#noparse>(${status.code})</#noparse></h3>
  <#else>
    <#list sections.section as section>
      <#if (section.@hidden[0]!'false') != 'true'>
        <#if section.@id == currentSection><h3>${section.label['${language}']?trim}</h3></#if> 
      </#if>
    </#list>
  </#if>
        </div>
      </div>

<#list sections.section as section>
   <#if (section.@hidden[0]!'false') != 'true' && (section.@id == currentSection) && section.sections.section?has_content>
      <div class="content container">
        <ul class="pages">
      <#list section.sections.section as subsection>
         <#if (subsection.@hidden[0]!'false') != 'true'>
            <#if !subsection.a?has_content>
          <li<#if subsection.@id == currentSubSection> class="active"</#if>><a href="/${section.@id}/${subsection.@id}" title="${subsection.label['${language}']?trim}">${subsection.label['${language}']?trim}</a></li>
            <#else>
          <li><a title="${subsection.label['${language}']?trim}"<#list subsection.a.@@ as attr> ${attr?node_name}="${attr}"</#list>>${subsection.label['${language}']?trim}</a></li>
            </#if>
         </#if>
      </#list>
        </ul>
      </div>
   </#if>
</#list>

      ${content}
      <div class="content footerWrapper">
        <div class="footer"><!-- <#if "error"!=currentSection><a href="https://github.com/restlet/restlet-sites/edit/master/modules/com.restlet/${pp.sourceFile}" title="Edit, comment this page">Edit, comment this page</a></#if> --></div>
      </div>
    </div>

    <div id="footer">
      <div class="container">
        <div class="span2 intro below">${labels.footer.intouch['${language}']}</div>
        <div class="span2a site"><h4><a href="http://blog.restlet.com/"><i class="blog-icon"></i>${labels.footer.blog['${language}']}</a></h4></div>
        <div class="span2b site"><h4><a href="https://twitter.com/restlet_com"><i class="follow-icon"></i>${labels.footer.twitter['${language}']}</a></h4></div>
        <div class="span4 newsletter"><form action="http://restlet.us4.list-manage1.com/subscribe/post?u=6e9d916ca1faf05c7dc49d21e&id=a8aa911b32" method="post" id="mc-embedded-subscribe-form" name="mc-embedded-subscribe-form" target="_blank"><input type="hidden" value="1" name="group[9053][1]" /><input type="hidden" value="2" name="group[9053][2]" /><input type="hidden" id="EMAIL" name="EMAIL" value=""/><span id="footerNewsLetterWrapper"><input type="email" name="EMAIL" required="required" placeholder="${labels.footer.newsletter['${language}']}"/><input type="submit" id="footerNewsLetterOkButton" value="OK"></span></form></div>
        <div class="clearBoth"></div>
        <div class="span2 intro below">${labels.footer.sites['${language}']}</div>
        <div class="span2a site">
          <h4><a href="http://restlet.com/"><img src="/images/logo-restlet-small.png" />Restlet</a></h4>
          <ul class="sub-list">
            <li><a href="http://restlet.com/products/">${labels.footer.restlet.product['${language}']}</a></li>
            <li><a href="http://restlet.com/services/">${labels.footer.restlet.services['${language}']}</a></li>
            <li><a href="http://restlet.com/about/">${labels.footer.restlet.about['${language}']}</a></li>
          </ul>
        </div>
        <div class="span2b site">
          <h4><a href="http://restlet.org/"><img src="/images/logo-restlet-framework-small.png" />Restlet Framework</a></h4>
          <ul class="sub-list">
            <li><a href="http://restlet.org/discover/">${labels.footer.restletframework.discover['${language}']}</a></li>
            <li><a href="http://restlet.org/download/">${labels.footer.restletframework.download['${language}']}</a></li>
            <li><a href="http://restlet.org/learn/">${labels.footer.restletframework.learn['${language}']}</a></li>
            <li><a href="http://restlet.org/participate/">${labels.footer.restletframework.participate['${language}']}</a></li>
          </ul>
        </div>
        <div class="span2c site">
          <h4><a href="http://apispark.com/"><img src="/images/logo-apispark-small.png" />APISpark</a></h4>
          <ul class="sub-list">
            <li><a href="http://apispark.com/features">${labels.footer.apispark.features['${language}']}</a></li>
            <li><a href="http://apispark.com/pricing">${labels.footer.apispark.pricing['${language}']}</a></li>
            <li><a href="http://apispark.com/docs/">${labels.footer.apispark.docs['${language}']}</a></li>
            <li><a href="http://apispark.com/about">${labels.footer.apispark.about['${language}']}</a></li>
          </ul>
        </div>
      </div>
      <div id="copyright">Copyright &copy; ${pp.now?string("yyyy")} Restlet - <a href="/legal" title="${labels.footer.copyright.legal['${language}']}">${labels.footer.copyright.legal['${language}']}</a></div>
    </div>
    <!-- generated ${pp.now} -->
   </body>
</html>
