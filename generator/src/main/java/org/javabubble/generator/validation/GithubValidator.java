package org.javabubble.generator.validation;

import org.javabubble.generator.model.GithubHandle;

import com.fasterxml.jackson.databind.JsonNode;

class GithubValidator extends RestAPIValidator<GithubHandle> {

	public GithubValidator() {
		super("API_AUTH_GITHUB");
	}

	@Override
	String requestURL(GithubHandle handle) {
		return "https://api.github.com/users/%s".formatted(handle.getLocalHandle());
	}

	@Override
	GithubHandle readResponse(JsonNode response) {
		return new GithubHandle(response.get("login").asText());
	}

}
