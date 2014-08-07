<#if !(title??)>
<#list sections.section as section>
      <#if section.@id == currentSection>
         <#assign title="Restlet Framework - ${section.label?trim}"  />
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
      <meta charset="utf-8" /> 
      <#if ("learn"=currentSection!"") && ("guide" == currentSubSection!"")>
      <meta name="description" content="Restlet Framework Guide - Learn More about Coding Powerful and Scalable Custom Web APIs in Java." />
	  </#if>
	  <#if ("learn"=currentSection!"") && ("tutorial" == currentSubSection!"")>
      <meta name="description" content="Restlet Framework Tutorial - Learn More about Coding Powerful and Scalable Custom Web APIs in Java." />
	  </#if>     
      ${metaheader!""}
      <link type="image/gif" href="/images/favicon-restlet-org.gif" rel="icon">
      <link rel="stylesheet" type="text/css"             href='/stylesheets/bootstrap.css' />
      <link rel="stylesheet" type="text/css"             href='/stylesheets/bootstrap-responsive.css' />
      <link rel="stylesheet" type="text/css"             href='/stylesheets/styles.css' />
      <link rel="alternate"  type="application/atom+xml" href="/feeds/summary" title="${labels.summaryRestletBlog?trim}" />
      <link rel="alternate"  type="application/rss+xml"  href="http://blog.restlet.com/feed/?cat=15314" title="${labels.restletBlog?trim}" />
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
      <script                type="text/javascript"     src="/javascript/jquery-1.9.0.min.js"></script>
      <script                type="text/javascript"     src="/javascript/jquery-cookie.js"></script>
      <script                type="text/javascript"     src="/javascript/common.js"></script>
<#if ("learn"=currentSection!"") && (("javadocs" == currentSubSection!"") || ("guide" == currentSubSection!"") || ("tutorial" == currentSubSection!""))>
      <script                type="text/javascript"     src="/javascript/jsclass-core.js"></script>
      <script                type="text/javascript"     src="/javascript/json-minified.js"></script>
      <script                type="text/javascript"     src="/javascript/restlet-client.js"></script>
      <script                type="text/javascript"     src="/javascript/bootstrap.min.js"></script>
      <script                type="text/javascript"     src="/javascript/data.js"></script>
    <#if ("javadocs" == currentSubSection!"")>
      <script                type="text/javascript"     src="/javascript/javadocs.js"></script>
    <#elseif ("guide" == currentSubSection!"")>
      <script                type="text/javascript"     src="/javascript/userguide.js"></script>
    <#elseif ("tutorial" == currentSubSection!"")>
      <script                type="text/javascript"     src="/javascript/tutorial.js"></script>
    </#if>
</#if>
      <script type="text/javascript">
<#if pp.sourceFile?matches("learn\\/[0-9]\\.[0-9]$") >
        $.cookie('branch', '${pp.sourceFile?substring(6)}', {path: '/' });
<#elseif pp.sourceFile?matches("learn\\/[0-9]\\.[0-9]/.*$") >
        $.cookie('branch', '${pp.sourceFile?substring(6, 9)}', {path: '/' });
</#if>
<#if javascript??>
      ${javascript}
</#if>

<#if ("learn"=currentSection!"") && (("javadocs" == currentSubSection!"") || ("guide" == currentSubSection!"") || ("tutorial" == currentSubSection!""))>
    $(document).ready(function() {
        init($("#cBranch"));
    });
</#if>
      </script>
   </head>
   <body>
   <!--[if IE 8]><div id="IE8"><div id="IE9"><![endif]-->
   <!--[if IE 9]><div id="IE9"><![endif]-->
    <div class='topbar'>
      <div class='header'>
        <div class='container'>
          <a class='brand' href="/" title='Restlet Framework'>
            <img alt='Restlet Framework Logo' height='136' width='129' src='/images/logo-restlet-new.png' />
          </a>
          <ul class='nav'>
    <#list sections.section as section>
      <#if !(section.@hidden?has_content)><li><a<#if section.@id == currentSection> class='active ${section.@id}link'<#else> class="${section.@id}link"</#if> href="/${section.@id}/">${section.title}</a></li></#if>
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
    <#list sections.section as section>
      <#if section.@id == currentSection>
       <h3>${section.label?trim} <span id="search"><div class="search-div">
<script>
  (function() {
    var cx = '003179985452155369789:any5hjk8wii';
    var gcse = document.createElement('script');
    gcse.type = 'text/javascript';
    gcse.async = true;
    gcse.src = (document.location.protocol == 'https:' ? 'https:' : 'http:') +
        '//www.google.com/cse/cse.js?cx=' + cx;
    var s = document.getElementsByTagName('script')[0];
    s.parentNode.insertBefore(gcse, s);
  })();
</script>
<gcse:searchbox-only></gcse:searchbox-only>
       </div></span></h3>
      </#if>
	</#list>
  </#if>
</#compress>
      </div>
    </div>

<#compress>
<#list sections.section as section>
   <#if (section.@id == currentSection) && section.sections.section?has_content>
    <div class="container subsections">
      <ul class="pages">
      <#list section.sections.section as subsection>
         
        <li<#if subsection.@id == currentSubSection> class="active"</#if>><a href="/${section.@id}/${subsection.@id}">${subsection.label?trim}</a></li>
         
      </#list>
      </ul>
    </div>
    <div class="clearBoth"></div>
   </#if>
