package com.pigtrax.batch.beans;

public class RefDataTranslation {

	private final String fieldLanguage;

	private final String fieldValue;

	private final Integer fieldCode;

	public RefDataTranslation(String fieldLanguage, String fieldValue, Integer fieldCode) {
		super();
		this.fieldLanguage = fieldLanguage;
		this.fieldValue = fieldValue;
		this.fieldCode = fieldCode;
	}

	public String getFieldLanguage() {
		return fieldLanguage;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public Integer getFieldCode() {
		return fieldCode;
	}

	@Override
	public String toString() {
		return "UserRole [fieldLanguage=" + fieldLanguage + ", fieldValue=" + fieldValue + ", fieldCode=" + fieldCode + "]";
	}

}
