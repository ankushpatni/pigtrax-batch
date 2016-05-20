package com.pigtrax.batch.drivable;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pigtrax.batch.beans.BreedingEvent;
import com.pigtrax.batch.beans.PregnancyInfo;
import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.dao.PenDaoImpl;
import com.pigtrax.batch.dao.interfaces.BreedingEventDao;
import com.pigtrax.batch.dao.interfaces.CompanyDao;
import com.pigtrax.batch.dao.interfaces.EmployeeGroupDao;
import com.pigtrax.batch.dao.interfaces.PigInfoDao;
import com.pigtrax.batch.dao.interfaces.PregnancyInfoDao;
import com.pigtrax.batch.dao.interfaces.PremisesDao;
import com.pigtrax.batch.drivable.interfaces.Derivable;
import com.pigtrax.batch.mapper.FarrowEventMapper;
import com.pigtrax.batch.mapper.interfaces.Mapper;
import com.pigtrax.batch.util.DateUtil;
@Component
public class FarrowEventDerivable implements Derivable {

	@Autowired
	private CompanyDao companyDao;

	@Autowired
	private PigInfoDao pigInfoDao;

	@Autowired
	private PenDaoImpl penDao;

	@Autowired
	private EmployeeGroupDao employeeGroupDao;
	
	@Autowired
	private PregnancyInfoDao pregnancyInfoDao;

	@Autowired
	BreedingEventDao breedingEventDao;
	
	@Autowired
	PremisesDao premiseDao;
	
	@Override
	public void derive(List<Mapper> list, ProcessDTO processDTO) {
		if (list != null) {
			for (Mapper mapper : list) {
				FarrowEventMapper farrowEventMapper = (FarrowEventMapper) mapper;
				if(!farrowEventMapper.isEmpty())
				{
					setPigIDPK(farrowEventMapper, processDTO);
					setServiceDate(farrowEventMapper);
					setPenId(farrowEventMapper);
					setFarrowDate(farrowEventMapper);
					setStillBorns(farrowEventMapper);
					setLiveBorns(farrowEventMapper);
					setMaleBorns(farrowEventMapper);
					setFemaleBorns(farrowEventMapper);
					setMummies(farrowEventMapper);
					setWeakBorns(farrowEventMapper);
					setweightInKGs(farrowEventMapper);
					setEmployeeGroupId(farrowEventMapper);
					setTeats(farrowEventMapper);
					setSowCondition(farrowEventMapper);
					//setPregnancyEventId(farrowEventMapper);
					setBreedingEventId(farrowEventMapper);
				}
			}
		}

	}

