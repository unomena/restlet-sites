<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
   <head>
      <title>${title}</title>
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <meta name="keywords"  content="REST, Java, framework, toolkit, HTTP, GWT, GAE, Android, JSE, JEE, Servlet, NIO" />
      <meta charset="utf-8" /> 
      <link rel="stylesheet" type="text/css"             href='/static/app/css/layout.css' />
      <link rel="stylesheet" type="text/css"             href='/static/app/css/restlet_framework.css' />
      <script src="/static/app/js/libs/modernizr.js"></script>
   </head>
   <body>
   <!--[if IE 8]><div id="IE8"><div id="IE9"><![endif]-->
   <!--[if IE 9]><div id="IE9"><![endif]-->
    


<header class="followMe">
    <div class="container">
        <div class="navbar col-12" role="navigation">
             <div class="navbar-header">
                <button type="button" class="navbar-toggle"></button>
                <a class="brand" href="/"><img alt="Restlet" src="/static/app/img/logo.png" /></a>
            </div> <div class="collapse navbar-collapse"> <ul class="nav">
                    <li >
                        <a href="javascript:void(0)">Products<span class="indicator"></span></a>
                        <div class="sub_menu_wrapper">
                            <div class="container">
                                <ul>
                                    <li>
                                        <a href="/products/restlet-framework/">
                                            <img alt="Restlet Framework" src="/static/app/img/f_logo.png" />
                                            <p>Create and use Java APIs</p>
                                            <div class="border"></div>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="/products/apispark/">
                                            <img alt="APISpark" src="/static/app/img/a_logo.png" />
                                            <p>The first self-service platform for APIs</p>
                                            <div class="border"></div>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="/products/restlet-studio/">
                                            <img alt="Restlet Studio" src="/static/app/img/s_logo.png" />
                                            <p>Web-based API design</p>
                                            <div class="border"></div>
                                        </a>
                                    </li>
                                </ul>
                            </div>    
                        </div>    
                    </li>
                    <li class="on">
                        <a href="#">Technical Resources<span class="indicator"></span></a>
                        <div class="sub_menu_wrapper menu_option_2">
                            <div class="container">
                                <ul>
                                    <li>
                                        <a href="/technical-resources/restlet-framework">
                                            <img alt="Restlet Framework" src="/static/app/img/f_logo.png" />
                                        </a>
                                        <ul>
                                            <li><a href="/technical-resources/restlet-framework">Tutorials</a></li>
                                            <li><a href="/technical-resources/restlet-framework">User Guide</a></li>
                                            <li><a href="/technical-resources/restlet-framework/faq/">FAQ</a></li>
                                            <li><a href="">Download</a></li>
                                        </ul>    
                                    </li>
                                    <li>
                                        <a href="/technical-resources/apispark/guide">
                                            <img alt="APISpark" src="/static/app/img/a_logo.png" />
                                        </a>
                                        <ul>
                                            <li><a href="/technical-resources/apispark/tutorials">Tutorials</a></li>
                                            <li><a href="/technical-resources/apispark/guide">User Guide</a></li>
                                            <li><a href="/technical-resources/apispark/faq/">FAQ</a></li>
                                            <li><a target="_blank" href="http://apispark.restlet.com/signin">Sign In</a></li>
                                            <li><a href="">Helpdesk</a></li>
                                        </ul>
                                    </li>
                                    <li>
                                        <a href="/technical-resources/restlet-studio">
                                            <img alt="Restlet Studio" src="/static/app/img/s_logo.png" />
                                        </a>
                                        <ul>
                                            <li><a href="/technical-resources/restlet-studio">Tutorials</a></li>
                                            <li><a href="/technical-resources/restlet-studio">User Guide</a></li>
                                            <li><a href="/technical-resources/restlet-studio/faq/">FAQ</a></li>
                                            <li><a target="_blank" href="http://studio.restlet.com">Launch</a></li>
                                        </ul>
                                    </li>
                                </ul>
                            </div>
                        </div>        
                    </li>
                    <li >
                        <a href="/company/">Company</a>
                    </li>
                    
                </ul>
                <ul class="nav pull-right">
                    <li >
                        <a href="">Blog</a>
                    </li>
                    
                        <li><a href="http://apispark.restlet.com/signin">Sign In</a></li>
                        
                    
                    <li class="search">
                        <form action="#" id="frmSearch"> 
                            <input type="text" name="q" class="text" placeholder="Search"> 
                            <input type="submit" name="sa" class="submit"> 
                        </form>
                    </li>
                </ul>
            </div>    
        </div>    
    </div>
</header><!--/HEADER-->



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

	<!--[if IE 8]></div></div><![endif]-->
	<!--[if IE 9]></div><![endif]-->
      <script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
      <script src="/static/app/js/libs/jquery.form.js"></script>
      <script src="/static/app/js/libs/jquery.formset.js"></script>
      <script src="/static/app/js/libs/jquery.validate.js"></script>
      <script src="/static/app/js/libs/jquery-ui-1.10.3.custom.min.js"></script>
      <script src="/static/app/js/libs/tag-it.min.js"></script>
      <script src="/static/app/js/libs/backstretch.min.js"></script>
      <script src="/static/app/js/libs/stellar.js"></script>
      <script src="/static/app/js/libs/waypoints.min.js"></script>
      <script src="/static/app/js/libs/inViewport.js"></script>
      <script src="/static/app/js/libs/jquery.flexslider-min.js"></script>
      <script src="/static/app/js/libs/mightyslider.js"></script>
      <script src="/static/app/js/libs/jquery.easing.js"></script>
      <script src="/static/app/js/libs/jquery.visualNav.min.js"></script>
      <script src="/static/app/js/libs/os_detection.js"></script>
      <script src="/static/app/js/scripts.js"></script>
   </body>
</html>
