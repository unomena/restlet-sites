<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

    <title>Restlet | Technical Resources | APISpark</title>
    <meta name="description" content="" />
    <meta name="keywords" content="" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />


      <link rel="shortcut icon" href="/static/app/img/favicon.ico" />
      <link rel="stylesheet" href="/static/app/css/jquery.tagit.css" />
      <link rel="stylesheet" href="/static/app/css/jquery-ui-1.10.3.custom.css" />

    <link href='http://fonts.googleapis.com/css?family=Ubuntu:300,400,500,700' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="/static/app/css/normalize.css" />
    <link rel="stylesheet" href="/static/app/css/base_grid.css" />
    <link rel="stylesheet" href="/static/app/css/mightyslider.css" />
    <link rel="stylesheet" href="/static/app/css/flexslider.css" />
    <link rel="stylesheet" href="/static/app/css/layout.css?v=1" />
    <script src="/static/app/js/libs/modernizr.js"></script>

</head>
<body>



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

<div class="technical_documentation restlet_framework_tech_doc">



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
  </div>
</div>

<footer>
  <div class="container">
    <ul class="col-12">
      <li class="col-2"><a href="/"><img src="/static/app/img/footer_logo.png"></a></li>
      <li class="col-6">
        <span class="section_label">Products</span>
        <ul>
          <li>
            <a href="/products/restlet-framework/">Restlet Framework</a>
            <div class="clear"></div>
            <span><img src="/static/app/img/line.jpg"></span>
          </li>
          <li>
            <a href="/products/apispark/">APISpark</a>
          </li>
          <li>
            <a href="/products/restlet-studio/">Restlet Studio</a>
          </li>
        </ul>
      </li>
      <li>
        <span class="section_label">Newsletter</span>
        <ul>
          <li>
            <p>Keep up-to-date on Restlet News</p>
            <form action="#" id="frmSignUp">
                <input type="text" name="email" class="text" placeholder="Email Address">

                <button class="blue" type="button">sign up</button>
            </form>
          </li>
        </ul>
      </li>
    </ul>
    <ul class="col-12">
      <li class="col-2">&nbsp;</li>
      <li class="col-6">
        <span class="section_label">Technical Resources</span>
        <ul>
          <li>
            <a href="/technical-resources/restlet-framework">Restlet Framework</a>
            <ul class="no_float">
              <li><a href="/technical-resources/restlet-framework">Tutorials</a></li>
              <li><a href="/technical-resources/restlet-framework">User Guide</a></li>
              <li><a href="/technical-resources/restlet-framework/faq/">FAQ</a></li>
              <li><a href="#">Download</a></li>
            </ul>
            <div class="clear"></div>
            <span><img src="/static/app/img/line.jpg"></span>
          </li>
          <li>
            <a href="/technical-resources/apispark/guide">APISpark</a>
            <ul class="no_float">
              <li><a href="/technical-resources/apispark/tutorials">Tutorials</a></li>
              <li><a href="/technical-resources/apispark/guide">User Guide</a></li>
              <li><a href="/technical-resources/apispark/faq/">FAQ</a></li>
              <li><a href="http://apispark.restlet.com/signin">Sign In</a></li>
              <li><a href="#">Helpdesk</a></li>
            </ul>
          </li>
          <li>
            <a href="/technical-resources/restlet-studio">Restlet Studio</a>
            <ul class="no_float">
              <li><a href="/technical-resources/restlet-studio">Tutorials</a></li>
              <li><a href="/technical-resources/restlet-studio">User Guide</a></li>
              <li><a href="/technical-resources/restlet-studio/faq/">FAQ</a></li>
              <li><a href="http://studio.restlet.com">Launch</a></li>
            </ul>
          </li>
        </ul>
      </li>
      <li class="col-4">
        <ul>
          <li class="no_padding">
            <ul>
              <li class="social no_padding">
                <span class="section_label">Follow us</span>
                <ul class="social_btns">
                  <li>
                      <a class="fb" href="https://www.facebook.com/pages/APISpark-by-Restlet/728497413851019?ref=hl" target="_blank">
                        <img src="/static/app/img/fb.png">
                      </a>
                  </li>
                  <li>
                      <a class="tw" href="http://www.twitter.com/apispark" target="_blank">
                        <img src="/static/app/img/tw.png">
                      </a>
                  </li>
                  <li>
                      <a class="googleP" href="https://plus.google.com/112652328863457385757/" target="_blank">
                        <img src="/static/app/img/googleP.png">
                      </a>
                  </li>

                  <li>
                      <a class="li" href="http://www.linkedin.com/company/restlet" target="_blank">
                        <img src="/static/app/img/li.png">
                      </a>
                  </li>
                  <li>
                      <a class="vimeo" href="https://vimeo.com/channels/apispark/" target="_blank">
                        <img src="/static/app/img/vimeo.png">
                      </a>
                  </li>
                  <li>
                      <a class="eh" href="http://www.slideshare.net/restlet/presentations" target="_blank">
                        <img src="/static/app/img/eh.png">
                      </a>
                  </li>
                  <li><a class="email" href="mailto:contact@apispark.com"><img src="/static/app/img/email.png"></a></li>
                </ul>
              </li>
            </ul>
          </li>
        </ul>
      </li>
    </ul>
    <ul class="col-12">
      <li class="col-2">&nbsp;</li>
      <li class="col-6">
        <ul class="no_float">
          <li><a href="/company/"><span class="section_label">Company</span></a></li>
          <li><a href="#"><span class="section_label">Blog</span></a></li>
        </ul>
      </li>
      <li class="col-4">
        <span class="sparky pull-right"><img src="/static/app/img/footer_sparky.png"></span>
      </li>
    </ul>
    <div class="hr col-12"></div>
    <div class="col-12 bottom_links">
      <span class="copy">© 2014 Restlet, Inc.</span>
      <span class="privacy"><a href="/privacy/">Privacy Policy</a> &nbsp; | &nbsp; <a href="/terms/">Terms  Of Use</a></span>
      <span class="credit pull-right">Site built by <strong><a href="http://unomena.com/" target="_blank">Unomena</a></strong></span>
    </div>
  </div>
