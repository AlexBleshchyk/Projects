package com.tsg.flooring.service;

@SuppressWarnings("serial")
public class NoOrdersException extends Exception{
	
	public NoOrdersException(String msg) {
		super(msg);
	}
	
	public NoOrdersException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
