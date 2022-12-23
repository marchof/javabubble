package org.javabubble.generator.validation;

import org.javabubble.generator.model.RedditHandle;

import com.fasterxml.jackson.databind.JsonNode;

class RedditValidator extends RestAPIValidator<RedditHandle> {

	@Override
	String requestURL(RedditHandle handle) {
		return "https://api.reddit.com/user/%s/about".formatted(handle.getLocalHandle());
	}

	@Override
	RedditHandle readResponse(JsonNode response) {
		return new RedditHandle(response.get("data").get("name").asText());
	}

}
