<#global title= "*Default title*" />
<#global javascript_files      = [] />
<#global stylesheet_files      = [] />
<#if (pp.sourceFileName?index_of(".") > -1) >
	<#global currentSubSection = pp.sourceFileName?substring(0, pp.sourceFileName?index_of(".")) />
<#else>
	<#global currentSubSection = pp.sourceFileName />
</#if>