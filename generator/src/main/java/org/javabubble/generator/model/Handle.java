package org.javabubble.generator.model;

import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonValue;

public abstract class Handle {

	@JsonValue
	private final String handle;

	protected Handle(String handle, Pattern pattern) {
		if (!pattern.matcher(handle).matches()) {
			throw new IllegalArgumentException(
					"Unexpected value %s for %s".formatted(handle, getClass().getSimpleName()));
		}
		this.handle = handle;
	}

	public final String getHandle() {
		return handle;
	}

	public abstract String getLocalHandle();

	public abstract String getWebLink();

	@Override
	public final String toString() {
		return "%s[%s]".formatted(getClass().getSimpleName(), handle);
	}

}
