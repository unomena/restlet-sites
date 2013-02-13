var cBranches;
//	var myCombo = (new Dropdown("idCombo")).initialize();
//myCombo.addSelectionListener(function(value) {
//    (...)
//});

function loadBranches() {
	cBranches.empty();
     for (var i = 0; b = branches[i]; i++) {
    	 if(("1.2" != b) && ("1.0" != b) && ("0.0" != b)){
        	 cBranches.append('<li id="' + b + '">' + b + '</li>');    		 
    	 }
     };
     cBranches.children().click(function() {
          setBranch(this.id);
          $(location).attr('href',"/learn/guide/" + branch);
     });
};
function refresh() {
	$("#" + cBranches.attr('id') + '-bt').empty();
	$("#" + cBranches.attr('id') + '-bt').append("<strong>" + branch + "</strong>");
}
function init(idBranches) {
	cBranches = $("#" + idBranches);
	loadBranches();
	branch = $.cookie('branch');
	if (!branch) {
		$.cookie('branch', getDefaultBranch($.cookie('qualifier')), {path: '/' });
		branch = $.cookie('branch');
	}
    refresh();
}
