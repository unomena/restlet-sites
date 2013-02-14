// Update data model.
var distributions = ${restletDistributions};
var editions = ${restletEditions};
var qualifiers = ${restletQualifiers};
var versions = ${restletVersions};
var qualifier = null;
var version = null;
var branch = null;
var edition = null;
var distribution = null;
var distributionId = null;

var branches = new Array();
for (var i = 0; v = versions[i]; i++) {
	var b = null;
	for (var j = 0; (!b) && (branches[j]); j++) {
		if (v.minorVersion == branches[j]) {
			b = branches[j];
		}
	};
	if (!b) {
		branches.push(v.minorVersion);
	}
};	
function getQualifier(id) {
	for (var i = 0; item = qualifiers[i]; i++) {
		if(item.id == id){
			return item;
		}
	};
	return null;
};
function getQualifierByVersion(id) {
	for (var i = 0; item = qualifiers[i]; i++) {
		if(item.version == id){
			return item;
		}
	};
	return null;
};
function getQualifierByBranch(id) {
	for (var i = 0; item = qualifiers[i]; i++) {
		if(item.version.indexOf(id) == 0){
			return item;
		}
	};
	return null;
};
function getBranch(id) {
	for (var i = 0; item = branches[i]; i++) {
		if(item == id){
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
function setQualifier(id) {
	qualifier = getQualifier(id);
	version  = getVersion(qualifier.version);
	$.cookie('qualifier', id, {path: '/' });
	$.cookie('version', version.id, {path: '/' });
}
function setVersion(id) {
	version  = getVersion(id);
	qualifier = getQualifierByVersion(id);		
	if(qualifier){
		$.cookie('qualifier', id, {path: '/' });			
	} else {
		$.removeCookie('qualifier', {path: '/' });
	}
	$.cookie('version', version.id, {path: '/' });
}
function setBranch(id) {
	branch = getBranch(id);
	$.cookie('branch', branch, {path: '/' });
}
function setEdition(id) {
	edition = getEdition(id);
	$.cookie('edition', id, {path: '/' });
}
function setDistribution(id) {
	distributionId = id;
	$.cookie('distribution', id, {path: '/' });		
};
function getDefaultBranch(qualifier) {
	var q = null;
	if (qualifier) {
		q = getQualifier(qualifier)
	}
	if (!q) {
		q = getQualifier(getDefaultQualifier());
	}
	return q.version.substring(0, 3);
}
function getDefaultQualifier(branch) {
	var result = 'stable'; // default value
	if(branch) {
		// guess the corresponding qualifier
		var q = null;
		for (var i = 0; q = qualifiers[i]; i++) {
			if(q.version.substring(0, 3) == branch){
				result = q.id;
				break;
			}
		};
	}
	return result;
}
function getDefaultEdition(version) {
	var str = 'all';
	for (var i = 0; i < version.editions.length; i++) {
		if('jse' == version.editions[i].id){
			return 'jse';
		}
	}
	return str;
}
function getDefaultDistribution(version, edition) {
	return 'zip';
}