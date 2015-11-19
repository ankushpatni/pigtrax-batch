package com.pigtrax.batch.drivable;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pigtrax.batch.beans.BreedingEvent;
import com.pigtrax.batch.config.RefData;
import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.dao.interfaces.BreedingEventDao;
import com.pigtrax.batch.dao.interfaces.CompanyDao;
import com.pigtrax.batch.dao.interfaces.EmployeeGroupDao;
import com.pigtrax.batch.dao.interfaces.PigInfoDao;
import com.pigtrax.batch.drivable.interfaces.Derivable;
import com.pigtrax.batch.mapper.PregnancyInfoMapper;
import com.pigtrax.batch.mapper.interfaces.Mapper;
import com.pigtrax.batch.util.Constants;
import com.pigtrax.batch.util.DateUtil;
import com.pigtrax.batch.util.ErrorBeanUtil;

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
			if(pregnancyInfoMapper.getDeriveResultDate() != null)
			{
				DateTime pregnancyResultDate = new DateTime(pregnancyInfoMapper.getDeriveResultDate());
				Integer eventTypeId = pregnancyInfoMapper.getDerivePregnancyEventTypeId();
				
				List<BreedingEvent> breedingEventList = breedingEventDao.getOpenServiceRecords(pregnancyInfoMapper.getDerivePigInfoId());
				
				if(breedingEventList != null && 0<breedingEventList.size())
				{
					for(BreedingEvent breedingEvent : breedingEventList)
					{
						DateTime serviceDate = new DateTime(breedingEvent.getServiceStartDate());
						int durationDays = Days.daysBetween(serviceDate, pregnancyResultDate).getDays();
						
						
						if (eventTypeId == 1 && durationDays >= 18 && durationDays <= 60) {
							pregnancyInfoMapper.setDeriveBreedingEventId(breedingEvent.getId());
							break;
						}
						else if (eventTypeId == 2 && durationDays >= 18 && durationDays >= 110) {
							pregnancyInfoMapper.setDeriveBreedingEventId(breedingEvent.getId());
							break;
						}
						else if (eventTypeId == 3 && durationDays >= 105 && durationDays <= 125) {
							pregnancyInfoMapper.setDeriveBreedingEventId(breedingEvent.getId());
							break;
						} 
					}
				}
				
			}
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
