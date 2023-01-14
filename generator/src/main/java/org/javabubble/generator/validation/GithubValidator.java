package org.javabubble.generator.validation;

import org.javabubble.generator.model.GithubHandle;

import com.fasterxml.jackson.databind.JsonNode;

class GithubValidator implements HandleValidator<GithubHandle> {

	private final RestClient client;

	public GithubValidator() {
		client = new RestClient("API_AUTH_GITHUB");
	}

	@Override
	public GithubHandle validate(GithubHandle handle) {
		return client.get("https://api.github.com/users/%s", handle.getLocalHandle()).content() //
				.map(n -> n.get("login")) //
				.map(JsonNode::asText) //
				.map(GithubHandle::new).orElse(null);
	}

}
