	var distributions = ${restletDistributions};
	var editions = ${restletEditions};
	var qualifiers = ${restletQualifiers};
	var versions = ${restletVersions};

	function getQualifier(id) {
		for (var i = 0; item = qualifiers[i]; i++) {
			if(item.id == id){
				return item;
			}
		};
		return null;
	};
	function getVersion(id) {
		for (var i = 0; item = versions[i]; i++) {
			if(item.id == id){
				return item;
			}
		};
		return null;
	};
	function getEdition(id) {
		for (var i = 0; item = editions[i]; i++) {
			if(item.id == id){
				return item;
			}
		};
		return null;
	};
	function getDistribution(id) {
		for (var i = 0; item = distributions[i]; i++) {
			if ((item.version == version.id) && (item.edition == edition.id) && (item.fileType == id)) {
				return item;
			}
		};
		return null;
	};
	function loadQualifiers(list) {
		list.empty();
		for (var i = 0; q = qualifiers[i]; i++) {
			list.append('<li id="' + q.id + '">' + q.name + '</li>');
		};
		list.children().click(function() {
			setQualifier(this.id);
		});
	};
	function loadEditions(list) {
		list.empty();
		for (var i = 0; edition = editions[i]; i++) {
			list.append('<li id="' + edition.id + '">' + edition.longname + '</li>');
		};
		list.children().click(function() {
			setEdition(this.id);
		});
	};
	function loadDistributions(list) {
		distribution = null;
		list.empty();
		list.append('<li id="zip">ZIP file</li>');
		list.append('<li id="exe">Windows installer</li>');
		list.children().click(function() {
			setDistribution(this.id);
		});
	};
	function listDistributions(minorVersion, tab) {
		tab.empty();
		for (var i = 0; distribution = distributions[i]; i++) {
			var str = "";
			if(("file" == distribution.type) && (minorVersion == distribution.version.substring(0, 3))){
				var v = getVersion(distribution.version);
				if(v){
					str += '<tr><td><a href="/downloads/' + v.minorVersion + '/' + distribution.fileName + '" class="file">';
					str += '<img alt="File:"';
					if("exe" == distribution.fileType){
						str += ' src="/images/executable.png">';
					} else {
						str += ' src="/images/zipfile.png">';
					}
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
		};

		var v = getVersion(version);
		if(v){
		}
	};

	function setQualifier(id) {
		$.cookie('qualifier', id, {path: '/' });
		qualifier = getQualifier(id);
		version  = getVersion(qualifier.version);
		$.cookie('version', version.id, {path: '/' });

		$('#branch-bt').empty();
		$('#branch-bt').append("<strong>" + qualifier.name + "</strong>");
		setDistribution();
	}
	function setEdition(id) {
		$.cookie('edition', id, {path: '/' });
		edition = getEdition(id);
		$('#edition-bt').empty();
		$('#edition-bt').append("<strong>" + edition.shortname + "</strong>");
		setDistribution();
	}
	function setDistribution(id) {
		if(id){
			distribution = getDistribution(id);
		} else if(distribution){
			distribution = getDistribution(distribution.fileType);
		}
		if(qualifier && edition && distribution){
			$.cookie('distribution', distribution.fileType, {path: '/' });
			$('#distribution-bt').empty();
			$('#download').empty();
			if("file" == distribution.type){
				if("zip" == distribution.fileType){
					$('#distribution-bt').append("<strong>ZIP file</strong>");
				} else if("exe" == distribution.fileType){
					$('#distribution-bt').append("<strong>Windows installer</strong>");
				} else {
					$('#distribution-bt').append("<strong>" + distribution.fileType + "</strong>");
				}
				
				var urlChangesLog = "/documentation/";
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
					document.location.href = "/downloads/" + version.minorVersion + "/" + distribution.fileName;
				});
			}
		}
	};
	
	function initDownload(){
		loadQualifiers($("#branch"));
		loadEditions($("#edition"));
		loadDistributions($("#distribution"));
		var str = $.cookie('qualifier');
		if(str){
			setQualifier(str);			
		} else {
			setQualifier("stable");
		}
		str = $.cookie('edition');
		if(str){
			setEdition(str);
		} else {
			setEdition("jse");
		}
		str = $.cookie('distribution');
		if(str){
			setDistribution(str);
		} else {
			setDistribution("zip");
		}
	}

	function initArchive(){
		loadQualifiers($("#branch"));
		loadEditions($("#edition"));
		loadDistributions($("#distribution"));
		var str = $.cookie('qualifier');
		if(!str){
			$.cookie('qualifier', "stable", {path: '/' });
		}
		qualifier = getQualifier($.cookie('qualifier'));
		str = $.cookie('version');
		if(!str){
			$.cookie('version', qualifier.version, {path: '/' });
		}
		version  = getVersion($.cookie('version'));
	}

//	var myCombo = (new Dropdown("idCombo")).initialize();
//myCombo.addSelectionListener(function(value) {
//    (...)
//});
	var qualifier = null;
	var version = null;
	var edition = null;
	var distribution = null;
