var cBranches;
var cEditions;
var cTypesDistribution;
var tabDir;
//	var myCombo = (new Dropdown("idCombo")).initialize();
//myCombo.addSelectionListener(function(value) {
//    (...)
//});

function loadBranches() {
	cBranches.empty();
     for (var i = 0; b = branches[i]; i++) {
    	 cBranches.append('<li id="' + b + '">' + b + '</li>');
     };
     cBranches.children().click(function() {
          setBranch(this.id);
          refresh();
     });
};
function loadEditions() {
	cEditions.empty();
     for (var i = 0; edition = editions[i]; i++) {
    	 cEditions.append('<li id="' + edition.id + '">' + edition.longname + '</li>');
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
function listDistributions() {
	tabDir.empty();
	for (var i = 0; d = distributions[i]; i++) {
		if((distributionId == d.fileType) && (branch == d.version.substring(0, 3)) && (!edition || (d.edition== edition.id))){
			displayDistribution(tabDir, d);					
		}
	};
};
function displayDistribution(tab, distribution) {
	var v = getVersion(distribution.version);
	if(v){
		var str = '<tr><td><a href="/downloads/' + v.minorVersion + '/' + distribution.fileName + '" class="file">';
//			str += '<img alt="File:"';
//			if("exe" == distribution.fileType){
//				str += ' src="/images/executable.png">';
//			} else {
//				str += ' src="/images/zipfile.png">';
//			}
		str += distribution.fileName;
		str += '</a></td>';
		str += '<td>' + distribution.fileSize + '</td>';
		if(v){
			str += '<td>' + v.published + '</td>';
		} else {
			str += '<td>-</td>';
		}
		str += '</tr>';
	}
	tab.append(str);
}

function refresh() {
	// récupération de la distribution courante.
	distribution = getDistribution(distributionId);
	$("#" + cBranches.attr('id') + '-bt').empty();
	$("#" + cBranches.attr('id') + '-bt').append("<strong>" + branch + "</strong>");
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
	//setDownloadButton();
	listDistributions();
}

function setDownloadButton(){
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

function init(idBranches, idEditions, idTypesDistribution, idDir) {
	cBranches = $("#" + idBranches);
	cEditions = $("#" + idEditions);
	cTypesDistribution = $("#" + idTypesDistribution);
	tabDir = $("#" + idDir);
	loadBranches();
	loadEditions();
	loadDistributions();

	branch = $.cookie('branch');
	if (!branch) {
		$.cookie('branch', getDefaultBranch($.cookie('qualifier')), {path: '/' });
		branch = $.cookie('branch');
	}
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
