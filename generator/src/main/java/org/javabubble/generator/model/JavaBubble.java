package org.javabubble.generator.model;

import java.text.Collator;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public record JavaBubble(

		List<JavaPerson> people

) {

	private static final Collator COLLATOR = Collator.getInstance(Locale.ENGLISH);
	private static final Comparator<JavaPerson> ORDER = Comparator.comparing(p -> p.name().split("-|\s"),
			(a1, a2) -> Arrays.compare(a1, a2, COLLATOR));

	public JavaBubble {
		people.stream().reduce(JavaBubble::checkOrder);
	}

	private static JavaPerson checkOrder(JavaPerson a, JavaPerson b) {
		if (ORDER.compare(a, b) > 0) {
			throw new IllegalArgumentException(
					"Invalid ordering: %s should be listed before %s".formatted(b.name(), a.name()));
		}
		return b;
	}

}
