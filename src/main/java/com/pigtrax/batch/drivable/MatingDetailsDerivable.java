package com.pigtrax.batch.drivable;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.dao.interfaces.BreedingEventDao;
import com.pigtrax.batch.dao.interfaces.CompanyDao;
import com.pigtrax.batch.dao.interfaces.EmployeeGroupDao;
import com.pigtrax.batch.dao.interfaces.PigInfoDao;
import com.pigtrax.batch.dao.interfaces.PremisesDao;
import com.pigtrax.batch.drivable.interfaces.Derivable;
import com.pigtrax.batch.mapper.MatingDetailsMapper;
import com.pigtrax.batch.mapper.interfaces.Mapper;
import com.pigtrax.batch.util.DateUtil;

@Component
public class MatingDetailsDerivable implements Derivable {

	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private PigInfoDao pigInfoDao;
	
	@Autowired
	EmployeeGroupDao employeeGroupDao;
	
	@Autowired
	BreedingEventDao breedingEventDao;
	
	@Autowired
	PremisesDao premisesDao;

	@Override
	public void derive(final List<Mapper> list, final ProcessDTO processDTO) {
		if (list != null) {
			for (Mapper mapper : list) {
				MatingDetailsMapper matingDetailsMapper = (MatingDetailsMapper) mapper;						
				setCompanyId(matingDetailsMapper, processDTO);
				setPremiseId(matingDetailsMapper, processDTO);
				setPigInfoId(matingDetailsMapper);
				setMatingDate(matingDetailsMapper);
				setSemenDate(matingDetailsMapper);
				setEmployeeGroupId(matingDetailsMapper);
				setMateQuality(matingDetailsMapper);
				setBreedingEventId(matingDetailsMapper);
			}
		}
	}

	
	private void setCompanyId(final MatingDetailsMapper matingDetailsMapper, ProcessDTO processDTO) {
			try {
				matingDetailsMapper.setDeriveCompanyId(processDTO.getCompanyId());
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	private void setPremiseId(final MatingDetailsMapper matingDetailsMapper, ProcessDTO processDto) {
		try {
			matingDetailsMapper.setDerivePremiseId(processDto.getPremiseId()); 
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}
	
	private void setPigInfoId(final MatingDetailsMapper matingDetailsMapper) {
		if(matingDetailsMapper.getPigId() != null && matingDetailsMapper.getDeriveCompanyId() != null && matingDetailsMapper.getDerivePremiseId() != null)
		{
			try {
				matingDetailsMapper.setDerivePigInfoId(pigInfoDao.getPigInfoId(matingDetailsMapper.getPigId().trim(), matingDetailsMapper.getDeriveCompanyId(), matingDetailsMapper.getDerivePremiseId()));  
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void setMatingDate(final MatingDetailsMapper matingDetailsMapper) {
		if(matingDetailsMapper.getMatingDate() != null)
		{
			try {
				matingDetailsMapper.setDeriveMatingDate(DateUtil.getDateFromString(matingDetailsMapper.getMatingDate()));  
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void setSemenDate(final MatingDetailsMapper matingDetailsMapper) {
		if(matingDetailsMapper.getSemenDate() != null)
		{
			try {
				matingDetailsMapper.setDeriveSemenDate(DateUtil.getDateFromString(matingDetailsMapper.getSemenDate()));  
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void setEmployeeGroupId(final MatingDetailsMapper matingDetailsMapper) {
		if(matingDetailsMapper.getEmployeeGroup() != null)
		{
			try {
				matingDetailsMapper.setDeriveEmployeeGroupId(employeeGroupDao.getEmployeeGroupPKId(matingDetailsMapper.getDeriveCompanyId(), matingDetailsMapper.getEmployeeGroup()));  
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	private void setMateQuality(final MatingDetailsMapper matingDetailsMapper) {
		if(matingDetailsMapper.getMateQuality() != null)
		{
			try {
				String matingQuality = matingDetailsMapper.getMateQuality();
				if(matingQuality== null) matingQuality = "";
				int derivedQuality = 0;
				if("Good".equalsIgnoreCase(matingDetailsMapper.getMateQuality()))
						derivedQuality = 1;
				else if("OK".equalsIgnoreCase(matingDetailsMapper.getMateQuality()))
					derivedQuality = 2;
				else if("poor".equalsIgnoreCase(matingDetailsMapper.getMateQuality()))
					derivedQuality = 3;
				matingDetailsMapper.setDeriveMateQuality(derivedQuality);  
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void setBreedingEventId(final MatingDetailsMapper matingDetailsMapper) {
		matingDetailsMapper.setDeriveBreedingEventId(breedingEventDao.getLatestServiceEventId(matingDetailsMapper.getDerivePigInfoId()));
	}

}
