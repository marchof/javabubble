package org.javabubble.generator.model;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.UNICODE_CHARACTER_CLASS;

public final class LinkedInHandle extends Handle {

	private static final Pattern HANDLE_PATTERN = Pattern.compile("[\\w\\-]+", UNICODE_CHARACTER_CLASS);

	public LinkedInHandle(String handle) {
		super(handle, HANDLE_PATTERN);
	}

	@Override
	public String getLocalHandle() {
		return getHandle();
	}

	@Override
	public String getWebLink() {
		return "https://www.linkedin.com/in/%s/".formatted(getHandle());
	}

}
