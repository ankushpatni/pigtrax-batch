package com.pigtrax.batch.config;

import com.pigtrax.batch.drivable.BreedingEventDerivable;
import com.pigtrax.batch.drivable.FarrowEventDerivable;
import com.pigtrax.batch.drivable.FeedDetailEventDerivable;
import com.pigtrax.batch.drivable.FeedEventDerivable;
import com.pigtrax.batch.drivable.GroupEventDetailDerivable;
import com.pigtrax.batch.drivable.GroupEventInfoDerivable;
import com.pigtrax.batch.drivable.IndividualPigletStatusDerivable;
import com.pigtrax.batch.drivable.MatingDetailsDerivable;
import com.pigtrax.batch.drivable.PiginfoDerivable;
import com.pigtrax.batch.drivable.PigletStatusInfoDerivable;
import com.pigtrax.batch.drivable.PregnancyInfoDerivable;
import com.pigtrax.batch.drivable.RemovalEventExceptSalesDetailsDerivable;
import com.pigtrax.batch.drivable.SalesEventDetailsDrivable;
import com.pigtrax.batch.handler.BreedingEventHandler;
import com.pigtrax.batch.handler.FarrowEventHandler;
import com.pigtrax.batch.handler.FeedDetailEventHandler;
import com.pigtrax.batch.handler.FeedEventHandler;
import com.pigtrax.batch.handler.GroupEventDetailHandler;
import com.pigtrax.batch.handler.GroupEventInfoHandler;
import com.pigtrax.batch.handler.IndividualPigletStatusHandler;
import com.pigtrax.batch.handler.MatingDetailsHandler;
import com.pigtrax.batch.handler.PigInfoHandler;
import com.pigtrax.batch.handler.PigletStatusInfoHandler;
import com.pigtrax.batch.handler.PregnancyInfoHandler;
import com.pigtrax.batch.handler.RemovalEventExceptSalesDetailsHandler;
import com.pigtrax.batch.handler.SalesEventDetailsHandler;
import com.pigtrax.batch.mapper.BreedingEventMapper;
import com.pigtrax.batch.mapper.FarrowEventMapper;
import com.pigtrax.batch.mapper.FeedDetailEventMapper;
import com.pigtrax.batch.mapper.FeedEventMapper;
import com.pigtrax.batch.mapper.GroupEventDetailMapper;
import com.pigtrax.batch.mapper.GroupEventInfoMapper;
import com.pigtrax.batch.mapper.IndividualPigletStatusMapper;
import com.pigtrax.batch.mapper.MatingDetailsMapper;
import com.pigtrax.batch.mapper.PigInfoMapper;
import com.pigtrax.batch.mapper.PigletStatusInfoMapper;
import com.pigtrax.batch.mapper.PregnancyInfoMapper;
import com.pigtrax.batch.mapper.RemovalEventExceptSalesDetailsMapper;
import com.pigtrax.batch.mapper.SalesEventDetailsMapper;
import com.pigtrax.batch.validator.BreedingEventValidator;
import com.pigtrax.batch.validator.FarrowEventValidator;
import com.pigtrax.batch.validator.FeedDetailEventValidator;
import com.pigtrax.batch.validator.FeedEventValidator;
import com.pigtrax.batch.validator.GroupEventDetailValidator;
import com.pigtrax.batch.validator.GroupEventInfoValidator;
import com.pigtrax.batch.validator.IndividualPigletStatusValidator;
import com.pigtrax.batch.validator.MatingDetailsValidator;
import com.pigtrax.batch.validator.PigInfoValidator;
import com.pigtrax.batch.validator.PigletStatusInfoValidator;
import com.pigtrax.batch.validator.PregnancyInfoValidator;
import com.pigtrax.batch.validator.RemovalEventExceptSalesDetailsValidator;
import com.pigtrax.batch.validator.SalesEventDetailsValidator;


