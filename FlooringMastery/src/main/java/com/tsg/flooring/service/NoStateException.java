package com.tsg.flooring.service;

@SuppressWarnings("serial")
public class NoStateException extends Exception{
	
	public NoStateException(String msg) {
		super(msg);
	}
	
	public NoStateException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
}
