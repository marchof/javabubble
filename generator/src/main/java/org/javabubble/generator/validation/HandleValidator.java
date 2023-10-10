package org.javabubble.generator.validation;

import org.javabubble.generator.model.Handle;

interface HandleValidator<H extends Handle> {

	H validate(H handle);

}
