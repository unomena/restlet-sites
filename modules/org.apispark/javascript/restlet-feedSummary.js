$(document).ready(function() {
	var clientResource = new ClientResource("/feeds/summary");
	clientResource.get(function(representation) {
		var jsonRepresentation = new JsonRepresentation(representation);
		var listContent = "";
		var elements = jsonRepresentation.getObject();
		if (elements.length && elements.length > 0) {
			listContent = '<h4 class="blog">News</h4><h5 class="blog"><a href="http://blog.restlet.com">Our blog&nbsp;<img style="margin:5px" alt="Syndication feed" src="/images/feed-20.png"></a></h5>';
			listContent += '<ul class="blog">';
			for ( var i = 0; i < elements.length; i++) {
				listContent += createFeedElement(elements[i]);
			}
			listContent += '</ul>';
		}
		$("#restletFeed").html(listContent);
	}, MediaType.APPLICATION_JSON);

	$("#searchTopicButton").click(function() {
		$(location).attr('href', "https://www.google.com/search?q=apispark+" + $("#searchArea").val());
	});

	$("#signupButton").click(function() {
		$(location).attr('href', "http://restlet.us4.list-manage2.com/subscribe?u=6e9d916ca1faf05c7dc49d21e&id=a8aa911b32");
	});

});

// newsletter
createFeedElement = function(element) {
	var result = "<li>";
	result += element.published;
	result += "&nbsp;<a href=\"" + element.uri + "\">" + element.title + "</a>";
	result += "</li>";
	return result;
};