package org.javabubble.generator.site;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Objects;

import org.javabubble.generator.model.Handle;
import org.javabubble.generator.model.JavaBubble;
import org.javabubble.generator.model.JavaPerson;

class ListCSV extends TextArtifact {

	static final String LOCATION = "api/bubble/list.csv";

	ListCSV(JavaBubble bubble) {
		super(bubble, LOCATION);
	}

	@Override
	void generate(Writer writer) throws IOException {
		var printer = new PrintWriter(writer);
		printer.println("List name,Account address");
		bubble.people().stream() //
				.map(JavaPerson::fediverse) //
				.filter(Objects::nonNull) //
				.map(Handle::getHandle) //
				.map("Java Bubble,@%s"::formatted) //
				.forEach(printer::println);
		printer.flush();
	}

}