</#list>
</#compress>
    <div class='container<#if "-"=currentSection> topics</#if> content'>
    <#if (("learn"=currentSection!"") && (("guide" == currentSubSection!"") || ("tutorial" == currentSubSection!"")))>${editButton!""}</#if><#if ("learn"=currentSection!"") && (("javadocs" == currentSubSection!"") || ("guide" == currentSubSection!"") || ("tutorial" == currentSubSection!""))>${branchSwitch!""}</#if>
		${content}
    </div>
    <div class="content footerWrapper">
        <div class="footer">
        <br>
        <#if (("learn"=currentSection!"") && (("guide" == currentSubSection!"") || ("tutorial" == currentSubSection!"") || ("roadmap" == currentSubSection!"")) || ("discover"=currentSection!"") && (("firststeps" == currentSubSection!"") || ("faq" == currentSubSection!"")) || ("participate"=currentSection!"") && (("index" == currentSubSection!"")))>
        <div id="disqus_thread"></div>
    <script type="text/javascript">
        /* * * CONFIGURATION VARIABLES: EDIT BEFORE PASTING INTO YOUR WEBPAGE * * */
        var disqus_shortname = 'restletblog'; // required: replace example with your forum shortname

        /* * * DON'T EDIT BELOW THIS LINE * * */
        (function() {
            var dsq = document.createElement('script'); dsq.type = 'text/javascript'; dsq.async = true;
            dsq.src = '//' + disqus_shortname + '.disqus.com/embed.js';
            (document.getElementsByTagName('head')[0] || document.getElementsByTagName('body')[0]).appendChild(dsq);
        })();
    </script>
    <noscript>Please enable JavaScript to view the <a href="http://disqus.com/?ref_noscript">comments powered by Disqus.</a></noscript>
    <a href="http://disqus.com" class="dsq-brlink">comments powered by <span class="logo-disqus">Disqus</span></a>
        </div>
        </#if>
    </div>

    <div id="footer">
      <div class="container">
        <div class="span2 intro below">${labels.footer.intouch['${language}']}</div>
        <div class="span2a site"><h4><a href="http://blog.restlet.com/"><i class="blog-icon"></i>${labels.footer.blog['${language}']}</a></h4></div>
        <div class="span2b site"><h4><a href="https://twitter.com/restlet_org"><i class="follow-icon"></i>${labels.footer.twitter['${language}']}</a></h4></div>
		${googlelinkVerification!""}
<!--        <div class="span2b site"><h4><a href="/download/notifications"><i class="notifications-icon"></i>${labels.notifications.submit['${language}']}</a></h4></div> -->
        <div class="span4 newsletter">
        <span id="footerNewsLetterWrapper" align="right">
	        <input type="email" id="footerNewsLetterEmail" name="EMAIL" class="required email" placeholder="${labels.footer.newsletter['${language}']}"/>
	        <button id="footerNewsLetterOkButton" class="button">OK</button>
        </span>
        </div>
        <div class="clearBoth"></div>
        <div class="span2 intro below">${labels.footer.sites['${language}']}</div>
        <div class="span2a site">
          <h4><a href="http://restlet.com/"><img src="/images/logo-restlet-framework-small.png" />Restlet</a></h4>
          <ul class="sub-list">
            <li><a class="discover" href="/discover/">${labels.footer.restletframework.discover['${language}']}</a></li>
            <li><a class="download" href="/download/">${labels.footer.restletframework.download['${language}']}</a></li>
            <li><a class="learn" href="/learn/">${labels.footer.restletframework.learn['${language}']}</a></li>
            <li><a class="participate" href="/participate/">${labels.footer.restletframework.participate['${language}']}</a></li>
            <li><a href="/about/">${labels.footer.restlet.about['${language}']}</a></li>
          </ul>
        </div>
        <div class="span2b site">
          <h4><a href="${ant["apispark.root.url"]!""}/" class="apisparklink"><img src="/images/logo-apispark-small.png"/>APISpark</a></h4>
          <ul class="sub-list">
            <li><a href="${ant["apispark.root.url"]!""}/features" class="apisparklink">${labels.footer.apispark.features['${language}']}</a></li>
            <li><a href="${ant["apispark.root.url"]!""}/pricing" class="apisparklink">${labels.footer.apispark.pricing['${language}']}</a></li>
            <li><a href="${ant["apispark.root.url"]!""}/docs/" class="apisparklink">${labels.footer.apispark.docs['${language}']}</a></li>
            <li><a href="${ant["apispark.root.url"]!""}/catalog" class="apisparklink">${labels.footer.apispark.catalog['${language}']}</a></li>
            <li><a href="http://support.apispark.com/" class="apisparklink">${labels.footer.apispark.helpdesk['${language}']}</a></li>
          </ul>
        </div>
      </div>
      <div id="copyright">Copyright &copy; ${pp.now?string("yyyy")} Restlet - <a href="/legal" title="${labels.footer.copyright.legal['${language}']}">${labels.footer.copyright.legal['${language}']}</a></div>
    </div>
    <!-- generated ${pp.now} -->
<#if footer??>
    ${footer}
</#if>
	<!--[if IE 8]></div></div><![endif]-->
	<!--[if IE 9]></div><![endif]-->
   </body>
</html>
