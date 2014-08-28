$(document).ready(function() {
	
	$("#videoPopup div.video-close-button").click(function() {
		$("#videoPopup").hide();
	});
	
	$(".kinlane div.close-button").click(function() {
		$("#deployVideoModal").hide();
		$("#deployUserGuideModal").hide();
		$("#deployKinModal").hide();
		$("#deployStickersModal").hide();
	});
    
    $('.btn-download-resources').click(
		function(event) {
			// open campaign popup
			if(event.currentTarget.classList[1] == 'watch-video-btn') {
				if ($.cookie('fill-email-campaign') != 'true') {
					$("#deployVideoModal").show();
				} else {
					printDoc(0);
				}
			} else if(event.currentTarget.classList[1] == 'userguidelink') {
				if ($.cookie('fill-email-campaign') != 'true') {
					$("#deployUserGuideModal").show();
				} else {
					printDoc(1);
				}
			} else if(event.currentTarget.classList[1] == 'kinlaneguidelink') {
				if ($.cookie('fill-email-campaign') != 'true') {
					$("#deployKinModal").show();
				} else {
					printDoc(2);
				}
			} else if(event.currentTarget.classList[1] == 'paasguidelink') {
				//$("#deployPaasModal").show();
				printDoc(3);
			} else if(event.currentTarget.classList[1] == 'roadguidelink') {
				//$("#deployRoadGuideModal").show();
				printDoc(4);
			} else if(event.currentTarget.classList[1] == 'stickerscampaignlink') {
				$("#deployStickersModal").show();
			}
			
			// we stop the event propagation
			if (event.preventDefault) {
			  event.preventDefault();
			}
			event.returnValue = false;								
		}
	);
	
	function printDoc(docNumber){
		var docLocation = "";
		if (docNumber == 0) {
			window.setTimeout($("#videoPopup").show(),1000);
		}else {
			if (docNumber == 1) {
				docLocation = "http://restlet.com/learn/archives/misc/2.2/rf-user-guide-2-2.pdf";
			} else if (docNumber == 2) {
				docLocation = "http://restlet.files.wordpress.com/2013/12/gigaom-research-a-field-guide-to-web-apis.pdf?utm_source=restlet-site&utm_medium=popup&utm_campaign=Kin%20Lane%20Guide";
			} else if (docNumber == 2) {
				docLocation = "http://restlet.files.wordpress.com/2014/05/gigaom-research-paas-market-moves-beyond-deployment-and-scaling.pdf?utm_source=restlet-site&utm_medium=resources&utm_campaign=David%20Linthicum%20ReportRP";
			} else if (docNumber == 4) {
				docLocation = "http://blog.restlet.com/wp-content/uploads/2014/06/ROAD-Designing-a-RESTful-web-API1.pdf?utm_source=restlet-site&utm_medium=resources&utm_campaign=ROAD%20GuideRP";
			}
			window.open(docLocation, "_blank");
		}
	}
	
	function generalModal(modalNumber){
		var emailField = "";
		var mixEvent = "";
		var docLocation = "";
		var modName = "";
		var button = "";
		var email = "";
		if (modalNumber == 'Video') {
			emailField = "3min Demo Video";
			mixEvent = "Watched 3min Demo Video";
			modName = "#deployVideoModal";
			button = "campaignVideoButton";
			email = "campaignVideoEmail";
		} else if (modalNumber == 'Guide') {
			emailField = "User Guide PDF";
			mixEvent = "Downloaded UserGuide PDF";
			docLocation = "http://restlet.com/learn/archives/misc/2.2/rf-user-guide-2-2.pdf";
			modName = "#deployUserGuideModal";
			button = "campaignGuideButton";
			email = "campaignGuideEmail";
		} else if (modalNumber == 'Kin') {
			emailField = "Kin Lane Guide";
			mixEvent = "Downloaded Kin Lane Guide";
			docLocation = "http://restlet.files.wordpress.com/2013/12/gigaom-research-a-field-guide-to-web-apis.pdf?utm_source=restlet-site&utm_medium=popup&utm_campaign=Kin%20Lane%20Guide";
			modName = "#deployKinModal";
			button = "campaignKinButton";
			email = "campaignKinEmail";
		}
		if (checkEmail(email, button)) {
			try {
				mixpanel.track("Shared email", {
					"email": $("#" + email).val(),
					"Email field location": emailField
				});
				mixpanel.alias($("#" + email).val(), mixpanel.get_distinct_id());
				mixpanel.people.set({"$email": $("#" + email).val()});
				mixpanel.track(mixEvent);
			} catch(err) {
				//nothing to do
			}
				
			// close popup
			$("#" + email).val("");
			$(modName).hide();
			
			// Set a one year cookie dedicated to all campaigns (unless stickers). 
			$.cookie('fill-email-campaign', 'true', {
				expires: 365
			});

			if (modalNumber == 'Video') {
				window.setTimeout($("#videoPopup").show(),1000);
			} else {				
				// launch pdf download in a new tab
				window.open(docLocation, "_blank");
			}
		}
	}
	
	function stickersModal() {
		if (checkEmail("campaignStickersEmail", "campaignStickersButton") 
				&& checkFieldEmpty("campaignName", "campaignStickersButton") 
				&& checkFieldEmpty("campaignAddress", "campaignStickersButton")) {
			
			try {
				mixpanel.track("Downloaded Resource", {
					"Resource Name":"Restlet Stickers",
					"Resource Location":"Resources page",
					"Email": $("#campaignStickersEmail").val(),
					"Name": $("#campaignName").val(),
					"Mailing Address": $("#campaignAddress").val()
				});
			} catch(err) {
				//nothing to do
			}
			
			// close popup
			$("#campaignName").val("");
			$("#campaignStickersEmail").val("");
			$("#campaignAddress").val("");
			$("#deployStickersModal").hide();
		}
	}
	
});
