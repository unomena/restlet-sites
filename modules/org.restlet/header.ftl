<#global javascript_files      = [] />
<#global stylesheet_files      = [] />
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

<#global downloadSection>
<div class="downloadsection">
    <div class='span3 left section'>
        <div>
            <img src="/images/download.png" />
            <h2>Download</h2>
        </div>
    </div>
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
</div>
</#global>
<#global archivesSection>
<div class="downloadsection">
    <div class='span3 left section'>
        <div>
            <img src="/images/download.png" />
            <h2>Download</h2>
        </div>
    </div>
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
                        <button id="cRelease-bt" class="btn no-radius" data-toggle="dropdown"><strong>2.1.1</strong></button>
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
                        <button id="cEdition-bt" class="btn no-radius" data-toggle="dropdown"><strong>OSGi</strong></button>
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