package org.javabubble.generator.model;

import java.util.regex.Pattern;

public final class YouTubeHandle extends Handle {

	private static final Pattern HANDLE_PATTERN = Pattern.compile("[A-Za-z0-9_.-]{3,30}");

	public YouTubeHandle(String handle) {
		super(handle, HANDLE_PATTERN);
	}

	@Override
	public String getLocalHandle() {
		return getHandle();
	}

	@Override
	public String getWebLink() {
		return "https://youtube.com/@%s".formatted(getLocalHandle());
	}

}
