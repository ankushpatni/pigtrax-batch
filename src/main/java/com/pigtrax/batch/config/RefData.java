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
	},
	
	PREGNANCYEVENTTYPE {
		@Override
		public Integer getId(String value) {
			return RefDataCache.getInstance().getRefDataId(value, Constants.REF_DATA_PREGNANCYEVENTTYPE);
		}
	},	
	
	PREGNANCYEXAMRESULTTYPE {
		@Override
		public Integer getId(String value) {
			return RefDataCache.getInstance().getRefDataId(value, Constants.REF_DATA_PREGNANCYEXAMRESULTTYPE);
		}
	},
	
	MORTALITYREASONTYPE {
		@Override
		public Integer getId(String value) {
			return RefDataCache.getInstance().getRefDataId(value, Constants.REF_DATA_MORTALITYREASONTYPE);
		}
	},
	PIGLETSTATUSEVENTTYPE {
		@Override
		public Integer getId(String value) {
			return RefDataCache.getInstance().getRefDataId(value, Constants.REF_DATA_PIGLETSTATUSEVENTTYPE);
		}
	},
	PHASEOFPRODUCTIONTYPE {
		@Override
		public Integer getId(String value) {
			return RefDataCache.getInstance().getRefDataId(value, Constants.REF_DATA_PHASEOFPRODUCTIONTYPE);
		}
	},

	BREEDINGSERVICETYPE {
		@Override
		public Integer getId(String value) {
			return RefDataCache.getInstance().getRefDataId(value, Constants.REF_DATA_BREEDINGSERIVCETYPE);
		}
	},

	REMOVALEVENTTYPE {
		@Override
		public Integer getId(String value) {
			return RefDataCache.getInstance().getRefDataId(value, Constants.REF_DATA_REMOVALEVENTTYPE);
		}
	},
	GCOMPANY {
		@Override
		public Integer getId(String value) {
			return RefDataCache.getInstance().getRefDataId(value, "GCOMPANY");
		}
	},
	GLINE {
		@Override
		public Integer getId(String value) {
			return RefDataCache.getInstance().getRefDataId(value, "GLINE");
		}
	}	
	;
	
	

	public abstract Integer getId(String value);

}
