<#global javascript_files      = [] />
<#global stylesheet_files      = [] />
<#global sections              = hierarchy.sections />
<#global labels                = labels.labels />
<#if pp.sourceDirectory?contains("error")>
    <#global currentSection = "error" />
<#elseif (pp.sourceDirectory?index_of("/") > -1)>
    <#global currentSection = pp.sourceDirectory?substring(0, pp.sourceDirectory?index_of("/")) />
<#else>
        <#if (pp.sourceFileName?index_of(".") > -1) >
            <#global currentSection = pp.sourceFileName?substring(0, pp.sourceFileName?index_of(".")) />
        <#else>
            <#global currentSection = "-" />
        </#if>
        <#if currentSection = "index" >
            <#global currentSection = "-" />
        </#if>
</#if>
<#if !(currentSubSection?has_content)>
    <#global currentSubSection = pp.sourceFileName />
    <#assign tab = pp.sourceDirectory?split("/") />
    <#if (tab?size>1) && !(tab[1] == "")>
        <#global currentSubSection = tab[1] />
    <#else>
        <#if (pp.sourceFileName?index_of(".") > -1) >
            <#global currentSubSection = pp.sourceFileName?substring(0, pp.sourceFileName?index_of(".")) />
        <#else>
            <#global currentSubSection = pp.sourceFileName />
        </#if>
    </#if>
</#if>
