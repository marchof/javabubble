package org.javabubble.generator.model;

import static java.util.function.Predicate.not;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public record JavaPerson(

		@JsonProperty(required = true) String name,

		@JsonInclude(Include.NON_NULL) TwitterHandle twitter,

		@JsonInclude(Include.NON_NULL) FediverseHandle fediverse,

		@JsonInclude(Include.NON_NULL) GitHubHandle github,

		@JsonInclude(Include.NON_NULL) RedditHandle reddit,

		@JsonInclude(Include.NON_NULL) LinkedInHandle linkedin,

		@JsonInclude(Include.NON_NULL) BlueskyHandle bluesky,

		@JsonInclude(Include.NON_NULL) YouTubeHandle youtube,

		@JsonInclude(Include.NON_NULL) TwitchHandle twitch

) {

	public JavaPerson {
		if (name == null || name.isBlank()) {
			throw new IllegalArgumentException("Field name must not be empty");
		}
	}

	@JsonIgnore
	public List<String> getUniqueHandles() {
		return Stream.of(twitter, fediverse, github, reddit, bluesky, youtube, twitch) //
				.filter(not(Objects::isNull)) //
				.map(Handle::getLocalHandle) //
				.distinct() //
				.sorted() //
				.toList();
	}

}