</footer>

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

  <script type="text/javascript">
    (function(f,b){if(!b.__SV){var a,e,i,g;window.mixpanel=b;b._i=[];b.init=function(a,e,d){function f(b,h){var a=h.split(".");2==a.length&&(b=b[a[0]],h=a[1]);b[h]=function(){b.push([h].concat(Array.prototype.slice.call(arguments,0)))}}var c=b;"undefined"!==typeof d?c=b[d]=[]:d="mixpanel";c.people=c.people||[];c.toString=function(b){var a="mixpanel";"mixpanel"!==d&&(a+="."+d);b||(a+=" (stub)");return a};c.people.toString=function(){return c.toString(1)+".people (stub)"};i="disable track track_pageview track_links track_forms register register_once alias unregister identify name_tag set_config people.set people.set_once people.increment people.append people.track_charge people.clear_charges people.delete_user".split(" ");
    for(g=0;g<i.length;g++)f(c,i[g]);b._i.push([a,e,d])};b.__SV=1.2;a=f.createElement("script");a.type="text/javascript";a.async=!0;a.src="//cdn.mxpnl.com/libs/mixpanel-2.2.min.js";e=f.getElementsByTagName("script")[0];e.parentNode.insertBefore(a,e)}})(document,window.mixpanel||[]);
    mixpanel.init("93f5221471dfd58f4d1823699fe71294");


  </script>
  <script type="text/javascript">
    var oneall_js_protocol = (("https:" == document.location.protocol) ? "https" : "http");
      document.write(unescape("%3Cscript src='" + oneall_js_protocol + "://apispark.api.oneall.com/socialize/library.js' type='text/javascript'%3E%3C/script%3E"));
  </script>
  <script type="text/javascript">
    oneall.api.plugins.social_login.build("social_login_link",
      { 'providers' : ['github', 'google', 'windowslive', 'yahoo', 'facebook', 'linkedin', 'stackexchange', 'wordpress'], 'callback_uri': 'https://apispark.rest-let.com/signin'.replace('http://', oneall_js_protocol + '://'), 'modal': true }
      );
  </script>
   </body>
</html>
