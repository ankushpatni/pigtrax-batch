package com.pigtrax.batch.exception;

public class HandlerException extends RuntimeException {
	private int code;

	private String message;

	public HandlerException() {
	}

	public HandlerException(String message) {
		super(message);
		this.message = message;
	}

	public HandlerException(int code, String message) {
		this.message = message;
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
