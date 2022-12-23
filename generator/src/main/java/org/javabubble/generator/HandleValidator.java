package org.javabubble.generator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.javabubble.generator.model.JavaBubble;
import org.javabubble.generator.model.ModelLoader;
import org.javabubble.generator.validation.PersonValidator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class HandleValidator {

	public static void main(String[] args) throws IOException {
		var validator = new PersonValidator();
		var bubble = new ModelLoader(Path.of("../")).load();
		var cleanedBubble = new JavaBubble(bubble.people().stream() //
				.map(validator::validate) //
				.toList());
		try (var writer = Files.newBufferedWriter(Path.of("../javapeople.yaml"))) {
			writer.write("# Please add new entries in alphabetic order of the name.\n");
			var mapper = new ObjectMapper(new YAMLFactory());
			mapper.writeValue(writer, cleanedBubble.people());
		}
	}

}
