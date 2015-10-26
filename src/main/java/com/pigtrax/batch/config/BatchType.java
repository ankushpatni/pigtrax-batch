package com.pigtrax.batch.config;

import com.pigtrax.batch.drivable.PiginfoDerivable;
import com.pigtrax.batch.handler.PigInfoHandler;
import com.pigtrax.batch.mapper.PigInfoMapper;
import com.pigtrax.batch.validator.PigInfoValidator;
import com.pigtrax.batch.drivable.PregnancyInfoDerivable;
import com.pigtrax.batch.handler.PregnancyInfoHandler;
import com.pigtrax.batch.mapper.PregnancyInfoMapper;
import com.pigtrax.batch.validator.PregnancyInfoValidator;

public enum BatchType {
	PIGINFO {
		@Override
		public Class<?> getMapperClass() {
			return PigInfoMapper.class;
		}

		@Override
		public Class<?> getValidatorClass() {
			return PigInfoValidator.class;
		}

		@Override
		public Class<?> getHandlerClass() {
			return PigInfoHandler.class;
		}

		@Override
		public Class<?> getDriveClass() {
			return PiginfoDerivable.class;
		}
	},
	PREGNANCYINFO {
		@Override
		public Class<?> getMapperClass() {
			return PregnancyInfoMapper.class;
		}

		@Override
		public Class<?> getValidatorClass() {
			return PregnancyInfoValidator.class;
		}

		@Override
		public Class<?> getHandlerClass() {
			return PregnancyInfoHandler.class;
		}

		@Override
		public Class<?> getDriveClass() {
			return PregnancyInfoDerivable.class;
		}
	};
	
	public abstract Class<?> getMapperClass();

	public abstract Class<?> getValidatorClass();

	public abstract Class<?> getHandlerClass();

	public abstract Class<?> getDriveClass();
}
