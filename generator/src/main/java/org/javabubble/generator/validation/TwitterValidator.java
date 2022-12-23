package org.javabubble.generator.validation;

import org.javabubble.generator.model.TwitterHandle;

import com.fasterxml.jackson.databind.JsonNode;

class TwitterValidator extends RestAPIValidator<TwitterHandle> {

	public TwitterValidator() {
		super("API_AUTH_TWITTER");
	}

	@Override
	String requestURL(TwitterHandle handle) {
		return "https://api.twitter.com/2/users/by?usernames=%s".formatted(handle.getLocalHandle());
	}

	@Override
	TwitterHandle readResponse(JsonNode response) {
		var data = response.get("data");
		if (data == null) {
			return null;
		}
		return new TwitterHandle(data.get(0).get("username").asText());
	}

}
