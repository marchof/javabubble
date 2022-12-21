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

		@JsonInclude(Include.NON_NULL) GithubHandle github,

		@JsonInclude(Include.NON_NULL) RedditHandle reddit

) {

	public JavaPerson {
		if (name == null || name.isBlank()) {
			throw new IllegalArgumentException("Field name must not be empty");
		}
	}

	@JsonIgnore
	public List<String> getUniqueHandles() {
		return Stream.of(twitter, fediverse, github, reddit) //
				.filter(not(Objects::isNull)) //
				.map(Handle::getLocalHandle) //
				.distinct() //
				.sorted() //
				.toList();
	}

}
