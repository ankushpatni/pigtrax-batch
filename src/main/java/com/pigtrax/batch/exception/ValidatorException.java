package com.pigtrax.batch.exception;

public class ValidatorException extends RuntimeException {

	private int code;

	private String message;

	public ValidatorException() {
	}

	public ValidatorException(String message) {
		super(message);
		this.message = message;
	}

	public ValidatorException(int code, String message) {
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
