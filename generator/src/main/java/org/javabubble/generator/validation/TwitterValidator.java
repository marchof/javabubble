package org.javabubble.generator.validation;

import org.javabubble.generator.model.TwitterHandle;

import com.fasterxml.jackson.databind.JsonNode;

class TwitterValidator implements HandleValidator<TwitterHandle> {

	private final RestClient client;

	public TwitterValidator() {
		client = new RestClient("API_AUTH_TWITTER");
	}

	@Override
	public TwitterHandle validate(TwitterHandle handle) {
		return client.get("https://api.twitter.com/2/users/by?usernames=%s", handle.getLocalHandle()).content() //
				.map(n -> n.get("data")) //
				.map(n -> n.get(0)) //
				.map(n -> n.get("username")) //
				.map(JsonNode::asText) //
				.map(TwitterHandle::new).orElse(null);
	}

}
