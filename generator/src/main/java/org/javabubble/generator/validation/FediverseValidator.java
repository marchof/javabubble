package org.javabubble.generator.validation;

import java.net.URI;

import org.javabubble.generator.model.FediverseHandle;

import com.fasterxml.jackson.databind.JsonNode;

class FediverseValidator implements HandleValidator<FediverseHandle> {

	private final RestClient client;

	public FediverseValidator() {
		client = new RestClient();
	}

	@Override
	public FediverseHandle validate(FediverseHandle handle) {
		// webfinger
		var response = client.get("https://%s/.well-known/webfinger?resource=acct:%s", handle.getServer(),
				handle.getHandle());
		if (response.content().isEmpty()) {
			return null;
		}
		var content = response.content().get();
		handle = new FediverseHandle(content.get("subject").asText().replace("acct:", ""));

		// find URL of Mastodon instance
		URI apiurl = null;
		for (var l : content.get("links")) {
			if ("self".equals(l.get("rel").asText())) {
				apiurl = URI.create(l.get("href").asText()).resolve("/api/v1/");
			}
		}
		if (apiurl == null) {
			System.err.println("Mostodon instance not found");
			return null;
		}

		// Check Mastodon instance for moved handles
		return client.get(apiurl + "/accounts/lookup?acct=%s", handle.getLocalHandle()).content() //
				.map(n -> n.get("moved")) //
				.map(n -> n.get("acct")) //
				.map(JsonNode::asText) //
				.map(FediverseHandle::new) //
				.orElse(handle);
	}

}
