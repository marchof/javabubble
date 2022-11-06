package org.javabubble.generator.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public record JavaPerson(

		@JsonProperty(required = true) String name,

		@JsonInclude(Include.NON_NULL) String twitter,

		@JsonInclude(Include.NON_NULL) String fediverse,

		@JsonInclude(Include.NON_NULL) String github

) {
}
