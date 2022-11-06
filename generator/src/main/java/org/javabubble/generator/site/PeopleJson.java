package org.javabubble.generator.site;

import java.io.IOException;
import java.io.Writer;

import org.javabubble.generator.model.JavaBubble;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PeopleJson extends TextArtifact {

	static final String LOCATION = "api/bubble/people.json";

	PeopleJson(JavaBubble bubble) {
		super(bubble, LOCATION);
	}

	@Override
	void generate(Writer writer) throws IOException {
		var mapper = new ObjectMapper(new JsonFactory());
		mapper.writeValue(writer, bubble.people());
	}

}
