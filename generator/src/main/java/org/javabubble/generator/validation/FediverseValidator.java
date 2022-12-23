package org.javabubble.generator.validation;

import org.javabubble.generator.model.FediverseHandle;

import com.fasterxml.jackson.databind.JsonNode;

class FediverseValidator extends RestAPIValidator<FediverseHandle> {

	@Override
	String requestURL(FediverseHandle handle) {
		return "https://%s/.well-known/webfinger?resource=acct:%s".formatted(handle.getServer(), handle.getHandle());
	}

	@Override
	FediverseHandle readResponse(JsonNode response) {
		var subject = response.get("subject").asText().replace("acct:", "");
		return new FediverseHandle(subject);
	}

}
