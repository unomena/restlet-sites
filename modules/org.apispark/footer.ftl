<#if !(title?has_content)>
<#list sections.section as section>
    <#if section.@id == currentSection>
        <#assign title="APISpark | ${section.label?trim}"  />
        <#list section.sections.section as subsection>
            <#if subsection.@id == currentSubSection>
                <#assign title="APISpark | ${section.label?trim} | ${subsection.label?trim}"  />
            </#if>
        </#list>
    </#if>
</#list>
</#if>
<!DOCTYPE html>
<html lang="en">
   <head>
      <title>${title}</title>
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <meta name="keywords"  content="apispark,web, apis" />
      <meta charset="utf-8">
      <link rel="icon" href="/images/favicon-api-support.gif" />
      <link rel="stylesheet" type="text/css"             href='/stylesheets/bootstrap.css' />
      <link rel="stylesheet" type="text/css"             href='/stylesheets/styles.css' />
      <link rel="stylesheet" type="text/css"             href='/stylesheets/bootstrap-responsive.css' />
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

  ga('create', 'UA-32615734-2', 'apispark.org');
  ga('send', 'pageview');

</script>
   </head>

   <body>
    <div class="topbar">
        <div class="header">
            <div class="container">
                <a class="brand" href="/" title="APISpark Support"><img alt="APISpark Support Logo" src="/images/apispark-support-logo.png" height="132" width="348" /></a>
                <ul class='nav'>
    <#list sections.section as section>
        <#if !(section.@hidden?has_content)>
                <#if !section.a?has_content>
          <li><a<#if section.@id == currentSection> class='active'</#if> href="/${section.@id}<#if section.sections.section?has_content>/</#if>">${section.title}</a></li>
            <#else>
          <li><a title="${section.label?trim}"<#list section.a.@@ as attr> ${attr?node_name}="${attr}"</#list>>${section.label?trim}</a></li>
            </#if>
        </#if>
    </#list>
                </ul>
                <div id="toolSignInButtonWrapper">
                    <div id="toolSignInButton">
                        <a class="rpxnow" onclick="return false;"
                            href="http://apispark.rpxnow.com/openid/v2/signin?token_url=http%3A%2F%2Fapispark.com/signin">Sign
                            in</a>
                    </div>
                </div>
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
       <h3>${section.label?trim}</h3>
      </#if>
	</#list>
  </#if>
</#compress>
      </div>
    </div>
    ${subheader!""}

<#compress>
<#list sections.section as section>
   <#if (section.@hidden[0]!'false') != 'true' && (section.@id == currentSection) && section.sections.section?has_content>
    <div class="container subsections">
      <ul class="pages">
      <#list section.sections.section as subsection>
         <#if (subsection.@hidden[0]!'false') != 'true'>
        <li<#if subsection.@id == currentSubSection> class="active"</#if>><a href="/${section.@id}/${subsection.@id}">${subsection.label?trim}</a></li>
         </#if>
      </#list>
      </ul>
      <hr/>
    </div>
    <div class="clearBoth"></div>
   </#if>
</#list>
</#compress>
    <div class='container content<#if "-"=currentSection> topics</#if>'>
      ${content}
    </div>
    <div class="content footerWrapper">
        <div class="footer"><!-- <#if "error"!=currentSection><a href="https://github.com/restlet/restlet-sites/edit/master/modules/org.apispark/${pp.sourceFile}" title="Edit, comment this page">Edit, comment this page</a></#if> --></div>
    </div>

    <div id="footer">
      <div class="container">
        <div class="span2 intro below">${labels.footer.intouch['${language}']}</div>
        <div class="span2a site"><h4><a href="http://blog.restlet.com/"><i class="blog-icon"></i>${labels.footer.blog['${language}']}</a></h4></div>
        <div class="span2b site"><h4><a href="https://twitter.com/apispark"><i class="follow-icon"></i>${labels.footer.twitter['${language}']}</a></h4></div>
        <div class="span4 newsletter"><form action="http://restlet.us4.list-manage1.com/subscribe/post?u=6e9d916ca1faf05c7dc49d21e&id=a8aa911b32" method="post" id="mc-embedded-subscribe-form" name="mc-embedded-subscribe-form" target="_blank"><input type="hidden" value="2" name="group[9053][2]" /><input type="hidden" id="EMAIL" name="EMAIL" value=""/><span id="footerNewsLetterWrapper"><input type="email" name="EMAIL" required="required" placeholder="${labels.footer.newsletter['${language}']}"/><input type="submit" id="footerNewsLetterOkButton" value="OK"></span></form></div>
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
          <h4><a href="http://apispark.com/"><img src="/images/logo-apispark-small.png"/>APISpark</a></h4>
          <ul class="sub-list">
            <li><a href="http://apispark.com/features/user">${labels.footer.apispark.features['${language}']}</a></li>
            <li><a href="http://apispark.com/faq">${labels.footer.apispark.faq['${language}']}</a></li>
            <li><a href="http://apispark.com/about">${labels.footer.apispark.about['${language}']}</a></li>
            <li><a href="http://apispark.com/pricing">${labels.footer.apispark.pricing['${language}']}</a></li> 
         </ul>
        </div>
        <div class="span2d site">
          <h4><a href="http://apispark.org/"><img src="/images/logo-apispark-support-small.png"/>APISpark Support</a></h4>
          <ul class="sub-list">
            <li><a href="http://apispark.org/tutorials/">${labels.footer.apisparksupport.tutorial['${language}']}</a></li>
            <li><a href="http://desk.apispark.org/" target="_blank">${labels.footer.apisparksupport.helpdesk['${language}']}</a></li>
            <li><a href="http://apispark.org/roadmap">${labels.footer.apisparksupport.roadmap['${language}']}</a></li>
          </ul>
        </div>
      </div>
      <div id="copyright">&copy; ${pp.now?string("yyyy")} Restlet, Inc. - All rights reserved - <a href="http://apispark.com/terms" title="${labels.footer.copyright.terms['${language}']}">${labels.footer.copyright.terms['${language}']}</a> - <a href="http://apispark.com/privacy" title="${labels.footer.copyright.privacy['${language}']}">${labels.footer.copyright.privacy['${language}']}</a></div>
    </div>
    <!-- generated ${pp.now} -->
   </body>
</html>
