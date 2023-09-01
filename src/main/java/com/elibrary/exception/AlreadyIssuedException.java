package com.elibrary.exception;

public class AlreadyIssuedException extends RuntimeException {

	public AlreadyIssuedException() {
		super();
	}

	public AlreadyIssuedException(String message) {
		super(message);

	}

}
