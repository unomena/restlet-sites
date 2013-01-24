<#if (pp.sourceFileName?index_of(".") > -1) >
	<#global currentSubSection = pp.sourceFileName?substring(0, pp.sourceFileName?index_of(".")) />
<#else>
	<#global currentSubSection = pp.sourceFileName />
</#if>
<#global title                 = "Restlet" /> 
<#global feed_noelios          = " " />
<#global javascript_files      = [] />
<#global stylesheet_files      = [] />
<#global body_event_managers   = " " />