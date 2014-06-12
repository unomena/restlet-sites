var cQualifiers;
var cEditions;
var cTypesDistribution;
var handleFragment = false;
var redirectDownload = false;

function loadQualifiers() {
	cQualifiers.empty();
	for ( var i = 0; q = qualifiers[i]; i++) {
		cQualifiers.append('<li id="' + q.id + '">' + q.name + '</li>');
	};
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
	cTypesDistribution.append('<li id="maven">Maven</li>');
	cTypesDistribution.append('<li id="p2">Eclipse</li>');
	cTypesDistribution.children().click(function() {
		setDistribution(this.id);
		refresh();
	});
};

function loadMavenSnippet() {
	$('#maven-snippet').empty();
	$('#maven-snippet').append('&lt;repositories&gt;\n');
	$('#maven-snippet').append('    &lt;repository&gt;\n');
	$('#maven-snippet').append('        &lt;id&gt;maven-restlet&lt;/id&gt;\n');
	$('#maven-snippet').append('        &lt;name&gt;Restlet repository&lt;/name&gt;\n');
	$('#maven-snippet').append('        &lt;url&gt;http://maven.restlet.com&lt;/url&gt;\n');
	$('#maven-snippet').append('    &lt;/repository&gt;\n');
	$('#maven-snippet').append('&lt;/repositories&gt;\n');
	$('#maven-snippet').append('&lt;properties&gt;\n');
	$('#maven-snippet').append('    &lt;restlet-version&gt;' + version.mavenVersion + '&lt;/restlet-version&gt;\n');
	$('#maven-snippet').append('&lt;/properties&gt;\n');
	$('#maven-snippet').append('&lt;dependencies&gt;\n');
	$('#maven-snippet').append('    &lt;dependency&gt;\n');
	$('#maven-snippet').append('        &lt;groupId&gt;org.restlet.jse&lt;/groupId&gt;\n');
	$('#maven-snippet').append('        &lt;artifactId&gt;org.restlet&lt;/artifactId&gt;\n');
	$('#maven-snippet').append('        &lt;version&gt;&#36;{restlet-version}&lt;/version&gt;\n');
	$('#maven-snippet').append('    &lt;/dependency&gt;\n');
	$('#maven-snippet').append('    &lt;dependency&gt;\n');
	$('#maven-snippet').append('        &lt;groupId&gt;org.restlet.jse&lt;/groupId&gt;\n');
	$('#maven-snippet').append('        &lt;artifactId&gt;org.restlet.ext.jackson&lt;/artifactId&gt;\n');
	$('#maven-snippet').append('        &lt;version&gt;&#36;{restlet-version}&lt;/version&gt;\n');
	$('#maven-snippet').append('    &lt;/dependency&gt;\n');
	$('#maven-snippet').append('&lt;/dependencies&gt;\n');	
}

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
		urlChangesLog += "/changes";
		
		// Update the cookie branch with the branch value
		$.cookie('branch', version.minorVersion, {path : '/'});

		$('#download').append('<p><button id="rfDownloadButton" class="btn btn-large btn-success" type="button"><em class="download-icon"></em>Download ' + version.fullVersionCompact + '</button></p>');
		
		$('#download').append('<p><a href="' + urlChangesLog + '">What\'s new?</a></p>');
		$('#download').append('<p>' + version.published + ' release</p>');
		
		$('#campaignButton').click(
				function(event) {
					if (checkEmail("campaignEmail", "campaignButton")) {
						mixpanel.track("Shared email", {
							"email": $("#campaignEmail").val(),
							"Email field location":"Kin Lane Guide"
						});
						mixpanel.alias($("#campaignEmail").val(), mixpanel.get_distinct_id());
						mixpanel.people.set({"$email": $("#campaignEmail").val()});
						mixpanel.track("Downloaded Kin Lane Guide");
						
						// close popup
						$("#campaignEmail").val("");
						$("#deployModal").hide();
						
						// Set a one year cookie dedicated to this campaign. 
						$.cookie('kin-lane-white-paper-v2', 'true', {
							expires: 365
						});
						
						// launch pdf download in a new tab
						window.open('http://restlet.files.wordpress.com/2013/12/gigaom-research-a-field-guide-to-web-apis.pdf?utm_source=restlet-site&utm_medium=popup&utm_campaign=Kin%20Lane%20Guide', "_blank");
					}
				}
		);

		if (distribution.fileType == "maven") {
			$('#rfDownloadButton').click(
					function() {
						loadMavenSnippet();
						
						$('#eclipse_infos').css('display','none');
						$('#maven_infos').css('display','none');
						$('#firststeps_infos').css('display','none');
						
						var hrefCallback = function() {
							$('#newsletter').css('display','block');
							$('#maven_infos').css('display','block');
							if ("true" != $.cookie("kin-lane-white-paper-v2")) {
								// open campaign popup
								$("#deployModal").show();								
							}
						}
						
						// in case Mixpanel servers don't get back to us in time
						// we use the same timeout value as the one defined in Mixpanel config
						window.setTimeout(hrefCallback, mixpanel.get_config('track_links_timeout'));
						
						// fire the tracking event, if the event is done before the
						// timeout, the previous hrefCallback call is cancelled by
						// the call to document.location.href
						mixpanel.track("Downloaded Restlet Framework", {
							"Version":version.id,
							"Release":qualifier.name,
							"Edition":edition.middlename,
							"Distribution":distribution.fileType
						}, hrefCallback);
					});
		} else if (distribution.fileType == "p2") {
			$('#rfDownloadButton').click(
					function() {
						loadMavenSnippet();
						$('#eclipse_infos').css('display','none');
						$('#maven_infos').css('display','none');
						$('#firststeps_infos').css('display','none');
						
						var hrefCallback = function() {
							$('#newsletter').css('display','block');
							$('#eclipse_infos').css('display','block');
							if ("true" != $.cookie("kin-lane-white-paper-v2")) {
								// open campaign popup
								$("#deployModal").show();								
							}
						}
						
						// in case Mixpanel servers don't get back to us in time
						// we use the same timeout value as the one defined in Mixpanel config
						window.setTimeout(hrefCallback, mixpanel.get_config('track_links_timeout'));
						
						// fire the tracking event, if the event is done before the
						// timeout, the previous hrefCallback call is cancelled by
						// the call to document.location.href
						mixpanel.track("Downloaded Restlet Framework", {
							"Version":version.id,
							"Release":qualifier.name,
							"Edition":edition.middlename,
							"Distribution":"eclipse"
						}, hrefCallback);
					});			
		} else {
			$('#rfDownloadButton').click(
					function(event) {
						loadMavenSnippet();
						$('#eclipse_infos').css('display','none');
						$('#maven_infos').css('display','none');
						$('#firststeps_infos').css('display','block');
						$('#newsletter').css('display','block');

						var hrefCallback = function(event) {
							// open campaign popup
							if ("true" != $.cookie("kin-lane-white-paper-v2")) {
								// open campaign popup
								$("#deployModal").show();								
							}
							// download selected restlet framework file
							document.location.href = "/download/" + version.minorVersion + "/" + distribution.fileName;
						}
						
						// in case Mixpanel servers don't get back to us in time
						// we use the same timeout value as the one defined in Mixpanel config
						var trackTimeout = window.setTimeout(hrefCallback, mixpanel.get_config('track_links_timeout'));
						
						// fire the tracking event, if the event is done before the
						// timeout, we call a window.clearTimeout
						mixpanel.track("Downloaded Restlet Framework", {
							"Version":version.id,
							"Release":qualifier.name,
							"Edition":edition.middlename,
							"Distribution":distribution.fileType
						}, function() {
							window.clearTimeout(trackTimeout);
							hrefCallback();
						});
					});
		}
	}
}

