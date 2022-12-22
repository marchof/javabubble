package org.javabubble.generator.model;

import java.util.regex.Pattern;

public final class FediverseHandle extends Handle {

	private static final Pattern HANDLE_PATTERN = Pattern.compile("[A-Za-z0-9_]+@[a-z0-9\\-]+(\\.[a-z0-9\\-]+)+");

	public FediverseHandle(String handle) {
		super(handle, HANDLE_PATTERN);
	}

	@Override
	public String getLocalHandle() {
		return getHandle().split("@")[0];
	}

	public String getServer() {
		return getHandle().split("@")[1];
	}

	@Override
	public String getWebLink() {
		return "https://%s/@%s".formatted(getServer(), getLocalHandle());
	}

}
