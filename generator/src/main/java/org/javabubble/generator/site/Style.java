package org.javabubble.generator.site;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;

import j2html.TagCreator;
import j2html.tags.specialized.StyleTag;
import j2html.utils.CSSMin;

class Style {

	// Adopt Github Stylesheet: https://github.com/sindresorhus/github-markdown-css
	private static final URI GITHUB_STYLESHEET = URI
			.create("https://cdnjs.cloudflare.com/ajax/libs/github-markdown-css/5.1.0/github-markdown.min.css");

	private static final String EXTRACSS = CSSMin.compressCss("""
			.markdown-body {
				box-sizing: border-box;
				min-width: 200px;
				max-width: 1024px;
				margin: 0 auto;
				padding: 45px;
			}
			@media (max-width: 767px) {
				.markdown-body {
					padding: 15px;
				}
			}
			""");

	private static String loadGitHubCSS() throws IOException {
		var client = HttpClient.newHttpClient();
		var get = HttpRequest.newBuilder(GITHUB_STYLESHEET).GET().build();
		try {
			return client.send(get, BodyHandlers.ofString(StandardCharsets.UTF_8)).body();
		} catch (InterruptedException e) {
			throw new InterruptedIOException();
		}
	}

	static StyleTag gitHubStyle() throws IOException {
		return TagCreator.style(loadGitHubCSS() + CSSMin.compressCss(EXTRACSS));
	}

}
