package org.javabubble.generator.validation;

import org.javabubble.generator.model.Handle;

class LoggingValidator<H extends Handle> implements HandleValidator<H> {

	private final HandleValidator<H> delegate;

	LoggingValidator(HandleValidator<H> delegate) {
		this.delegate = delegate;
	}

	@Override
	public H validate(H handle) {
		if (handle == null) {
			return null;
		}
		System.out.print(handle + " -> ");
		System.out.flush();
		H validated = delegate.validate(handle);
		if (validated == null) {
			System.out.println("does not exist");
		} else {
			if (validated.getHandle().equals(handle.getHandle())) {
				System.out.println("ok");
			} else {
				System.out.println(validated);
			}
		}
		return validated;
	}

}
