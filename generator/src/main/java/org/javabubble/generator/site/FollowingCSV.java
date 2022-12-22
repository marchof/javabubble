package org.javabubble.generator.site;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Objects;

import org.javabubble.generator.model.Handle;
import org.javabubble.generator.model.JavaBubble;
import org.javabubble.generator.model.JavaPerson;

class FollowingCSV extends TextArtifact {

	static final String LOCATION = "api/bubble/following.csv";

	FollowingCSV(JavaBubble bubble) {
		super(bubble, LOCATION);
	}

	@Override
	void generate(Writer writer) throws IOException {
		var printer = new PrintWriter(writer);
		printer.println("Account address,Show boosts,Notify on new posts,Languages");
		bubble.people().stream() //
				.map(JavaPerson::fediverse) //
				.filter(Objects::nonNull) //
				.map(Handle::getHandle) //
				.map("@%s,true,false,"::formatted) //
				.forEach(printer::println);
		printer.flush();
	}

}
