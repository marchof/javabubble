package org.javabubble.generator.validation;

import org.javabubble.generator.model.Handle;

interface HandleValidator<H extends Handle> {

	public abstract H validate(H handle);

}
