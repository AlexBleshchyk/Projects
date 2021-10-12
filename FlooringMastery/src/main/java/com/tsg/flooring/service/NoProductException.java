package com.tsg.flooring.service;

@SuppressWarnings("serial")
public class NoProductException extends Exception {
	public NoProductException(String msg) {
		super(msg);
	}
	
	public NoProductException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
