var cQualifiers;
var cEditions;
var cTypesDistribution;
//     var myCombo = (new Dropdown("idCombo")).initialize();
//myCombo.addSelectionListener(function(value) {
//    (...)
//});
function loadQualifiers() {
	cQualifiers.empty();
	for (var i = 0; q = qualifiers[i]; i++) {
    	cQualifiers.append('<li id="' + q.id + '">' + q.name + '</li>');
    };
    cQualifiers.children().click(function() {
    	setQualifier(this.id);
    	refresh();
    });
};
function loadEditions() {
	cEditions.empty();
     for (var i = 0; edition = editions[i]; i++) {
    	 if ("all" != edition.id) {
        	 cEditions.append('<li id="' + edition.id + '">' + edition.longname + '</li>');    		 
    	 }
     };
     cEditions.children().click(function() {
          setEdition(this.id);
          refresh();
     });
};
function loadDistributions() {
     distribution = null;
     cTypesDistribution.empty();
     cTypesDistribution.append('<li id="zip">ZIP file</li>');
     cTypesDistribution.append('<li id="exe">Windows installer</li>');
     cTypesDistribution.children().click(function() {
          setDistribution(this.id);
          refresh();
     });
};

function setDownloadButton() {
	$('#download').empty();
	if(distribution && "file" == distribution.type){
		// Update download button
		var urlChangesLog = "/learn/";
		if("unstable" == qualifier.id){
			urlChangesLog += "snapshot";
		} else {
			urlChangesLog += version.minorVersion;
		}
		urlChangesLog += "/jse/changes";
		
		$('#download').append('<p><button class="btn btn-large btn-success" type="button">Download ' + version.fullVersionCompact + '</button></p>');
		$('#download').append('<p>File size: ' + distribution.fileSize  + '</p>');
		$('#download').append('<p>Date: ' + version.published  + '</p>');
		$('#download').append('<p><a href="' + urlChangesLog + '">What\'s new</a></p>');
		$('#download button').click(function () {
			document.location.href = "/download/" + version.minorVersion + "/" + distribution.fileName;
		});
	}
}

function refresh(){
	// Look for the current distribution.
	distribution = getDistribution(distributionId);
	$("#" + cQualifiers.attr('id') + '-bt').empty();
	$("#" + cQualifiers.attr('id') + '-bt').append("<strong>" + qualifier.name + "</strong>");
	$("#" + cEditions.attr('id') + '-bt').empty();
	$("#" + cEditions.attr('id') + '-bt').append("<strong>" + edition.shortname + "</strong>");
	$("#" + cTypesDistribution.attr('id') + '-bt').empty();
	if("zip" == distributionId){
		$("#" + cTypesDistribution.attr('id') + '-bt').append("<strong>ZIP file</strong>");
	} else if("exe" == distributionId){
		$("#" + cTypesDistribution.attr('id') + '-bt').append("<strong>Windows installer</strong>");
	} else if("maven" == distributionId){
		$("#" + cTypesDistribution.attr('id') + '-bt').append("<strong>Maven</strong>");
	} else if("p2" == distributionId){
		$("#" + cTypesDistribution.attr('id') + '-bt').append("<strong>OSGi</strong>");
	}
	setDownloadButton();
}

function init(idQualifiers, idEditions, idTypesDistribution) {
	cQualifiers = $("#" + idQualifiers);
	cEditions = $("#" + idEditions);
	cTypesDistribution = $("#" + idTypesDistribution);
	loadQualifiers();
	loadEditions();
	loadDistributions();

	
	qualifier = getQualifier($.cookie('qualifier'));
	if(!qualifier){
		$.cookie('qualifier', getDefaultQualifier(), {path: '/' });
		qualifier = getQualifier($.cookie('qualifier'));
	}
	version = getVersion(qualifier.version);
	
	edition = getEdition($.cookie('edition'));
	if(!edition) {
		$.cookie('edition', getDefaultEdition(version), {path: '/' });
		edition = getEdition($.cookie('edition'));
	}

	distribution = getDistribution($.cookie('distribution'));
	if(!distribution){
		$.cookie('distribution', getDefaultDistribution(version, edition), {path: '/' });
	}
	distributionId = $.cookie('distribution');

	refresh();
}
