<#import "/library.ftl" as lib>

<#macro h2 title anchor hide=false>
    <div class='content container'>
	  <h3><a class="anchor" name="${anchor}"></a>${title}</h3>
	  <div<#if hide> style="display:none" </#if>><#nested></div>
    </div>
</#macro>

<#macro h3><h3><#nested></h3></#macro>

<#macro a href title="-"><a href="${href}?url"<#if title!="-"> title="${title}"</#if>><#nested></a></#macro>

