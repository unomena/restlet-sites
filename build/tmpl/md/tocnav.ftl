<#if flatnodes?has_content>
    <#list flatnodes as node>
<#assign key = node?replace("/", ".") />
<#if (node_index > 0)>
${key}.prev=${flatnodes[node_index - 1]}
</#if>
<#if (node_has_next)>
${key}.next=${flatnodes[node_index + 1]}
</#if>
    </#list>
</#if>
