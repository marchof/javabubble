package org.javabubble.generator.site;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import org.javabubble.generator.model.JavaBubble;

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
				.map(p -> p.fediverse() + ",true,false,") //
				.forEach(printer::println);
		printer.flush();
	}

}
