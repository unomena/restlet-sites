<#global javascript_files=[] />
<#global stylesheet_files=[] />
<#global sections              = hierarchy.sections />
<#global labels                = labels.labels />
<#if pp.sourceDirectory?contains("error")>
    <#global currentSection = "error" />
<#elseif (pp.sourceDirectory?index_of("/") > -1)>
    <#global currentSection = pp.sourceDirectory?substring(0, pp.sourceDirectory?index_of("/")) />
<#else>
    <#global currentSection = "-" />
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

<#global campaignPopupSection>
<!-- Popup panel -->
    <div class="modalDialog kinlane" id="deployModal">
		<div>
    		<div class="close-button"></div>
        	<div class="doc-preview"></div>
        	<div class="doc-desc">
        		<div class="doc-title">
                	<h3>Free Developer Guide</h3>
            	</div>
            	<div class="doc-content">
                	<h4><strong>A field guide to web APIs</strong> By Kin Lane</h4>
                	<div class="text">Kin Lane, API Evangelist, has been in the technology space for over 20 years and wants to help the world understand the great value and potential that is growing in the web API market.</div>
                	<div class="avatar"></div>
                	<div class="clearBoth"></div>
            	</div>
            	<div class="doc-download">
                	<input type="email" id="campaignEmail" required="required" placeholder="Email"/>
                	<button id="campaignButton">Download</button>
            	</div>
            	<div class="doc-footer">
                	<p>Your details will never be shared to a 3rd party. We hate spam just as much as you do.</p>
            	</div>
            	<div class="clearBoth"></div>
			</div>
			<div class="clearBoth"></div>
		</div>
	</div>
</#global>

<#global downloadSection>
<div class="downloadsection">
    <div class='span3 center section'>
        <form class="form-horizontal">
            <div class="control-group">
                <label class="control-label">Release:</label>
                <div class="controls">
                    <div class="btn-group">
                        <button id="cBranch-bt" class="btn no-radius" data-toggle="dropdown"><strong>Stable</strong></button>
                        <button class="btn dropdown-toggle no-radius" data-toggle="dropdown"><strong><span class="caret"></span></strong></button>
                        <ul id="cBranch" class="dropdown-menu" role="menu" aria-labelledby="drop5">
                            <!-- dropdown menu links -->
                        </ul>
                    </div>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">Edition:</label>
                <div class="controls">
                    <div class="btn-group">
                        <button id="cEdition-bt" class="btn no-radius" data-toggle="dropdown"><strong>JSE</strong></button>
                        <button class="btn dropdown-toggle no-radius"
                            data-toggle="dropdown">
                            <strong><span class="caret"></span></strong>
                        </button>
                        <ul id="cEdition" class="dropdown-menu">
                            <!-- dropdown menu links -->
                        </ul>
                    </div>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">Distribution:</label>
                <div class="controls">
                    <div class="btn-group">
                        <button id="cDistribution-bt" class="btn no-radius" data-toggle="dropdown"><strong>ZIP file</strong></button>
                        <button class="btn dropdown-toggle no-radius" data-toggle="dropdown"><strong><span class="caret"></span></strong></button>
                        <ul id="cDistribution" class="dropdown-menu">
                            <!-- dropdown menu links -->
                        </ul>
                    </div>
                </div>
            </div>
        </form>
    </div>
    <div id="download" class='span3 right section download'>
                            <!-- download link -->
    </div>
    <div class="span3 right section download download-description-list">
      <ul>
        <li>Open Source</li>
        <li>RESTful</li>
        <li>Client &amp; Server</li>
        <li>Scalable</li>
        <li>Secure</li>
      </ul>
    </div>
 	<div class="clearBoth"></div>
</div>
</#global>

