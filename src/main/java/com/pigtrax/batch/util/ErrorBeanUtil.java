package com.pigtrax.batch.util;

import com.pigtrax.batch.exception.ErrorBean;

public class ErrorBeanUtil {

	public static ErrorBean populateErrorBean(String code, String message, String property, boolean isRecoverable) {
		ErrorBean errBean = new ErrorBean();
		errBean.setCode(code);
		errBean.setMessage(message);
		errBean.setProperty(property);
		errBean.setRecoverable(isRecoverable);
		return errBean;
	}
}
