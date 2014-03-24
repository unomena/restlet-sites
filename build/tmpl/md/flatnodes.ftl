<#if toc?has_content>
    <@loop toc.ul />
</#if>

<#macro loop ul>
    <#list ul.li as section>
<#local key = section.a.@href?replace("/", ".") />
${version}.${key}
        <@loop section.ul />
    </#list>
</#macro>
