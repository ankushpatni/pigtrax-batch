package com.pigtrax.batch.mapper.interfaces;

public abstract class AbstractMapper implements Mapper {

	private Boolean isRecovrableErrors;

	public Boolean isRecovrableErrors() {
		return isRecovrableErrors;
	}

	public void setRecovrableErrors(Boolean isRecovrableErrors) {
		this.isRecovrableErrors = isRecovrableErrors;
	}

}
