package com.pigtrax.batch.drivable;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pigtrax.batch.config.RefData;
import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.dao.interfaces.CompanyDao;
import com.pigtrax.batch.dao.interfaces.PenDao;
import com.pigtrax.batch.dao.interfaces.PigInfoDao;
import com.pigtrax.batch.dao.interfaces.PremisesDao;
import com.pigtrax.batch.dao.interfaces.RefDataDao;
import com.pigtrax.batch.drivable.interfaces.Derivable;
import com.pigtrax.batch.mapper.BreedingEventMapper;
import com.pigtrax.batch.mapper.PigInfoMapper;
import com.pigtrax.batch.mapper.interfaces.Mapper;
import com.pigtrax.batch.util.Constants;

@Component
public class BreedingEventDerivable implements Derivable {

	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private PigInfoDao pigInfoDao;
	
	@Autowired
	PenDao penDao;
	
	@Autowired
	RefDataDao refDataDao;
	
	@Autowired
	PremisesDao premisesDao;

	@Override
	public void derive(final List<Mapper> list, final ProcessDTO processDTO) {
		if (list != null) {
			for (Mapper mapper : list) {
				BreedingEventMapper breedingEventMapper = (BreedingEventMapper) mapper;		
				if(!breedingEventMapper.isEmpty())
				{
					setCompanyId(breedingEventMapper, processDTO);
					setPremiseId(breedingEventMapper, processDTO);
					setPigInfoId(breedingEventMapper);
					setBreedingServiceTypeId(breedingEventMapper);
					setSowCondition(breedingEventMapper);
					//setPenId(breedingEventMapper);
					setWtInKgs(breedingEventMapper);
				}
			}
		}
	}

	
	private void setCompanyId(final BreedingEventMapper breedingEventMapper, ProcessDTO processDto) {
	
		try {
			breedingEventMapper.setDeriveCompanyId(processDto.getCompanyId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	
	private void setPremiseId(final BreedingEventMapper breedingEventMapper, ProcessDTO processDto) {
		try {
			breedingEventMapper.setDerivePremiseId(processDto.getPremiseId()); 
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}
	
	private void setPigInfoId(final BreedingEventMapper breedingEventMapper) {
		if(breedingEventMapper.getPigId() != null && breedingEventMapper.getDeriveCompanyId() != null && breedingEventMapper.getDerivePremiseId() != null)
		{
			try {
				breedingEventMapper.setDerivePigInfoId(pigInfoDao.getPigInfoId(breedingEventMapper.getPigId().trim(), breedingEventMapper.getDeriveCompanyId(), breedingEventMapper.getDerivePremiseId()));  
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void setBreedingServiceTypeId(final BreedingEventMapper breedingEventMapper)
	{
		if(breedingEventMapper.getServiceType() != null)
		{
			try {
				Integer breedingServiceTypeIdFromRefData = RefData.BREEDINGSERVICETYPE.getId(breedingEventMapper.getServiceType().trim());
				if (breedingServiceTypeIdFromRefData > -1) {
					breedingEventMapper.setDeriveServiceTypeId(breedingServiceTypeIdFromRefData);
				}
			} catch (Exception e) {
				e.printStackTrace(); 
			}
		}
	}
	
	private void setSowCondition(final BreedingEventMapper breedingEventMapper)
	{
		if(breedingEventMapper.getSowCondition() != null)
		{
			try{
				breedingEventMapper.setDeriveSowCondition(Integer.parseInt(breedingEventMapper.getSowCondition().trim()));
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	private void setPenId(final BreedingEventMapper breedingEventMapper)
	{
		if(breedingEventMapper.getPen() != null && !Constants.BLANK_STRING.equals(breedingEventMapper.getPen().trim()))
		{
			try {
				breedingEventMapper.setDerivePenId(penDao.getPenPKId(breedingEventMapper.getPen().trim(), breedingEventMapper.getDeriveCompanyId(),  breedingEventMapper.getDerivePremiseId()));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private void setWtInKgs(final BreedingEventMapper breedingEventMapper)
	{
		if(breedingEventMapper.getWeightInKgs() != null)
		{
			try{
				breedingEventMapper.setDeriveWtInKgs(Double.parseDouble(breedingEventMapper.getWeightInKgs().trim()));
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}

}
