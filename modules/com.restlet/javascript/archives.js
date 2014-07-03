var cBranches;
var cReleases;
var cEditions;
var cTypesDistribution;
var tabDir;
var handleFragment = false;
var redirectDownload = false;

function loadBranches() {
	cBranches.empty();
	for ( var i = 0; b = branches[i]; i++) {
		cBranches.append('<li id="' + b + '">' + b + '</li>');
	}
	;
	cBranches.children().click(function() {
		refreshBranch(this.id);
	});
};

function listDistributions() {
	tabDir.empty();
	for ( var i = 0; d = distributions[i]; i++) {
		if ((distributionId == d.fileType)
				&& (branch == d.version.substring(0, 3))
				&& (version.id == d.version)
				&& (!edition || ("all" == edition.id) || (d.edition == edition.id))) {
			displayDistribution(tabDir, d);
		}
	}
	;
};
function displayDistribution(tab, distribution) {
	var v = getVersion(distribution.version);
	if (v) {
		var str = '<tr><td><a href="/download/current?release=' + v.fullVersionCompact + '&edition=' + distribution.edition  + '&distribution=' + distribution.fileType + '" class="file">';
		// str += '<img alt="File:"';
		// if("exe" == distribution.fileType){
		// str += ' src="/images/executable.png">';
		// } else {
		// str += ' src="/images/zipfile.png">';
		// }
		str += distribution.fileName;
		str += '</a></td>';
		str += '<td>' + distribution.fileSize + '</td>';
		if (v) {
			str += '<td>' + v.published + '</td>';
		} else {
			str += '<td>-</td>';
		}
		str += '</tr>';
	}
	tab.append(str);
}

function refreshBranch(branchId) {
	setBranch(branchId);

	var versionId = null;
	var first = true;
	cReleases.empty();
	for ( var i = 0; v = versions[i]; i++) {
		if (v.minorVersion == branch) {
			cReleases.append('<li id="' + v.id + '">' + v.fullVersionCompact
					+ '</li>');
			if (((version != null) && v.id == version.id) || first) {
				versionId = v.id;
				first = false;
			}
		}
	}
	;
	cReleases.children().click(function() {
		refreshRelease(this.id);
	});
	refreshRelease(versionId);
}

function refreshRelease(releaseId) {
	setVersion(releaseId);

	var editionId = null;
	var first = true;
	cEditions.empty();
	for ( var j = 0; e0 = version.editions[j]; j++) {
		for ( var i = 0; e = editions[i]; i++) {
			if (e.id == e0.id) {
				cEditions.append('<li id="' + e.id + '">' + e.longname
						+ '</li>');
				if ((edition != null && e.id == edition.id) || first) {
					editionId = e.id;
					first = false;
				}
			}
		}
		;
	}
	cEditions.children().click(function() {
		refreshEdition(this.id);
	});
	refreshEdition(editionId);
}

function refreshEdition(editionId) {
	setEdition(editionId);

	cTypesDistribution.empty();
	var distroId = null;
	var first = true;
	for ( var j = 0; d = distributions[j]; j++) {
		if (((d.version == version.id) && (d.edition == edition.id))) {
			if ("zip" == d.fileType) {
				cTypesDistribution.append('<li id="zip">ZIP file</li>');
				if (d.fileType == distributionId || first) {
					distroId = d.fileType;
				}
				first = false;
			} else if ("exe" == d.fileType || first) {
				cTypesDistribution
						.append('<li id="exe">Windows installer</li>');
				if (d.fileType == distributionId || first) {
					distroId = d.fileType;					
				}
				first = false;
			} else if ("maven" == d.fileType || first) {
				cTypesDistribution
						.append('<li id="maven">Maven</li>');
				if (d.fileType == distributionId || first) {
					distroId = d.fileType;					
				}
				first = false;
			} else if ("p2" == d.fileType || first) {
				cTypesDistribution
						.append('<li id="p2">Eclipse</li>');
				if (d.fileType == distributionId || first) {
					distroId = d.fileType;					
				}
				first = false;
			}

		}
	}
	cTypesDistribution.children().click(function() {
		refresh(this.id);
	});

	refresh(distroId);
};

