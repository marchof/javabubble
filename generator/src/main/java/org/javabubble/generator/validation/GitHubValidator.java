package org.javabubble.generator.validation;

import org.javabubble.generator.model.GitHubHandle;

import com.fasterxml.jackson.databind.JsonNode;

class GitHubValidator implements HandleValidator<GitHubHandle> {

	private final RestClient client;

	public GitHubValidator() {
		client = new RestClient("API_AUTH_GITHUB");
	}

	@Override
	public GitHubHandle validate(GitHubHandle handle) {
		return client.get("https://api.github.com/users/%s", handle.getLocalHandle()).content() //
				.map(n -> n.get("login")) //
				.map(JsonNode::asText) //
				.map(GitHubHandle::new).orElse(null);
	}

}