	private void setTeats(final FarrowEventMapper farrowEventMapper) {
		try {
			farrowEventMapper.setDeriveTeasts(Integer.parseInt(farrowEventMapper.getTeats()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setSowCondition(final FarrowEventMapper farrowEventMapper) {
		try {
			farrowEventMapper.setDeriveSowCondition(Integer.parseInt(farrowEventMapper.getSowCondition()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setPigIDPK(final FarrowEventMapper farrowEventMapper, ProcessDTO processDTO) {
		try {
			Integer derivedCompanyId = processDTO.getCompanyId();
			farrowEventMapper.setDeriveCompanyId(derivedCompanyId);
			
			Integer derivedPremiseId = processDTO.getPremiseId();
			farrowEventMapper.setDerivePremiseId(derivedPremiseId);			
		
			farrowEventMapper.setDerivePigInfoId(pigInfoDao.getPigInfoId(farrowEventMapper.getPigId(), farrowEventMapper.getDeriveCompanyId(), farrowEventMapper.getDerivePremiseId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setServiceDate(final FarrowEventMapper farrowEventMapper) {
		try {
			farrowEventMapper.setDeriveServiceDate(DateUtil.getDateFromString(farrowEventMapper.getServiceDate()));
		} catch (Exception e) {
			e.printStackTrace(); 
		}
	}

	private void setPenId(final FarrowEventMapper farrowEventMapper) {
		if (farrowEventMapper.getPenId() != null) {
			try {
				farrowEventMapper.setDerivePenId(penDao.getPenPKId(farrowEventMapper.getPenId()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void setFarrowDate(final FarrowEventMapper farrowEventMapper) {
		try {
			farrowEventMapper.setDeriveFarrowDate(DateUtil.getDateFromString(farrowEventMapper.getFarrowDate()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setStillBorns(final FarrowEventMapper farrowEventMapper) {
		try {
			farrowEventMapper.setDeriveStillBorns(Integer.parseInt(farrowEventMapper.getStillBorns()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setLiveBorns(final FarrowEventMapper farrowEventMapper) {
		try {
			farrowEventMapper.setDeriveLiveBorns(Integer.parseInt(farrowEventMapper.getLiveBorns()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setMaleBorns(final FarrowEventMapper farrowEventMapper) {
		try {
			farrowEventMapper.setDeriveMaleBorns(Integer.parseInt(farrowEventMapper.getMaleBorns()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setFemaleBorns(final FarrowEventMapper farrowEventMapper) {
		try {
			farrowEventMapper.setDeriveFemaleBorns(Integer.parseInt(farrowEventMapper.getFemaleBorns()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setMummies(final FarrowEventMapper farrowEventMapper) {
		try {
			farrowEventMapper.setDeriveMummies(Integer.parseInt(farrowEventMapper.getMummies()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setWeakBorns(final FarrowEventMapper farrowEventMapper) {
		try {
			farrowEventMapper.setDeriveWeakBorns(Integer.parseInt(farrowEventMapper.getWeakBorns()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private void setweightInKGs(final FarrowEventMapper farrowEventMapper) {
		try {
			farrowEventMapper.setDeriveWeightInKGs(Double.parseDouble(farrowEventMapper.getWeightInKGs())); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setEmployeeGroupId(final FarrowEventMapper farrowEventMapper) {
		if (farrowEventMapper.getEmployeeGrpId() != null) {
			try {
				farrowEventMapper.setDeriveEmployeeGrpId(employeeGroupDao.getEmployeeGroupPKId(farrowEventMapper.getDeriveCompanyId(),farrowEventMapper.getEmployeeGrpId()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void setPregnancyEventId(final FarrowEventMapper farrowEventMapper)
	{
		if(farrowEventMapper.getPigId() != null && farrowEventMapper.getDeriveFarrowDate() != null)
		{
			List<PregnancyInfo> pregnancyList = pregnancyInfoDao.getOpenPregnancyRecords(farrowEventMapper.getDerivePigInfoId());
			if(pregnancyList != null && 0 <pregnancyList.size())
			{
				for(PregnancyInfo pregnancyInfo :  pregnancyList)
				{
					if(pregnancyInfo != null)
					{
						DateTime serviceDate = new DateTime(breedingEventDao.getServiceStartDate(pregnancyInfo.getBreedingEventId()));
						DateTime farrowDate = new DateTime(farrowEventMapper.getDeriveFarrowDate());
						int duration = Days.daysBetween(serviceDate, farrowDate).getDays();
						if(duration >= 105 && duration <= 130)
						{
							farrowEventMapper.setPragnancyEventId(pregnancyInfo.getId());
							break;
						}
					}
				}
			}
		}
	}
	
	
	
	private void setBreedingEventId(final FarrowEventMapper farrowEventMapper)
	{
		if(farrowEventMapper.getDerivePigInfoId() != null && farrowEventMapper.getDeriveFarrowDate() != null)
		{
			//List<PregnancyEvent> pregnancyList = pregnancyEventDao.getOpenPregnancyRecords(farrowEventDto.getPigInfoId());
			List<BreedingEvent> breedingEventList = breedingEventDao.getPendingFarrowServiceRecords(farrowEventMapper.getDerivePigInfoId());
			if(breedingEventList != null && 0 <breedingEventList.size())
			{
				for(BreedingEvent breedingEventDetails :  breedingEventList)
				{
					if(breedingEventDetails != null) 
					{
						DateTime serviceDate = new DateTime(breedingEventDetails.getServiceStartDate());
						DateTime farrowDate = new DateTime(farrowEventMapper.getDeriveFarrowDate());
						int duration = Days.daysBetween(serviceDate, farrowDate).getDays();
						if(duration >= 105 && duration <= 125)
						{
							farrowEventMapper.setBreedingEventId(breedingEventDetails.getId());
							break;
						}
					}
				}
			}
			
		}
	}

}
