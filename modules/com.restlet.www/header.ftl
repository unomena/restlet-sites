<#import "/library.ftl" as lib>

<#macro h3 title anchor="-">
    <div class='content container'>
    <div class='span900'>
	  <h3><#if !(anchor=="-")><a class="anchor" name="${anchor}"></a></#if>${title}</h3>
      <#nested>
    </div>
    </div>
</#macro>
