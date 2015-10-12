package com.pigtrax.batch.config;

import com.pigtrax.batch.util.Constants;

public enum RefData {
	SEX {
		@Override
		public Integer getId(String value) {
			return RefDataCache.getInstance().getRefDataId(value, Constants.REF_DATA_SEX);
		}
	},
	GFUNCTION {
		@Override
		public Integer getId(String value) {
			return RefDataCache.getInstance().getRefDataId(value, Constants.REF_DATA_GFUNCTION);
		}
	};

	public abstract Integer getId(String value);

}
