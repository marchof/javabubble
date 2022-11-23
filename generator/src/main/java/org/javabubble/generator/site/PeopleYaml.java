package org.javabubble.generator.site;

import java.io.IOException;
import java.io.Writer;

import org.javabubble.generator.model.JavaBubble;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class PeopleYaml extends TextArtifact {

	static final String LOCATION = "api/bubble/people.yaml";

	PeopleYaml(JavaBubble bubble) {
		super(bubble, LOCATION);
	}

	@Override
	void generate(Writer writer) throws IOException {
		var mapper = new ObjectMapper(new YAMLFactory());
		mapper.writeValue(writer, bubble.people());
	}

}
