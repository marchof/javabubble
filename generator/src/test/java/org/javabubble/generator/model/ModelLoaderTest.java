package org.javabubble.generator.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;

class ModelLoaderTest {

	@Test
	@Disabled ("'toot.thoughtworks.com' is on maintenance (now?)")
	void loadProblematics() throws IOException {
		ModelLoader modelLoader = new ModelLoader(Path.of ("src/test/resources/javapeople-with-problems.yaml"));
		var bubble = modelLoader.load();
		Assertions.assertNotNull(bubble);
	}

	@Test
	void loadNonExisting() throws IOException {
		ModelLoader modelLoader = new ModelLoader(Path.of ("src/test/resources/javapeople-nonexisting.yaml"));
		Assertions.assertThrows(IllegalArgumentException.class, modelLoader::load);
	}
}