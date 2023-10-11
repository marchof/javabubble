package org.javabubble.generator.model;

import java.util.regex.Pattern;

public final class TwitchHandle extends Handle {

	private static final Pattern HANDLE_PATTERN = Pattern.compile("[A-Za-z0-9_]{4,25}");

	public TwitchHandle(String handle) {
		super(handle, HANDLE_PATTERN);
	}

	@Override
	public String getLocalHandle() {
		return getHandle();
	}

	@Override
	public String getWebLink() {
		return "https://twitch.tv/%s".formatted(getLocalHandle());
	}

}
