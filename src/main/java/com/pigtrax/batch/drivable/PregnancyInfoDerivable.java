package com.pigtrax.batch.drivable;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pigtrax.batch.config.RefData;
import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.dao.interfaces.BreedingEventDao;
import com.pigtrax.batch.dao.interfaces.CompanyDao;
import com.pigtrax.batch.dao.interfaces.EmployeeGroupDao;
import com.pigtrax.batch.dao.interfaces.PigInfoDao;
import com.pigtrax.batch.drivable.interfaces.Derivable;
import com.pigtrax.batch.mapper.PregnancyInfoMapper;
import com.pigtrax.batch.mapper.interfaces.Mapper;
import com.pigtrax.batch.util.DateUtil;

@Component
public class PregnancyInfoDerivable implements Derivable {

	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private PigInfoDao pigInfoDao;
	
	@Autowired
	BreedingEventDao breedingEventDao;
	
	@Autowired
	EmployeeGroupDao employeeGroupDao;

	@Override
	public void derive(final List<Mapper> list, final ProcessDTO processDTO) {
		if (list != null) {
			for (Mapper mapper : list) {
				PregnancyInfoMapper pregnancyInfoMapper = (PregnancyInfoMapper) mapper;
				setExamDate(pregnancyInfoMapper);
				setResultDate(pregnancyInfoMapper);				
				setCompanyId(pregnancyInfoMapper);
				setPregnancyEventTypeId(pregnancyInfoMapper);
				setPregnancyExamResultTypeId(pregnancyInfoMapper); 
				setPigInfoId(pregnancyInfoMapper);
				setBreedingEventId(pregnancyInfoMapper);
				setEmployeeGroupId(pregnancyInfoMapper);
				setSowCondition(pregnancyInfoMapper);
				setBreedingEventId(pregnancyInfoMapper);
			}
		}
	}

	private void setExamDate(final PregnancyInfoMapper pregnancyInfoMapper) {
		try {
			pregnancyInfoMapper.setDeriveExamDate(DateUtil.getDateFromString(pregnancyInfoMapper.getExamDate()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setResultDate(final PregnancyInfoMapper pregnancyInfoMapper) {
		try {
			pregnancyInfoMapper.setDeriveResultDate(DateUtil.getDateFromString(pregnancyInfoMapper.getResultDate()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	

	private void setCompanyId(final PregnancyInfoMapper pregnancyInfoMapper) {
		try {
			pregnancyInfoMapper.setDeriveCompanyId(companyDao.getCompanyId(pregnancyInfoMapper.getCompanyId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setPigInfoId(final PregnancyInfoMapper pregnancyInfoMapper) {
		try {
			pregnancyInfoMapper.setDerivePigInfoId(pigInfoDao.getPigInfoId(pregnancyInfoMapper.getPigId(), pregnancyInfoMapper.getDeriveCompanyId()));  
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setEmployeeGroupId(final PregnancyInfoMapper pregnancyInfoMapper) {
		try {
			pregnancyInfoMapper.setDeriveEmployeeGroupId(employeeGroupDao.getEmployeeGroupPKId(pregnancyInfoMapper.getDeriveCompanyId(), pregnancyInfoMapper.getEmployeeGroup()));  
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setBreedingEventId(final PregnancyInfoMapper pregnancyInfoMapper) {
		try {
			pregnancyInfoMapper.setDeriveBreedingEventId(breedingEventDao.getBreedingEventPKId(pregnancyInfoMapper.getDerivePigInfoId(), DateUtil.getDateFromString(pregnancyInfoMapper.getServiceDate().trim())));  
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setPregnancyEventTypeId(final PregnancyInfoMapper pregnancyInfoMapper) {
		try {
			Integer pregnancyEventTypeIdFromRefData = RefData.PREGNANCYEVENTTYPE.getId(pregnancyInfoMapper.getPregnancyEventType().trim());
			if (pregnancyEventTypeIdFromRefData > -1) {
				pregnancyInfoMapper.setDerivePregnancyEventTypeId(pregnancyEventTypeIdFromRefData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setPregnancyExamResultTypeId(final PregnancyInfoMapper pregnancyInfoMapper) {
		try {
			Integer pregnancyExamResultTypeIdFromRefData = RefData.PREGNANCYEXAMRESULTTYPE.getId(pregnancyInfoMapper.getPregnancyExamResultType());
			if (pregnancyExamResultTypeIdFromRefData > -1) {
				pregnancyInfoMapper.setDerivePregnancyExamResultTypeId(pregnancyExamResultTypeIdFromRefData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setSowCondition(final PregnancyInfoMapper pregnancyInfoMapper) {
		try {
			pregnancyInfoMapper.setDeriveSowCondition(Integer.parseInt(pregnancyInfoMapper.getSowCondition()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