function refresh() {
	// Look for the current distribution.
	distribution = getDistribution(distributionId);
	$("#" + cQualifiers.attr('id') + '-bt').empty();
	$("#" + cQualifiers.attr('id') + '-bt').append("<strong>" + qualifier.name + "</strong>");
	$("#" + cEditions.attr('id') + '-bt').empty();
	$("#" + cEditions.attr('id') + '-bt').append("<strong>" + edition.longname + "</strong>");
	$("#" + cTypesDistribution.attr('id') + '-bt').empty();
	
	if ("zip" == distributionId) {
		$("#" + cTypesDistribution.attr('id') + '-bt').append("<strong>ZIP file</strong>");
	} else if ("exe" == distributionId) {
		$("#" + cTypesDistribution.attr('id') + '-bt').append("<strong>Windows installer</strong>");
	} else if ("maven" == distributionId) {
		$("#" + cTypesDistribution.attr('id') + '-bt').append("<strong>Maven</strong>");
	} else if ("p2" == distributionId) {
		$("#" + cTypesDistribution.attr('id') + '-bt').append("<strong>Eclipse</strong>");
	}

	if (handleFragment) {
		window.location.hash = "#release=" + qualifier.id +"&edition=" + edition.id + "&distribution=" + distributionId;
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
 * @param rd
 * 		True indicates if the click on the download button redirects to the download page.
 */
function init(sq, se, std, hf, rd) {
	cQualifiers = sq;
	cEditions = se;
	cTypesDistribution = std;
	if (hf) {
		handleFragment = true;
	} else {
		handleFragment = false;
	}
	if (rd) {
		redirectDownload = true;
	} else {
		redirectDownload = false;
	}

	loadQualifiers();
	loadEditions();
	loadDistributions();

	var hash = window.location.hash;
	var itemId = getParameterByName(hash, "release", $.cookie('release'));

	qualifier = getQualifier(itemId);
	if (!qualifier) {
		qualifier = getQualifier(getDefaultQualifier($.cookie('branch')));
	}
	$.cookie('release', qualifier.id, {path : '/'});

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
