package com.pigtrax.batch.drivable;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pigtrax.batch.config.RefData;
import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.dao.interfaces.BreedingEventDao;
import com.pigtrax.batch.dao.interfaces.CompanyDao;
import com.pigtrax.batch.dao.interfaces.EmployeeGroupDao;
import com.pigtrax.batch.dao.interfaces.FarrowEventDao;
import com.pigtrax.batch.dao.interfaces.PigInfoDao;
import com.pigtrax.batch.drivable.interfaces.Derivable;
import com.pigtrax.batch.mapper.IndividualPigletStatusMapper;
import com.pigtrax.batch.mapper.PigletStatusInfoMapper;
import com.pigtrax.batch.mapper.PregnancyInfoMapper;
import com.pigtrax.batch.mapper.interfaces.Mapper;
import com.pigtrax.batch.util.DateUtil;

@Component
public class IndividualPigletStatusDerivable implements Derivable {

	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private PigInfoDao pigInfoDao;
	
	@Autowired
	FarrowEventDao farrowEventDao;

	@Override
	public void derive(final List<Mapper> list, final ProcessDTO processDTO) {
		if (list != null) {
			for (Mapper mapper : list) {
				IndividualPigletStatusMapper individualPigletMapper = (IndividualPigletStatusMapper) mapper;						
				setCompanyId(individualPigletMapper);
				setPigInfoId(individualPigletMapper);
				setFarrowEventDate(individualPigletMapper);
				setFarrowEventId(individualPigletMapper);
				setWtAtBirth(individualPigletMapper);
				setWtAtWeaning(individualPigletMapper);
			}
		}
	}

	
	private void setCompanyId(final IndividualPigletStatusMapper individualPigletMapper) {
		try {
			individualPigletMapper.setDeriveCompanyId(companyDao.getCompanyId(individualPigletMapper.getCompanyId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setPigInfoId(final IndividualPigletStatusMapper individualPigletMapper) {
		try {
			individualPigletMapper.setDerivePigInfoId(pigInfoDao.getPigInfoId(individualPigletMapper.getPigId(), individualPigletMapper.getDeriveCompanyId()));  
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setFarrowEventDate(final IndividualPigletStatusMapper individualPigletMapper) {
		try {
			individualPigletMapper.setFarrowEventDate(DateUtil.getDateFromString(individualPigletMapper.getFarrowDate()));  
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setFarrowEventId(final IndividualPigletStatusMapper individualPigletMapper)
	{
		try {
			if(individualPigletMapper.getDerivePigInfoId() != null && individualPigletMapper.getFarrowEventDate() != null)
			{
				individualPigletMapper.setDeriveFarrowEventId(farrowEventDao.getFarrowEventId(individualPigletMapper.getDerivePigInfoId(), individualPigletMapper.getFarrowEventDate()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setWtAtBirth(final IndividualPigletStatusMapper individualPigletMapper)
	{
		try {
			individualPigletMapper.setDeriveWtAtBirth(Double.parseDouble(individualPigletMapper.getWtAtBirth()));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setWtAtWeaning(final IndividualPigletStatusMapper individualPigletMapper)
	{
		try {
			individualPigletMapper.setDeriveWtAtWeaning(Double.parseDouble(individualPigletMapper.getWtAtWeaning()));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
