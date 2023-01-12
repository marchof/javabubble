package org.javabubble.generator.model;

import java.util.regex.Pattern;

public final class LinkedInHandle extends Handle {

	private static final Pattern HANDLE_PATTERN = Pattern.compile("[A-Za-z0-9_\\-]+");

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
