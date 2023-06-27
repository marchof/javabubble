package org.javabubble.generator.model;

import java.util.regex.Pattern;

public final class BlueskyHandle extends Handle {
    private static final Pattern HANDLE_PATTERN = Pattern.compile("[A-Za-z0-9.-]+");

    public BlueskyHandle(String handle) {
        super(handle, HANDLE_PATTERN);
    }

    @Override
    public String getLocalHandle() {
        return getHandle();
    }

    @Override
    public String getWebLink() {
        return "https://bsky.app/profile/%s".formatted(getLocalHandle());
    }
}
