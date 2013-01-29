function Dropdown(id) {
	this.id = id;
	this.selectionListeners = [];
}

Dropdown.prototype = {
	getDropDownElement: function(elt) {
		var parent = elt.parent();
		while (!parent.hasClass("dropdown")) {
			parent = parent.parent();
		}
		return parent;
	},

	initialize: function(options) {
		if (options!=null) {
			this.options = options;
		} else {
			this.options = {global:false};
		}
		return this.internalInitialize();
	},

	reinitialize: function() {
		//this.selectionListeners = [];
		return this.internalInitialize();
	},

	internalInitialize: function() {
		var currentThis = this;
		var dropdownDtASelector = "#"+this.id+".dropdown dt a";
		if (this.options.global) {
			dropdownDtASelector = "#"+this.id+" .dropdown dt a";
		}
		$(dropdownDtASelector).unbind("click");
		$(dropdownDtASelector).click(function() {
			var dropdown = currentThis.getDropDownElement($(this));
			dropdown.find("dd ul").toggle();
			var dt = dropdown.find("dt");
			if (dt.hasClass("opened")) {
				dt.removeClass("opened");
			} else {
				dt.addClass("opened");
			}
			var dd = dropdown.find("dd");
			if (dd.hasClass("opened")) {
				dd.removeClass("opened");
			} else {
				dd.addClass("opened");
			}
		});
                            
		var dropdownDdASelector = "#"+this.id+".dropdown dd ul li a";
		if (this.options.global) {
			dropdownDdASelector = "#"+this.id+" .dropdown dd ul li a";
		}
		$(dropdownDdASelector).unbind("click");
		$(dropdownDdASelector).click(function() {
			var text = $(this).html();
			var dropdown = currentThis.getDropDownElement($(this));
			if (!currentThis.options["menu"]) {
				dropdown.find("dt a").html(text);
			}
			var value = $("<div>"+text+"</div>").find("span.value").text();
			for (var i=0; i<currentThis.selectionListeners.length; i++) {
				currentThis.selectionListeners[i](value, $(this));
			}
			dropdown.find("dt.opened").removeClass("opened");
			dropdown.find("dd.opened").removeClass("opened");
			dropdown.find("dd ul").hide();
		});

		if (!Dropdown.globalEventListenerRegistered) {
			$(document).bind('click', function(e) {
				Dropdown.globalEventListenerRegistered = true;
				var $clicked = $(e.target);
				if (! $clicked.parents().hasClass("dropdown")) {
					$(".dropdown dd ul").hide();
					$(".dropdown dt.opened").removeClass("opened");
					$(".dropdown dd.opened").removeClass("opened");
				}
			});
		}
		
		return this;
	},

	getSelectedValue: function() {
		var dropdownSelector = "#"+this.id+".dropdown";
		if (this.options.global) {
			dropdownSelector = "#"+this.id+" .dropdown";
		}
		return $(dropdownSelector).find("dt a span.value").text();
	},
        	
	setSelectedValue: function(value) {
		var currentThis = this;
		var dropdownDdLiASelector = "#"+this.id+".dropdown dd ul li a span.value";
		if (this.options.global) {
			dropdownDdLiASelector = "#"+this.id+" .dropdown dd ul li a span.value";
		}
		$(dropdownDdLiASelector).each(function() {
			if ($(this).text()==value) {
				var parent = $(this).parent();
				var dropdown = currentThis.getDropDownElement($(this));
				dropdown.find("dt a").html(parent.html());
			}
		});
	},
	
	setSelectedText: function(html) {
		var selectedValue = this.getSelectedValue();
		//Update dt
		var dropdownDtLiASelector = "#"+this.id+".dropdown dt a";
		if (this.options.global) {
			dropdownDtLiASelector = "#"+this.id+" .dropdown dt a";
		}
		$(dropdownDtLiASelector).html(html+"<span class=\"value\">"+selectedValue+"</span>");

		//Update dl
		var dropdownDdLiASelector = "#"+this.id+".dropdown dd ul li a";
		if (this.options.global) {
			dropdownDdLiASelector = "#"+this.id+" .dropdown dd ul li a";
		}
		$(dropdownDdLiASelector).each(function() {
			var value = $(this).find("span.value").text();
			if (selectedValue==value) {
				$(this).html(html+"<span class=\"value\">"+selectedValue+"</span>");
			}
		});
	},
	
	setTextForValue: function(value, html, reuseValue) {
		if (reuseValue==null) {
			reuseValue = true;
		}
		var dropdownDdLiASelector = "#"+this.id+".dropdown dd ul li a";
		if (this.options.global) {
			dropdownDdLiASelector = "#"+this.id+" .dropdown dd ul li a";
		}
		$(dropdownDdLiASelector).each(function() {
			var tmpValue = $(this).find("span.value").text();
			if (tmpValue==value) {
				if (reuseValue) {
					$(this).html(html+"<span class=\"value\">"+selectedValue+"</span>");
				} else {
					$(this).html(html);
				}
			}
		});
	},
	
	addSelectionListener: function(callback) {
		this.selectionListeners.push(callback);
	},
	
	appendElement: function(html, value) {
		var dropdownDdUlSelector = "#"+this.id+".dropdown dd ul";
		if (this.options.global) {
			dropdownDdUlSelector = "#"+this.id+" .dropdown dd ul";
		}
		$(dropdownDdUlSelector).append("<li><a>"+html+"<span class=\"value\">"+value+"</span></a></li>");
	},
	
	addElementBeforeLast: function(html, value) {
		var dropdownDdUlSelector = "#"+this.id+".dropdown dd ul li:last-child";
		if (this.options.global) {
			dropdownDdUlSelector = "#"+this.id+" .dropdown dd ul li:last-child";
		}
		$(dropdownDdUlSelector).before("<li><a>"+html+"<span class=\"value\">"+value+"</span></a></li>");
	}
};