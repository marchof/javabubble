package org.javabubble.generator.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;

class ModelLoaderTest {

	@Test
	@Disabled ("'toot.thoughtworks.com' is on maintenance (now?)")
	void validateProblematics() throws IOException {
		ModelLoader modelLoader = new ModelLoader(Path.of ("src/test/resources/javapeople-with-problems.yaml"));
		var bubble = modelLoader.load();
		ModelValidator.validate(bubble, true);
		Assertions.assertNotNull(bubble);
	}

	@Test
	void loadNonExisting() throws IOException {
		ModelLoader modelLoader = new ModelLoader(Path.of ("src/test/resources/javapeople-nonexisting.yaml"));
		var bubble = modelLoader.load();
		Assertions.assertThrows(IllegalArgumentException.class, () -> ModelValidator.validate(bubble, true));
	}
}