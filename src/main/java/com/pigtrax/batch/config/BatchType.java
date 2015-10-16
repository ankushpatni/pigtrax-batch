package com.pigtrax.batch.config;

import com.pigtrax.batch.drivable.PiginfoDerivable;
import com.pigtrax.batch.handler.PigInfoHandler;
import com.pigtrax.batch.mapper.PigInfoMapper;
import com.pigtrax.batch.validator.PigInfoValidator;

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
	};

	public abstract Class<?> getMapperClass();

	public abstract Class<?> getValidatorClass();

	public abstract Class<?> getHandlerClass();

	public abstract Class<?> getDriveClass();
}
