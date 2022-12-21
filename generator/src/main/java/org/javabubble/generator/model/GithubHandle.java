package org.javabubble.generator.model;

import java.util.regex.Pattern;

public class GithubHandle extends Handle {

	private static final Pattern HANDLE_PATTERN = Pattern.compile("[A-Za-z0-9_\\-]+");

	public GithubHandle(String handle) {
		super(handle, HANDLE_PATTERN);
	}

	@Override
	public String getLocalHandle() {
		return getHandle();
	}

	@Override
	public String getWebLink() {
		return "https://github.com/%s/".formatted(getHandle());
	}

}
