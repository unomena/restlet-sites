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
      <link rel="meta"       type="application/rdf+xml" href="http://www.restlet.org/about/doap"  title="DOAP" />
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
      <script type="text/javascript">
<#if javascript??>
      ${javascript}
</#if>
/*
         var _gaq = _gaq || [];
         _gaq.push(['_setAccount', 'UA-32616835-1']);
         _gaq.push(['_setDomainName', 'restlet.com']);
         _gaq.push(['_setAllowLinker', true]);
         _gaq.push(['_trackPageview']);
         (function() {
             var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
             ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
             var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
          })();
*/
      </script>
   </head>

  <body<#if body_event_managers?has_content>${body_event_managers}</#if>>
    <div class="globalBg">
      <div class='topbar'>
        <div class='header'>
          <div class='container'>
            <a class='brand' href="/" title='Restlet Framework'><img alt='Restlet Framework Logo' height='136' src='/images/logo-restlet-sas.png' width='129' /></a>
            <ul class='nav'>
<#list hierarchy?children.section as section>
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
    <#list hierarchy?children.section as section>
      <#if (section.@hidden[0]!'false') != 'true'>
        <#if section.@id == currentSection><h3>${section.label['${language}']?trim}</h3></#if> 
      </#if>
    </#list>
  </#if>
        </div>
      </div>

<#list sections.section as section>
   <#if (section.@hidden[0]!'false') != 'true' && (section.@id == currentSection)>
      <div class="content container">
        <ul class="pages">
      <#list section.sections.section as subsection>
         <#if (subsection.@hidden[0]!'false') != 'true'>
          <li<#if subsection.@id == currentSubSection> class="active"</#if>><a href="/${section.@id}/${subsection.@id}" title="${subsection.label['${language}']?trim}">${subsection.label['${language}']?trim}</a></li>
         </#if>
      </#list>
        </ul>
      </div>
   </#if>
</#list>

      ${content}
      <div class="content footerWrapper">
        <div class="footer"></div>
      </div>
    </div>

    <footer>
      <div class="container">
        <div class="span2 intro">${labels.footer.intouch['${language}']}</div>
        <div class="span2a"><a href="http://blog.restlet.com/"><strong><i class="blog-icon"></i>${labels.footer.blog['${language}']}</strong></a></div>
        <div class="span2b"><a href="https://twitter.com/restlet_com"><strong><i class="follow-icon"></i>${labels.footer.twitter['${language}']}</strong></a></div>
        <div class="span4 newsletter"><span id="footerNewsLetterWrapper"><input id="footerNewsLetter" value="${labels.footer.newsletter['${language}']}"/><span id="footerNewsLetterOkButton">OK</span></span></div>
        <div class="clearBoth"></div>
        <div class="emptySeparator"></div>
        <div class="span2 intro below">${labels.footer.sites['${language}']}</div>
        <div class="span2a site">
          <h4><img src="/images/logo-restlet-small.png" class="pull-left"/><a href="http://restlet.com">Restlet</a></h4>
          <ul class="sub-list">
            <li><a href="http://www.restlet.com/products">${labels.footer.restlet.product['${language}']}</a></li>
            <li><a href="http://www.restlet.com/services">${labels.footer.restlet.services['${language}']}</a></li>
            <li><a href="http://www.restlet.com/company">${labels.footer.restlet.about['${language}']}</a></li>
          </ul>
        </div>
        <div class="span2b site">
          <h4><img src="/images/logo-restlet-framework-small.png" class="pull-left"/><a href="http://restlet.org">Restlet Framework</a></h4>
          <ul class="sub-list">
            <li><a href="http://www.restlet.org/discover">${labels.footer.restletframework.discover['${language}']}</a></li>
            <li><a href="http://www.restlet.org/download">${labels.footer.restletframework.download['${language}']}</a></li>
            <li><a href="http://www.restlet.org/learn">${labels.footer.restletframework.learn['${language}']}</a></li>
            <li><a href="http://www.restlet.org/participate">${labels.footer.restletframework.participate['${language}']}</a></li>
          </ul>
        </div>
        <div class="span2c site">
          <h4><img src="/images/logo-apispark-small.png" class="pull-left"/><a href="http://apispark.com">APISpark</a></h4>
          <ul class="sub-list">
            <li><a href="http://apispark.com/features/user">${labels.footer.apispark.features['${language}']}</a></li>
            <li><a href="http://apispark.com/faq">${labels.footer.apispark.faq['${language}']}</a></li>
            <li><a href="http://apispark.com/about">${labels.footer.apispark.about['${language}']}</a></li>
          </ul>
        </div>
        <div class="span2d site">
          <h4><img src="/images/logo-apispark-support-small.png" class="pull-left"/><a href="http://support.apispark.org">APISpark Support</a></h4>
          <ul class="sub-list">
            <li><a href="http://support.apispark.org/tutorials">${labels.footer.apisparksupport.tutorial['${language}']}</a></li>
            <li><a href="http://desk.apispark.org/">${labels.footer.apisparksupport.helpdesk['${language}']}</a></li>
            <li><a href="http://support.apispark.org/roadmap">${labels.footer.apisparksupport.roadmap['${language}']}</a></li>
          </ul>
        </div>
      </div>
    </footer>
    <!-- ${messages.copyright['${language}']} -->
    <!-- generated ${pp.now} -->
   </body>
</html>
