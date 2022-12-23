package org.javabubble.generator.validation;

import static java.net.http.HttpClient.Redirect.NORMAL;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;

import org.javabubble.generator.model.Handle;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

abstract class RestAPIValidator<H extends Handle> implements HandleValidator<H> {

	private final HttpClient client;

	private final String authorization;

	RestAPIValidator() {
		this(null);
	}

	RestAPIValidator(String authorizationEnvVariable) {
		client = HttpClient.newBuilder().followRedirects(NORMAL).build();
		if (authorizationEnvVariable != null) {
			authorization = System.getenv(authorizationEnvVariable);
		} else {
			authorization = null;
		}
	}

	@Override
	public H validate(H handle) {
		try {
			var uri = URI.create(requestURL(handle));
			var builder = HttpRequest.newBuilder(uri);
			if (authorization != null) {
				builder.header("Authorization", authorization);
			}
			var response = client.send(builder.build(), BodyHandlers.ofString());
			if (response.statusCode() != 200) {
				return null;
			}
			var mapper = new ObjectMapper(new JsonFactory());
			var root = mapper.readTree(response.body());
			return readResponse(root);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}

	abstract String requestURL(H handle);

	abstract H readResponse(JsonNode response);

}
