<#global javascript_files      = [] />
<#global stylesheet_files      = [] />
<#global sections              = hierarchy.sections />
<#global labels                = labels.labels />
<#global messages              = messages.messages />
                
<#if (pp.sourceFileName?index_of(".") > -1) >
    <#global currentSubSection = pp.sourceFileName?substring(0, pp.sourceFileName?index_of(".")) />
<#else>
    <#global currentSubSection = pp.sourceFileName />
</#if>
<#if pp.sourceDirectory?contains("error")>
    <#global currentSection = "error" />
<#elseif (pp.sourceDirectory?index_of("/") > -1)>
    <#global currentSection = pp.sourceDirectory?substring(0, pp.sourceDirectory?index_of("/")) />
<#else>
    <#global currentSection = "-" />
</#if>

<#macro h3 title anchor="-">
    <div class='content container'>
    <div class='span900'>
	  <h3><#if !(anchor=="-")><a class="anchor" name="${anchor}"></a></#if>${title}</h3>
      <#nested>
    </div>
    </div>
</#macro>