<#global archivesSection>
<div class="downloadsection">
    <div class='span3 center section'>
        <form class="form-horizontal">
            <div class="control-group">
                <label class="control-label">Branch:</label>
                <div class="controls">
                    <div class="btn-group">
                        <button id="cBranch-bt" class="btn no-radius" data-toggle="dropdown"><strong>2.1</strong></button>
                        <button class="btn dropdown-toggle no-radius" data-toggle="dropdown"><strong><span class="caret"></span></strong></button>
                        <ul id="cBranch" class="dropdown-menu" role="menu" aria-labelledby="drop5">
                            <!-- dropdown menu links -->
                        </ul>
                    </div>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">Release:</label>
                <div class="controls">
                    <div class="btn-group">
                        <button id="cRelease-bt" class="btn no-radius" data-toggle="dropdown"><strong>2.1.7</strong></button>
                        <button class="btn dropdown-toggle no-radius" data-toggle="dropdown"><strong><span class="caret"></span></strong></button>
                        <ul id="cRelease" class="dropdown-menu" role="menu" aria-labelledby="drop5">
                            <!-- dropdown menu links -->
                        </ul>
                    </div>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">Edition:</label>
                <div class="controls">
                    <div class="btn-group">
                        <button id="cEdition-bt" class="btn no-radius" data-toggle="dropdown"><strong>Zip</strong></button>
                        <button class="btn dropdown-toggle no-radius"
                            data-toggle="dropdown">
                            <strong><span class="caret"></span></strong>
                        </button>
                        <ul id="cEdition" class="dropdown-menu">
                            <!-- dropdown menu links -->
                        </ul>
                    </div>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">Distribution:</label>
                <div class="controls">
                    <div class="btn-group">
                        <button id="cDistribution-bt" class="btn no-radius" data-toggle="dropdown"><strong>ZIP file</strong></button>
                        <button class="btn dropdown-toggle no-radius" data-toggle="dropdown"><strong><span class="caret"></span></strong></button>
                        <ul id="cDistribution" class="dropdown-menu">
                            <!-- dropdown menu links -->
                        </ul>
                    </div>
                </div>
            </div>
        </form>
    </div>
    <div id="download" class='span3 right section download'>
                            <!-- download link -->
    </div>
    <div class="span3 right section download download-description-list">
      <ul>
        <li>Open Source</li>
        <li>RESTful</li>
        <li>Client &amp; Server</li>
        <li>Scalable</li>
        <li>Secure</li>
      </ul>
    </div>
    <div class="clearBoth"></div>
</div>
</#global>

<#global branchSwitch>
<div class='center branchwitch'>
    <form class="form-horizontal">
        <div class="control-group">
            <label class="control-label">Branch:</label>
            <div class="controls">
                <div class="btn-group">
                    <button id="cBranch-bt" class="btn no-radius" data-toggle="dropdown"><strong>Stable</strong></button>
                    <button class="btn dropdown-toggle no-radius" data-toggle="dropdown"><strong><span class="caret"></span></strong></button>
                    <ul id="cBranch" class="dropdown-menu" role="menu" aria-labelledby="drop5">
                        <!-- dropdown menu links -->
                    </ul>
                </div>
            </div>
        </div>
    </form>
</div>
</#global>
<#global editButton>
<div class='center editButton'>
        <div class="control-group">
            <div class="controls">
                    <div class="btn no-radius"><strong><a href="https://github.com/restlet/restlet-sites/edit/master/modules/com.restlet/${pp.sourceFile?replace("html", "md")}" title="Edit this page">Edit</a></strong></div>
            </div>
        </div>
</div>
</#global>

<#macro navigationtitle sourcedirectory filepath version>
<#local key = (sourcedirectory?substring(16) + filepath?substring(0, (filepath?length) -5) + ".md")?replace("/", ".") />
<#if nodes[(version + "."  + key + ".title")]?has_content>${nodes[(version + "."  + key + ".title")]}</#if>
</#macro>

<#macro navigationbar sourcedirectory filepath version>
    <#local key = (sourcedirectory?substring(16) + filepath?substring(0, (filepath?length) -5) + ".md")?replace("/", ".") />
<div class="guide navigation bar">
    <div class="clearBoth"></div>
    <hr />
    <#if nodes[(version + "." + key + ".title")]?has_content>
        <@navigationlink nodes key "prev" "Previous" version/>
        <@navigationlink nodes key "up" "Up" version />
        <@navigationlink nodes key "next" "Next" version />
    </#if>
</div>
</#macro>

<#macro navigationlink map key property label version>
<div class="guide navigation ${property}">
    <#if map[version + "."  + key + "." + property]?has_content>
<#local source=nodes[version + "."  + key + ".url"]>
<#local tab=source?split("/") />
<#local target=nodes[map[version + "."  + key + "." + property] + ".url"]>
<div><a href="<#list tab as segment><#if segment_has_next>../</#if></#list>${target}">${label}</a></div>
<div>${nodes[map[version + "."  + key + "." + property] + ".title"]}</div>
    <#else>
         <#if "up" == property>
<div><a href="/learn/guide/${version}">${label}</a></div>
<div>Table of contents</div>
         <#else>
&nbsp;
         </#if>
    </#if>
</div>
</#macro>

<#macro h3 title="-" anchor="-" spanclass="span6">
    <div class="${spanclass}">
      <#if !(title=="-")><h3><#if !(anchor=="-")><a class="anchor" name="${anchor}"></a></#if>${title}</h3></#if>
      <#nested>
    </div>
</#macro>
