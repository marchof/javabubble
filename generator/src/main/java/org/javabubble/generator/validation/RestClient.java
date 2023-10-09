package org.javabubble.generator.validation;

import static java.net.http.HttpClient.Redirect.NORMAL;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

final class RestClient {

	private final HttpClient client;

	private final String authorization;

	RestClient() {
		this(null);
	}

	RestClient(String authorizationEnvVariable) {
		client = HttpClient.newBuilder().followRedirects(NORMAL).build();
		if (authorizationEnvVariable != null) {
			authorization = System.getenv(authorizationEnvVariable);
		} else {
			authorization = null;
		}
	}

	Response get(String uri, Object... params) {
		var builder = HttpRequest.newBuilder(URI.create(uri.formatted(params)));
		if (authorization != null) {
			builder.header("Authorization", authorization);
		}
		try {
			var response = client.send(builder.build(), BodyHandlers.ofString());
			JsonNode content;
			if (response.statusCode() == 200) {
				var mapper = new ObjectMapper(new JsonFactory());
				content = mapper.readTree(response.body());
			} else {
				System.out.println("Unexpected server response: " + response.statusCode());
				System.out.println(response.body());
				content = null;
			}
			return new Response(Optional.ofNullable(content), response.statusCode());
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			return new Response(Optional.empty(), 500);
		}
	}

	record Response(Optional<JsonNode> content, int status) {
	}

}
