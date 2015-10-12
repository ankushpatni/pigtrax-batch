package com.pigtrax.batch.exception;

public class ErrorBean {

	private String code;

	private String property;

	private String message;

	private boolean isRecoverable;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isRecoverable() {
		return isRecoverable;
	}

	public void setRecoverable(boolean isRecoverable) {
		this.isRecoverable = isRecoverable;
	}

}
