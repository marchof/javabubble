package org.javabubble.generator.site;

import static j2html.TagCreator.rawHtml;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

import j2html.tags.DomContent;

enum SocialIcons {

	mastodon, twitter, github, reddit, linkedin, bluesky, youtube;

	DomContent svg() {
		return rawHtml("<svg class=\"socialicon\"><use xlink:href=\"#%s\"/></svg>".formatted(name()));
	}

	static DomContent symbols() {
		var buffer = new StringWriter();
		buffer.write("<svg display=\"none\">");
		for (var i : values()) {
			try (var in = SocialIcons.class.getResourceAsStream("assets/%s.svg".formatted(i.name()))) {
				buffer.write(new String(in.readAllBytes(), StandardCharsets.UTF_8));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		buffer.write("</svg>");
		return rawHtml(buffer.toString());
	}

}
