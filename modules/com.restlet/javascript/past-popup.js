$(document).ready(function() {
	$(".kinlane div.close-button").click(function() {
		$("#deployVideoModal").hide();
		$("#deployUserGuideModal").hide();
		$("#deployKinModal").hide();
		$("#deployStickersModal").hide();
	});
    
    $('.btn-download-resources').click(
		function(event) {
			// open campaign popup
			if(event.currentTarget.classList[2] == 'watch-video-btn') {				
				$("#deployVideoModal").show();
			} else if(event.currentTarget.classList[2] == 'userguidelink') {
				$("#deployUserGuideModal").show();
			} else if(event.currentTarget.classList[2] == 'kinlaneguidelink') {
				$("#deployKinModal").show();
			} else if(event.currentTarget.classList[2] == 'paasguidelink') {
				//$("#deployPaasModal").show();
			} else if(event.currentTarget.classList[2] == 'roadguidelink') {
				//$("#deployRoadGuideModal").show();
			} else if(event.currentTarget.classList[2] == 'stickerscampaignlink') {
				$("#deployStickersModal").show();
			}
			
			// we stop the event propagation
			if (event.preventDefault) {
			  event.preventDefault();
			}
			event.returnValue = false;								
		}
	);

	$('#campaignButton').click(
		function(event) {
			if(event.currentTarget.classList[2] == 'watch-video-btn') {				
				generalModal(0);
			} else if(event.currentTarget.classList[2] == 'userguidelink') {
				generalModal(1);
			} else if(event.currentTarget.classList[2] == 'kinlaneguidelink') {
				generalModal(2);
			} else if(event.currentTarget.classList[2] == 'paasguidelink') {
				//paasModal();
			} else if(event.currentTarget.classList[2] == 'roadguidelink') {
				//roadGuideModal();
			} else if(event.currentTarget.classList[2] == 'stickerscampaignlink') {
				stickersModal();
			}
		}
	);
	
	function generalModal(modalNumber){
		var emailField = "";
		var mixEvent = "";
		var docLocation = "";
		var modName = "";
		if (modalNumber == 1) {
			emailField = "3min Demo Video";
			mixEvent = "Watched 3min Demo Video";
			modName = "#deployVideoModal";
		} else if (modalNumber == 1) {
			emailField = "User Guide PDF";
			mixEvent = "Downloaded UserGuide PDF";
			docLocation = "http://restlet.com/learn/archives/misc/2.2/rf-user-guide-2-2.pdf";
			modName = "#deployUserGuideModal";
		} else if (modalNumber == 2) {
			emailField = "Kin Lane Guide";
			mixEvent = "Downloaded Kin Lane Guide";
			docLocation = "http://restlet.files.wordpress.com/2013/12/gigaom-research-a-field-guide-to-web-apis.pdf?utm_source=restlet-site&utm_medium=popup&utm_campaign=Kin%20Lane%20Guide";
			modName = "#deployKinModal";
		}
		if (checkEmail("campaignEmail", "campaignButton")) {
			try {
				mixpanel.track("Shared email", {
					"email": $("#campaignEmail").val(),
					"Email field location": emailField
				});
				mixpanel.alias($("#campaignEmail").val(), mixpanel.get_distinct_id());
				mixpanel.people.set({"$email": $("#campaignEmail").val()});
				mixpanel.track(mixEvent);
			} catch(err) {
				//nothing to do
			}
				
			// close popup
			$("#campaignEmail").val("");
			$(modName).hide();
			
			// Set a one year cookie dedicated to all campaigns (unless stickers). 
			$.cookie('fill-email-campaign', 'true', {
				expires: 365
			});
			
			if (modalNumber == 0) {
				window.setTimeout($("#videoPopup").show(),1000);
			} else {				
				// launch pdf download in a new tab
				window.open(docLocation, "_blank");
			}
		}
	}
	
	function stickersModal() {
		if (checkEmail("campaignEmail", "campaignButton") 
				&& checkFieldEmpty("campaignName", "campaignButton") 
				&& checkFieldEmpty("campaignAddress", "campaignButton")) {
			
			try {
				mixpanel.track("Downloaded Resource", {
					"Resource Name":"Restlet Stickers",
					"Resource Location":"Resources page",
					"Email": $("#campaignEmail").val(),
					"Name": $("#campaignName").val(),
					"Mailing Address": $("#campaignAddress").val()
				});
			} catch(err) {
				//nothing to do
			}
			
			// close popup
			$("#campaignName").val("");
			$("#campaignEmail").val("");
			$("#campaignAddress").val("");
			$("#deployStickersModal").hide();
		}
	}
	
});
