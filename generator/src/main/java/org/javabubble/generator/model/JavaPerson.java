package org.javabubble.generator.model;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public record JavaPerson(

		@JsonProperty(required = true) String name,

		@JsonInclude(Include.NON_NULL) String twitter,

		@JsonInclude(Include.NON_NULL) String fediverse,

		@JsonInclude(Include.NON_NULL) String github,

		@JsonInclude(Include.NON_NULL) String reddit

) {

	public Optional<String> twitterLink() {
		return Optional.ofNullable(twitter()) //
				.map(handle -> handle.substring(1)) //
				.map("https://twitter.com/%s"::formatted);
	}

	public Optional<String> fediverseLink() {
		return Optional.ofNullable(fediverse()) //
				.map(handle -> handle.split("@")) //
				.map("https://%3$s/@%2$s"::formatted);
	}

	public Optional<String> githubLink() {
		return Optional.ofNullable(github()) //
				.map("https://github.com/%s/"::formatted);
	}

	public Optional<String> redditLink() {
		return Optional.ofNullable(reddit()) //
				.map("https://www.reddit.com/user/%s"::formatted);
	}

}
