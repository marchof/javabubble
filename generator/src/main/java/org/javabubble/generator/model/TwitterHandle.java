package org.javabubble.generator.model;

import java.util.regex.Pattern;

public class TwitterHandle extends Handle {

	private static final Pattern HANDLE_PATTERN = Pattern.compile("@[A-Za-z0-9_]+");

	public TwitterHandle(String handle) {
		super(handle, HANDLE_PATTERN);
	}

	@Override
	public String getLocalHandle() {
		return getHandle().substring(1);
	}

	@Override
	public String getWebLink() {
		return "https://twitter.com/%s".formatted(getLocalHandle());
	}

}
