package com.wileyedge.exceptions;

public class InvalidInputException extends RuntimeException {
	
	public InvalidInputException() {
		super();
	}
	
	public InvalidInputException(String str) {
		super(str);
	}
	
}