public enum BatchType {
	ENTRYEVENT {
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
	PREGNANCYEVENT {
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
	},

	PIGLETSTATUSEVENT {
		@Override
		public Class<?> getMapperClass() {
			return PigletStatusInfoMapper.class;
		}

		@Override
		public Class<?> getValidatorClass() {
			return PigletStatusInfoValidator.class;
		}

		@Override
		public Class<?> getHandlerClass() {
			return PigletStatusInfoHandler.class;
		}

		@Override
		public Class<?> getDriveClass() {
			return PigletStatusInfoDerivable.class;
		}
	},
	FARROWEVENT {
		@Override
		public Class<?> getMapperClass() {
			return FarrowEventMapper.class;
		}

		@Override
		public Class<?> getValidatorClass() {
			return FarrowEventValidator.class;
		}

		@Override
		public Class<?> getHandlerClass() {
			return FarrowEventHandler.class;
		}

		@Override
		public Class<?> getDriveClass() {
			return FarrowEventDerivable.class;
		}
	},
	GROUPEVENT{
		@Override
		public Class<?> getMapperClass() {
			return GroupEventInfoMapper.class;
		}

		@Override
		public Class<?> getValidatorClass() {
			return GroupEventInfoValidator.class;
		}

		@Override
		public Class<?> getHandlerClass() {
			return GroupEventInfoHandler.class;
		}

		@Override
		public Class<?> getDriveClass() {
			return GroupEventInfoDerivable.class;
		}
	},
	FEEDEVENT{
		@Override
		public Class<?> getMapperClass() {
			return FeedEventMapper.class;
		}

		@Override
		public Class<?> getValidatorClass() {
			return FeedEventValidator.class;
		}

		@Override
		public Class<?> getHandlerClass() {
			return FeedEventHandler.class;
		}

		@Override
		public Class<?> getDriveClass() {
			return FeedEventDerivable.class;
		}
	},
	FEEDEVENTDETAILS{
		@Override
		public Class<?> getMapperClass() {
			return FeedDetailEventMapper.class;
		}

		@Override
		public Class<?> getValidatorClass() {
			return FeedDetailEventValidator.class;
		}

		@Override
		public Class<?> getHandlerClass() {
			return FeedDetailEventHandler.class;
		}

		@Override
		public Class<?> getDriveClass() {
			return FeedDetailEventDerivable.class;
		}
	},
	GROUPEVENTDETAILS{
		@Override
		public Class<?> getMapperClass() {
			return GroupEventDetailMapper.class;
		}

		@Override
		public Class<?> getValidatorClass() {
			return GroupEventDetailValidator.class;
		}

		@Override
		public Class<?> getHandlerClass() {
			return GroupEventDetailHandler.class;
		}

		@Override
		public Class<?> getDriveClass() {
			return GroupEventDetailDerivable.class;
		}
	},
	INDIVIDUALPIGLET{
		@Override
		public Class<?> getMapperClass() {
			return IndividualPigletStatusMapper.class;
		}

		@Override
		public Class<?> getValidatorClass() {
			return IndividualPigletStatusValidator.class;
		}

		@Override
		public Class<?> getHandlerClass() {
			return IndividualPigletStatusHandler.class;
		}

		@Override
		public Class<?> getDriveClass() {
			return IndividualPigletStatusDerivable.class;
		}
	},
	BREEDINGEVENT{
		@Override
		public Class<?> getMapperClass() {
			return BreedingEventMapper.class;
		}
		@Override
		public Class<?> getValidatorClass() {
			return BreedingEventValidator.class;
		}
		
		@Override
		public Class<?> getHandlerClass() {
			return BreedingEventHandler.class;
		}
		
		@Override
		public Class<?> getDriveClass() {
			return BreedingEventDerivable.class;
		}
	},
		
	MORTALITYANDADJUSTMENT{
		@Override
		public Class<?> getMapperClass() {
			return RemovalEventExceptSalesDetailsMapper.class;
		}

		@Override
		public Class<?> getValidatorClass() {
			return RemovalEventExceptSalesDetailsValidator.class;
		}

		@Override
		public Class<?> getHandlerClass() {
			return RemovalEventExceptSalesDetailsHandler.class;
		}

		@Override
		public Class<?> getDriveClass() {
			return RemovalEventExceptSalesDetailsDerivable.class;
		}
	},
	SALES{
		@Override
		public Class<?> getMapperClass() {
			return SalesEventDetailsMapper.class;
		}
			
		@Override
		public Class<?> getValidatorClass() {
			return SalesEventDetailsValidator.class;
		}

		@Override
		public Class<?> getHandlerClass() {
			return SalesEventDetailsHandler.class;
		}

		@Override
		public Class<?> getDriveClass() {
			return SalesEventDetailsDrivable.class;
		}
	},
			
	MATINGDETAILS{
		@Override
		public Class<?> getMapperClass() {
			return MatingDetailsMapper.class;
		}
		
		@Override
		public Class<?> getValidatorClass() {
			return MatingDetailsValidator.class;
		}

		@Override
		public Class<?> getHandlerClass() {
			return MatingDetailsHandler.class;
		}

		@Override
		public Class<?> getDriveClass() {
			return MatingDetailsDerivable.class;
		}
	}
	;

	public abstract Class<?> getMapperClass();

	public abstract Class<?> getValidatorClass();

	public abstract Class<?> getHandlerClass();

	public abstract Class<?> getDriveClass();
}
