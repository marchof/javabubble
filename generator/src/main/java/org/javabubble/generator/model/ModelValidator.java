package org.javabubble.generator.model;

import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

class ModelValidator {

	private static final Pattern TWITTER_PATTERN = Pattern.compile("@[A-Za-z0-9_]+");
	private static final Pattern FEDIVERSE_PATTERN = Pattern.compile("@[A-Za-z0-9_]+@[a-z0-9\\-]+(\\.[a-z0-9\\-]+)+");
	private static final Pattern GITHUB_PATTERN = Pattern.compile("[A-Za-z0-9_\\-]+");

	private static final Comparator<JavaPerson> ORDER = Comparator.comparing(JavaPerson::name,
			String::compareToIgnoreCase);

	static void validate(JavaBubble bubble) {
		validate(bubble.people());
	}

	private static void validate(List<JavaPerson> people) {
		people.forEach(ModelValidator::validate);
		people.stream().reduce(ModelValidator::checkOrder);
	}

	private static void validate(JavaPerson person) {
		checkNonEmpty("name", person.name());
		checkPattern("twitter", TWITTER_PATTERN, person.twitter());
		checkPattern("fediverse", FEDIVERSE_PATTERN, person.fediverse());
		checkPattern("github", GITHUB_PATTERN, person.github());
	}

	private static void checkNonEmpty(String field, String value) {
		if (value == null || value.isBlank()) {
			throw new IllegalArgumentException("Field %s must not be empty".formatted(field));
		}
	}

	private static void checkPattern(String field, Pattern pattern, String value) {
		if (value != null && !pattern.matcher(value).matches()) {
			throw new IllegalArgumentException("Field %s hast unexpected value %s".formatted(field, value));
		}
	}

	private static JavaPerson checkOrder(JavaPerson a, JavaPerson b) {
		if (ORDER.compare(a, b) > 0) {
			throw new IllegalArgumentException(
					"Invalid ordering: %s should be listed before %s".formatted(b.name(), a.name()));
		}
		return b;
	}

}
