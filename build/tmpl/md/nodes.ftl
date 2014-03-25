${version}.index.md.title=Table of content
${version}.index.md.url=
<#if toc?has_content>
    <@loop toc.ul />
</#if>

<#macro loop ul>
    <#list ul.li as section>
<#local key = section.a.@href?replace("/", ".") />
<#local pathtab = section.a.@href?split("/") />
<#if (pathtab?size > 2)>
${version}.${key}.up=${version}.<#list pathtab as path><#if (path_index < (pathtab?size - 2))>${path}.</#if></#list>index.md
</#if>
${version}.${key}.title=${section.a}
<#if section.a.@href?ends_with("index.md")>
${version}.${key}.url=${section.a.@href?substring(0, (section.a.@href?length - 8))}
<#elseif section.a.@href?ends_with(".md")>
${version}.${key}.url=${section.a.@href?substring(0, (section.a.@href?length - 3))}
<#else>
${version}.${key}.url=${section.a.@href}
</#if>
        <@loop section.ul />
    </#list>
</#macro>
