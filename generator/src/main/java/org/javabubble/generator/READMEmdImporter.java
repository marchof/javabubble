package org.javabubble.generator;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.javabubble.generator.model.JavaPerson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

/**
 * Temporary converter for the list in README.md to javapeople.yaml
 */
public class READMEmdImporter {

	public static void main(String... args) throws IOException {
		try (var in = Files.newBufferedReader(Path.of("../README.md"), UTF_8);
				var out = Files.newBufferedWriter(Path.of("../javapeople.yaml"), UTF_8)) {

			out.write("# Please add new entries in alphabetic order of the name.\n");

			var pattern = Pattern.compile("(.*)\\|(.*)\\|(.*)");
			var javaPeople = in.lines() //
					.map(pattern::matcher) //
					.filter(Matcher::matches) //
					.skip(2) //
					.map(m -> new JavaPerson(m.group(3).strip(), m.group(1).strip(), m.group(2).strip(), null)) //
					.sorted(Comparator.comparing(JavaPerson::name, String::compareToIgnoreCase)) //
					.toList();
			new ObjectMapper(new YAMLFactory()).writeValue(out, javaPeople);
		}
	}
}