function refresh(distroId) {
	setDistribution(distroId);
	// récupération de la distribution courante.
	distribution = getDistribution(distroId);
	if (!distribution) {
		distribution = getDistribution(getDefaultDistribution(version, edition));
		distributionId = distribution.fileType;
	}

	$("#" + cBranches.attr('id') + '-bt').empty();
	$("#" + cBranches.attr('id') + '-bt').append("<strong>" + branch + "</strong>");
	$("#" + cReleases.attr('id') + '-bt').empty();
	$("#" + cReleases.attr('id') + '-bt').append("<strong>" + version.fullVersionCompact + "</strong>");
	$("#" + cEditions.attr('id') + '-bt').empty();
	$("#" + cEditions.attr('id') + '-bt').append("<strong>" + edition.longname + "</strong>");
	$("#" + cTypesDistribution.attr('id') + '-bt').empty();
	window.location.hash = "#branch=" + branch + "&release=" + version.id + "&edition=" + edition.id + "&distribution=" + distributionId;
	if ("zip" == distributionId) {
		$("#" + cTypesDistribution.attr('id') + '-bt').append("<strong>ZIP file</strong>");
	} else if ("exe" == distributionId) {
		$("#" + cTypesDistribution.attr('id') + '-bt').append("<strong>Windows installer</strong>");
	} else if ("maven" == distributionId) {
		$("#" + cTypesDistribution.attr('id') + '-bt').append("<strong>Maven</strong>");
	} else if ("p2" == distributionId) {
		$("#" + cTypesDistribution.attr('id') + '-bt').append("<strong>Eclipse</strong>");
	}
	setDownloadButton();
	// listDistributions();
}

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
		if (qualifier != null && "unstable" == qualifier.id) {
			urlChangesLog += "snapshot";
		} else {
			urlChangesLog += version.minorVersion;
		}
		urlChangesLog += "/changes";

		$('#download').append(
				'<p><button id="rfDownloadButton" class="btn btn-large btn-success" type="button"><em class="download-icon"></em>Download '
						+ version.fullVersionCompact + '</button></p>');

		$('#download').append('<p><a href="' + urlChangesLog + '">What\'s new?</a></p>');
		$('#download').append('<p>' + version.published + ' release</p>');
		
		if ($.cookie('branch') === "1.2") {
			$.cookie('branch', '2.0', {path : '/'});
		}

		$('#campaignButton').click(
				function(event) {
					if (checkEmail("campaignEmail", "campaignButton") 
							&& checkFieldEmpty("campaignName", "campaignButton") 
							&& checkFieldEmpty("campaignAddress", "campaignButton")) {
						mixpanel.track("Shared email", {
							"email": $("#campaignEmail").val(),
							"name": $("#campaignName").val(),
							"mailing address": $("#campaignAddress").val(),
							"Email field location":"RF and AS Stickers"
						});
						mixpanel.alias($("#campaignEmail").val(), mixpanel.get_distinct_id());
						mixpanel.people.set({"$email": $("#campaignEmail").val()});
						mixpanel.track("Ordered RF and AS stickers");
						
						// close popup
						$("#campaignName").val("");
						$("#campaignEmail").val("");
						$("#campaignAddress").val("");
						$("#deployModal").hide();
						
						// Set a one year cookie dedicated to this campaign. 
						$.cookie('stickers-campaign-v2', 'true', {
							expires: 365
						});
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
							if ("true" != $.cookie("stickers-campaign-v2")) {
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
							"Release":version.fullVersionCompact,
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
							if ("true" != $.cookie("stickers-campaign-v2")) {
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
							"Release":version.fullVersionCompact,
							"Edition":edition.middlename,
							"Distribution":"eclipse"
						}, hrefCallback);
					});			
		} else if (redirectDownload) {
			$('#rfDownloadButton').click(
					function() {
						loadMavenSnippet();
						$('#eclipse_infos').css('display','none');
						$('#maven_infos').css('display','none');
						$('#firststeps_infos').css('display','block');
						$('#newsletter').css('display','block');

						var hrefCallback = function(event) {
							// open campaign popup
							if ("true" != $.cookie("stickers-campaign-v2")) {
								// open campaign popup
								$("#deployModal").show();								
							}

							// download selected restlet framework file, we delay the download from 500ms
							// in order to all background images are downloaded (safari/chrome bug)
							window.setTimeout(function () {
									document.location.href = "/download/past?distribution=" + distribution.fileType + "&release=" + version.id + "&edition=" + edition.id;
								}, 500);
						}
						
						// in case Mixpanel servers don't get back to us in time
						// we use the same timeout value as the one defined in Mixpanel config
						var trackTimeout = window.setTimeout(hrefCallback, mixpanel.get_config('track_links_timeout'));
						
						// fire the tracking event, if the event is done before the
						// timeout, we call a window.clearTimeout
						mixpanel.track("Downloaded Restlet Framework", {
							"Version":version.id,
							"Release":version.fullVersionCompact,
							"Edition":edition.middlename,
							"Distribution":distribution.fileType
						}, function() {
							window.clearTimeout(trackTimeout);
							hrefCallback();
						});
					});			
		} else {
			$('#rfDownloadButton').click(
					function() {
						loadMavenSnippet();
						$('#eclipse_infos').css('display','none');
						$('#maven_infos').css('display','none');
						$('#firststeps_infos').css('display','block');
						$('#newsletter').css('display','block');

						var hrefCallback = function(event) {
							// open campaign popup
							if ("true" != $.cookie("stickers-campaign-v2")) {
								// open campaign popup
								$("#deployModal").show();								
							}
							// download selected restlet framework file, we delay the download from 500ms
							// in order to all background images are downloaded (safari/chrome bug)
							window.setTimeout(function () {
									document.location.href = "/download/" + version.minorVersion + "/" + distribution.fileName;
								}, 500);
						}
						
						// in case Mixpanel servers don't get back to us in time
						// we use the same timeout value as the one defined in Mixpanel config
						var trackTimeout = window.setTimeout(hrefCallback, mixpanel.get_config('track_links_timeout'));
						
						// fire the tracking event, if the event is done before the
						// timeout, we call a window.clearTimeout
						mixpanel.track("Downloaded Restlet Framework", {
							"Version":version.id,
							"Release":version.fullVersionCompact,
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

/**
 * Initializes the data model, cookies, and selectors.
 * 
 * @param sb
 *            The selector of branches.
 * @param sr
 *            The selector of releases.
 * @param se
 *            The selector of editions.
 * @param std
 *            The selector of type of distributions.
 * @param dir
 *            The div where to display the listing.
 */
function init(sb, sr, se, std, dir, hf, rd) {
	cBranches = sb;
	cReleases = sr;
	cEditions = se;
	cTypesDistribution = std;
	tabDir = dir;
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

	var hash = window.location.hash;
	var itemId = getParameterByName(hash, "branch", $.cookie('branch'));

	branch = itemId;
	if (!branch) {
		branch = getDefaultBranch($.cookie('release'));
	}
	$.cookie('branch', branch, {
		path : '/'
	});

	itemId = getParameterByName(hash, "release", $.cookie('release'));
	qualifier = getQualifier(itemId);
	if (!qualifier) {
		$.cookie('release', itemId, {
			path : '/'
		});
		version = getVersion(itemId);
	} else {
		$.cookie('release', qualifier.id, {
			path : '/'
		});
		version = getVersion(qualifier.version);
	}

	itemId = getParameterByName(hash, "edition", $.cookie('edition'));
	edition = getEdition(itemId);
	if (!edition) {
		edition = getEdition(getDefaultEdition(version));
	}
	$.cookie('edition', edition.id, {
		path : '/'
	});

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
	$.cookie('distribution', distributionId, {
		path : '/'
	});

	loadBranches();
	refreshBranch(branch);
}
