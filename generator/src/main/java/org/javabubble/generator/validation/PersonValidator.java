package org.javabubble.generator.validation;

import org.javabubble.generator.model.FediverseHandle;
import org.javabubble.generator.model.GitHubHandle;
import org.javabubble.generator.model.JavaPerson;
import org.javabubble.generator.model.RedditHandle;

public class PersonValidator {

	private final HandleValidator<FediverseHandle> fediverseValidator = new LoggingValidator<>(
			new FediverseValidator());
	private final HandleValidator<GitHubHandle> githubValidator = new LoggingValidator<>(new GitHubValidator());
	private final HandleValidator<RedditHandle> redditValidator = new LoggingValidator<>(new RedditValidator());

	public JavaPerson validate(JavaPerson person) {
		return new JavaPerson(person.name(), //
				person.twitter(), //
				fediverseValidator.validate(person.fediverse()), //
				githubValidator.validate(person.github()), //
				redditValidator.validate(person.reddit()), //
				person.linkedin(), //
				person.bluesky(), //
				person.youtube(), //
				person.twitch());
	}

}
