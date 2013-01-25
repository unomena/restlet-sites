<#global feed_noelios          = " " />
<#global javascript_files      = [] />
<#global stylesheet_files      = [] />
<#global body_event_managers   = " " />
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