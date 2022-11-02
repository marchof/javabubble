package org.javabubble.generator.site;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileOutput implements SiteOutput {

	private Path base;

	public FileOutput(Path base) {
		this.base = base;
	}

	@Override
	public Writer newTextDocument(String path) throws IOException {
		var file = base.resolve(path);
		Files.createDirectories(file.getParent());
		return Files.newBufferedWriter(file, TEXT_ENCODING);
	}

}
