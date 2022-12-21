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

	private final Path base;
	private final ObjectMapper mapper;

	public ModelLoader(Path base) {
		this.base = base;
		this.mapper = new ObjectMapper(new YAMLFactory());
	}

	public JavaBubble load() throws IOException {
		return new JavaBubble(parseYAML("javapeople.yaml", new TypeReference<List<JavaPerson>>() {
		}));
	}

	private <T> T parseYAML(String file, TypeReference<T> type) throws IOException {
		try (var in = Files.newBufferedReader(base.resolve(file), UTF_8)) {
			return mapper.readValue(in, type);
		}
	}

}
