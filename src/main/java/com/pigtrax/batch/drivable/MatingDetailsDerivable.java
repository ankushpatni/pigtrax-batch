package com.pigtrax.batch.drivable;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.dao.interfaces.BreedingEventDao;
import com.pigtrax.batch.dao.interfaces.CompanyDao;
import com.pigtrax.batch.dao.interfaces.EmployeeGroupDao;
import com.pigtrax.batch.dao.interfaces.PigInfoDao;
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

	@Override
	public void derive(final List<Mapper> list, final ProcessDTO processDTO) {
		if (list != null) {
			for (Mapper mapper : list) {
				MatingDetailsMapper matingDetailsMapper = (MatingDetailsMapper) mapper;						
				setCompanyId(matingDetailsMapper);
				setPigInfoId(matingDetailsMapper);
				setMatingDate(matingDetailsMapper);
				setEmployeeGroupId(matingDetailsMapper);
				setMateQuality(matingDetailsMapper);
				setBreedingEventId(matingDetailsMapper);
			}
		}
	}

	
	private void setCompanyId(final MatingDetailsMapper matingDetailsMapper) {
		if(matingDetailsMapper.getCompanyId() != null)
		{
			try {
				matingDetailsMapper.setDeriveCompanyId(companyDao.getCompanyId(matingDetailsMapper.getCompanyId().trim()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void setPigInfoId(final MatingDetailsMapper matingDetailsMapper) {
		if(matingDetailsMapper.getPigId() != null && matingDetailsMapper.getDeriveCompanyId() != null)
		{
			try {
				matingDetailsMapper.setDerivePigInfoId(pigInfoDao.getPigInfoId(matingDetailsMapper.getPigId().trim(), matingDetailsMapper.getDeriveCompanyId()));  
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
				matingDetailsMapper.setDeriveMateQuality(Integer.parseInt(matingDetailsMapper.getMateQuality()));  
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void setBreedingEventId(final MatingDetailsMapper matingDetailsMapper) {
		matingDetailsMapper.setDeriveBreedingEventId(breedingEventDao.getLatestServiceEventId(matingDetailsMapper.getDerivePigInfoId()));
	}

}
