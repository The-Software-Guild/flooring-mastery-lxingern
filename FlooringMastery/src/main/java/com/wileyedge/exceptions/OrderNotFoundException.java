package com.wileyedge.exceptions;

public class OrderNotFoundException extends RuntimeException {

	public OrderNotFoundException() {
		super();
	}
	
	public OrderNotFoundException(String str) {
		super(str);
	}
	
}
