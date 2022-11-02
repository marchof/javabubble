package org.javabubble.generator.site;

import java.io.IOException;
import java.util.List;

import org.javabubble.generator.model.JavaBubble;

public class Site {

	private SiteOutput output;

	private final List<TextArtifact> artifacts;

	public Site(JavaBubble bubble, SiteOutput output) {
		this.output = output;
		this.artifacts = List.of( //
				new ReadmeHTML(bubble), //
				new PeopleYaml(bubble), //
				new PeopleJson(bubble), //
				new FollowingCSV(bubble));
	}

	public void generate() throws IOException {
		for (var a : artifacts) {
			a.generate(output);
		}
	}

}
