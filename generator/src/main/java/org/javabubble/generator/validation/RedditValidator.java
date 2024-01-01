package org.javabubble.generator.validation;

import org.javabubble.generator.model.RedditHandle;

import com.fasterxml.jackson.databind.JsonNode;

@SuppressWarnings("unused")
class RedditValidator implements HandleValidator<RedditHandle> {

	private final RestClient client;

	public RedditValidator() {
		client = new RestClient();
	}

	@Override
	public RedditHandle validate(RedditHandle handle) {
		return client.get("https://api.reddit.com/user/%s/about", handle.getLocalHandle()).content() //
				.map(n -> n.get("data")) //
				.map(n -> n.get("name")) //
				.map(JsonNode::asText) //
				.map(RedditHandle::new).orElse(null);
	}

}
