package org.javabubble.generator.site;

import java.io.IOException;
import java.io.Writer;

import org.javabubble.generator.model.JavaBubble;

abstract class TextArtifact {

	protected final JavaBubble bubble;

	private final String path;

	public TextArtifact(JavaBubble bubble, String path) {
		this.bubble = bubble;
		this.path = path;
	}

	void generate(SiteOutput output) throws IOException {
		try (var writer = output.newTextDocument(path)) {
			generate(writer);
		}
	}

	abstract void generate(Writer writer) throws IOException;

}