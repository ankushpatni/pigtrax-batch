package com.pigtrax.batch.mapper.interfaces;

public abstract class AbstractMapper implements Mapper {

	private boolean isRecovrableErrors = true;

	public boolean isRecovrableErrors() {
		return isRecovrableErrors;
	}

	public void setRecovrableErrors(boolean isRecovrableErrors) {
		this.isRecovrableErrors = isRecovrableErrors;
	}

}
