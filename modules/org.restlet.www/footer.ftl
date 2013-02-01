<#if !(title?has_content)>
<#list hierarchy.sections.section as section>
      <#if section.@id == currentSection>
         <#assign title="Restlet - ${section.label?trim}"  />
      </#if>
</#list>
</#if>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
   <head>
      <title>${title}</title>
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <meta name="keywords"  content="REST, Java, framework, toolkit, HTTP, GWT, GAE, Android, JSE, JEE, Servlet, NIO" />
      <meta charset="utf-8">
      <link sizes="32x32" href="/images/favicon-restlet-org.gif" rel="icon">
      <!-- link type="image/x-icon" href="/images/icons/favicon.ico" rel="shortcut icon" -->
      <link rel="stylesheet" type="text/css"             href='/stylesheets/bootstrap.css' />
      <link rel="stylesheet" type="text/css"             href='/stylesheets/styles.css' />
      <link rel="stylesheet" type="text/css"             href='/stylesheets/bootstrap-responsive.css' />
      <link rel="alternate"  type="application/atom+xml" href="/feeds/summary" title="${labels.labels.summaryRestletBlog?trim}" />
      <link rel="alternate"  type="application/rss+xml"  href="http://blog.restlet.com/feed/?cat=15314" title="${labels.labels.restletBlog?trim}" />
      <link rel="meta"       type="application/rdf+xml"  href="http://www.restlet.org/about/doap"  title="DOAP" />
<#list stylesheet_files as stylesheet_file>
      <link rel="stylesheet" type="text/css"            href="${stylesheet_file}" />
</#list>
<#if stylesheet??>
      <style type="text/css">
      ${stylesheet}
      </style>
</#if>
<#list javascript_files as javascript_file>
      <script                type="text/javascript"     src="${javascript_file}"></script>
</#list>
      <script type="text/javascript">
<#if pp.sourceFile?matches("learn\\/[0-9]\\.[0-9]$") >
        $.cookie('branch', '${pp.sourceFile?substring(6)}', {path: '/' });
<#elseif pp.sourceFile?matches("learn\\/[0-9]\\.[0-9]/.*$") >
        $.cookie('branch', '${pp.sourceFile?substring(6, 9)}', {path: '/' });
</#if>
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

   <body>
    <div class='topbar'>
      <div class='header'>
        <div class='container'>
          <a class='brand' href="/" title='Restlet Framework'>
            <img alt='Restlet Framework Logo' height='136' width='129' src='/images/logo-restlet.png' />
          </a>
          <ul class='nav'>
    <#list hierarchy.sections.section as section>
      <#if !(section.@hidden?has_content)><li><a<#if section.@id == currentSection> class='active'</#if> href="/${section.@id}/">${section.title}</a></li></#if>
	</#list>
          </ul>
        </div>
      </div>
    </div>
    <div class='hero ac <#if headerClass?has_content>${headerClass}<#else>smallHeader</#if>'>
      <div class='container'>
<#compress>
  <#if header?has_content>${header}
  <#elseif "error"=currentSection><h3>${errorPageTitle!"Error page"} <#noparse>(${status.code})</#noparse></h3>
  <#else>
    <#list hierarchy.sections.section as section>
      <#if section.@id == currentSection>
       <h3>${section.label?trim}</h3>
      </#if>
	</#list>
  </#if>
</#compress>
      </div>
    </div>

<#compress>
<#list hierarchy.sections.section as section>
   <#if (section.@hidden[0]!'false') != 'true' && (section.@id == currentSection)>
    <div class="container subsections">
      <ul class="pages">
      <#list section.sections.section as subsection>
         <#if (subsection.@hidden[0]!'false') != 'true'>
        <li<#if subsection.@id == currentSubSection> class="active"</#if>><a href="/${section.@id}/${subsection.@id}">${subsection.label?trim}</a></li>
         </#if>
      </#list>
      </ul>
      <hr/>
      <div class="emptySeparator"></div>
    </div>
    <div class="clearBoth"></div>
   </#if>
</#list>
</#compress>
    
    <div class='container<#if "-"=currentSection> topics</#if> content'>
      ${content}
    </div>
    <div class="content footerWrapper">
        <div class="footer"></div>
    </div>

    <footer>
      <div class="container">
        <div class="span2 intro">Stay in touch:</div>
        <div class="span2a"><strong><i class="blog-icon"></i>Our blog</strong></div>
        <div class="span2b"><strong><i class="follow-icon"></i>Follow us</strong></div>
        <div class="span4 newsletter"><span id="footerNewsLetterWrapper"><input id="footerNewsLetter" value="Newsletter (no spam)"/><span id="footerNewsLetterOkButton">OK</span></span></div>
        <div class="clearBoth"></div>
        <div class="emptySeparator"></div>
        <div class="span2 intro below">Our sites:</div>
        <div class="span2a site">
          <h4><img src="/images/logo-restlet-small.png" class="pull-left"/><a href="http://restlet.com">Restlet</a></h4>
          <ul class="sub-list">
            <li><a href="http://www.restlet.com/products/">Products</a></li>
            <li><a href="http://www.restlet.com/services/">Services</a></li>
            <li><a href="http://www.restlet.com/company/">About us</a></li>
            <li><a href="http://www.restlet.com/products/">Legal ??</a></li>
          </ul>
        </div>
        <div class="span2b site">
          <h4><img src="/images/logo-restlet-framework-small.png" class="pull-left"/><a href="http://restlet.org">Restlet Framework</a></h4>
          <ul class="sub-list">
            <li><a href="http://www.restlet.org/discover/">Discover</a></li>
            <li><a href="http://www.restlet.org/download/">Download</a></li>
            <li><a href="http://www.restlet.org/learn/">Learn</a></li>
            <li><a href="http://www.restlet.org/participate/">Participate</a></li>
          </ul>
        </div>
        <div class="span2c site">
          <h4><img src="/images/logo-apispark-small.png" class="pull-left"/><a href="http://apispark.com">APISpark</a></h4>
          <ul class="sub-list">
            <li><a href="http://beta.apispark.com/features/all">Features</a></li>
            <!--<li><a href="">Pricing ??</a></li> -->
            <li><a href="">Catalog ??</a></li>
            <li><a href="">Legal ??</a></li>
          </ul>
        </div>
        <div class="span2d site">
          <h4><img src="/images/logo-apispark-support-small.png" class="pull-left"/><a href="http://support.apispark.org">APISpark Support</a></h4>
          <ul class="sub-list">
            <li><a href="http://support.apispark.org/tutorials">Tutorials</a></li>
            <li><a href="http://support.apispark.org/userGuide">User Guide</a></li>
            <li><a href="http://support.apispark.org/roadmap">Roadmap</a></li>
            <li><a href="http://support.apispark.org/helpdesk">Help Desk</a></li>
          </ul>
        </div>
      </div>
      <!-- Copyright &copy; 2005-2013 <a href="http://www.restlet.com">Restlet</a> -->
    </footer>
   </body>
</html>
