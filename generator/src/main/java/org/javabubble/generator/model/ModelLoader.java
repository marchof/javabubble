package org.javabubble.generator.model;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class ModelLoader {

	private final Path path;
	private final ObjectMapper mapper;

	public ModelLoader(Path path) {
		this.path = path;
		this.mapper = new ObjectMapper(new YAMLFactory());
	}

	public JavaBubble load() throws IOException {
		var bubble = new JavaBubble(parseYAML(new TypeReference<List<JavaPerson>>() {
		}));
		ModelValidator.validate(bubble);
		return bubble;
	}

	private <T> T parseYAML(TypeReference<T> type) throws IOException {
		try (var in = Files.newBufferedReader(path, UTF_8)) {
			return mapper.readValue(in, type);
		}
	}

}
