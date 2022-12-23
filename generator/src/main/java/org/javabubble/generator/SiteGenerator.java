package org.javabubble.generator;

import java.io.IOException;
import java.nio.file.Path;

import org.javabubble.generator.model.ModelLoader;
import org.javabubble.generator.site.FileOutput;
import org.javabubble.generator.site.Site;

public class SiteGenerator {

	public static void main(String[] args) throws IOException {
		var bubble = new ModelLoader(Path.of("../")).load();
		var output = new FileOutput(Path.of("target/site"));
		new Site(bubble, output).generate();
	}
}
