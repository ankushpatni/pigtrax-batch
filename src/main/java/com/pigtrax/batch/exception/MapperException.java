package com.pigtrax.batch.exception;

public class MapperException extends RuntimeException {
	private int code;

	private String message;

	public MapperException() {
	}

	public MapperException(String message) {
		super(message);
		this.message = message;
	}

	public MapperException(int code, String message) {
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
