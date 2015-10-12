package com.pigtrax.batch.core;

import com.pigtrax.batch.config.BatchType;

public class ProcessDTO {

	private boolean hasHeaders;

	private BatchType batchType;

	private String dataSrc;

	private String seperator;

	private String fileType;

	private String userName;

	public boolean hasHeaders() {
		return hasHeaders;
	}

	public void setHasHeaders(boolean hasHeaders) {
		this.hasHeaders = hasHeaders;
	}

	public BatchType getBatchType() {
		return batchType;
	}

	public void setBatchType(BatchType batchType) {
		this.batchType = batchType;
	}

	public String getDataSrc() {
		return dataSrc;
	}

	public void setDataSrc(String dataSrc) {
		this.dataSrc = dataSrc;
	}

	public String getSeperator() {
		return seperator;
	}

	public void setSeperator(String seperator) {
		this.seperator = seperator;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public boolean isHasHeaders() {
		return hasHeaders;
	}

}
