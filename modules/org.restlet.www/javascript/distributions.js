var cQualifiers;
var cEditions;
var cTypesDistribution;
var handleFragment = false;
// var myCombo = (new Dropdown("idCombo")).initialize();
// myCombo.addSelectionListener(function(value) {
// (...)
// });

function getParameterByName(query, name, defaultValue) {
	var result = defaultValue;
	if (query) {
		name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
		var regexS = "[#\\?&]?" + name + "=([^&#]*)";
		var regex = new RegExp(regexS);
		var results = regex.exec(query);
		if (results != null) {
			result = decodeURIComponent(results[1].replace(/\+/g, " "));
		}
	}
	return result;
}

function loadQualifiers() {
	cQualifiers.empty();
	for ( var i = 0; q = qualifiers[i]; i++) {
		cQualifiers.append('<li id="' + q.id + '">' + q.name + '</li>');
	}
	;
	cQualifiers.children().click(function() {
		setQualifier(this.id);
		refresh();
	});
};
function loadEditions() {
	cEditions.empty();
	for ( var i = 0; edition = editions[i]; i++) {
		if ("all" != edition.id) {
			cEditions.append('<li id="' + edition.id + '">' + edition.longname
					+ '</li>');
		}
	}
	;
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
	if (distribution && "file" == distribution.type) {
		// Update download button
		var urlChangesLog = "/learn/";
		if ("unstable" == qualifier.id) {
			urlChangesLog += "snapshot";
		} else {
			urlChangesLog += version.minorVersion;
		}
		urlChangesLog += "/jse/changes";

		$('#download').append(
				'<p><button class="btn btn-large btn-success" type="button">Download '
						+ version.fullVersionCompact + '</button></p>');
		$('#download')
				.append('<p>File size: ' + distribution.fileSize + '</p>');
		$('#download').append('<p>Date: ' + version.published + '</p>');
		$('#download').append(
				'<p><a href="' + urlChangesLog + '">What\'s new</a></p>');
		$('#download button').click(
				function() {
					document.location.href = "/download/"
							+ version.minorVersion + "/"
							+ distribution.fileName;
				});
	}
}

function refresh() {
	// Look for the current distribution.
	distribution = getDistribution(distributionId);
	$("#" + cQualifiers.attr('id') + '-bt').empty();
	$("#" + cQualifiers.attr('id') + '-bt').append(
			"<strong>" + qualifier.name + "</strong>");
	$("#" + cEditions.attr('id') + '-bt').empty();
	$("#" + cEditions.attr('id') + '-bt').append(
			"<strong>" + edition.shortname + "</strong>");
	$("#" + cTypesDistribution.attr('id') + '-bt').empty();
	
	if ("zip" == distributionId) {
		$("#" + cTypesDistribution.attr('id') + '-bt').append(
				"<strong>ZIP file</strong>");
	} else if ("exe" == distributionId) {
		$("#" + cTypesDistribution.attr('id') + '-bt').append(
				"<strong>Windows installer</strong>");
	} else if ("maven" == distributionId) {
		$("#" + cTypesDistribution.attr('id') + '-bt').append(
				"<strong>Maven</strong>");
	} else if ("p2" == distributionId) {
		$("#" + cTypesDistribution.attr('id') + '-bt').append(
				"<strong>OSGi</strong>");
	}

	if (handleFragment) {
		window.location.hash = "#qualifier=" + qualifier.id +"&edition=" + edition.id + "&distribution=" + distributionId;
	}

	setDownloadButton();
}

/**
 * Initializes the data model, cookies, and selectors.
 * @param sq
 * 		The selector of qualifiers.
 * @param se
 * 		The selector of editions.
 * @param std
 * 		The selector of type of distributions.
 * @param hf
 * 		True indicates that the uri will reflect the selectors's value in the uri fragment.
 */
function init(sq, se, std, hf) {
	cQualifiers = sq;
	cEditions = se;
	cTypesDistribution = std;
	if (hf) {
		handleFragment = true;
	} else {
		handleFragment = false;
	}

	loadQualifiers();
	loadEditions();
	loadDistributions();

	var hash = window.location.hash;
	var itemId = getParameterByName(hash, "qualifier", $.cookie('qualifier'));

	qualifier = getQualifier(itemId);
	if (!qualifier) {
		qualifier = getQualifier(getDefaultQualifier($.cookie('branch')));
	}
	$.cookie('qualifier', qualifier.id, {path : '/'});

	version = getVersion(qualifier.version);

	itemId = getParameterByName(hash, "edition", $.cookie('edition'));
	edition = getEdition(itemId);
	if (!edition) {
		edition = getEdition(getDefaultEdition(version));
	}
	$.cookie('edition', edition.id, {path : '/'});
	
	itemId = getParameterByName(hash, "distribution", $.cookie('distribution'));
	distribution = getDistribution(itemId);
	if (!distribution) {
		distribution = getDistribution(getDefaultDistribution(version, edition));
	}
	if ("file" == distribution.type) {
		distributionId = distribution.fileType;
	} else {
		distributionId = distribution.type;
	}
	$.cookie('distribution', distributionId, {path : '/'});
	
	refresh();
}
