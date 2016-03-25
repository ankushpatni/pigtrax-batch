package com.pigtrax.batch.drivable;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.dao.interfaces.CompanyDao;
import com.pigtrax.batch.dao.interfaces.FarrowEventDao;
import com.pigtrax.batch.dao.interfaces.PigInfoDao;
import com.pigtrax.batch.dao.interfaces.PremisesDao;
import com.pigtrax.batch.drivable.interfaces.Derivable;
import com.pigtrax.batch.mapper.IndividualPigletStatusMapper;
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
	
	@Autowired
	PremisesDao premiseDao;

	@Override
	public void derive(final List<Mapper> list, final ProcessDTO processDTO) {
		if (list != null) {
			for (Mapper mapper : list) {
				IndividualPigletStatusMapper individualPigletMapper = (IndividualPigletStatusMapper) mapper;
				if(!individualPigletMapper.isEmpty())
				{
					setCompanyId(individualPigletMapper, processDTO);
					setPremiseId(individualPigletMapper, processDTO);
					setLitterId(individualPigletMapper);
					setWtAtBirth(individualPigletMapper);
					setWtAtWeaning(individualPigletMapper);
					setWtAtFirstMonth(individualPigletMapper);
					setWtAtSecondMonth(individualPigletMapper);
					setWtAtThirdMonth(individualPigletMapper);
					setWtAtFourthMonth(individualPigletMapper);
					setWtAtFifthMonth(individualPigletMapper);
					setWtAtSixthMonth(individualPigletMapper);
					setFirstDate(individualPigletMapper);
					setSecondDate(individualPigletMapper);
					setThirdDate(individualPigletMapper);
					setFourthDate(individualPigletMapper);
					setFifthDate(individualPigletMapper);
					setSixthDate(individualPigletMapper);
				}
			}
		}
	}

	
	private void setCompanyId(final IndividualPigletStatusMapper individualPigletMapper, ProcessDTO processDto) {
		try {
			individualPigletMapper.setDeriveCompanyId(processDto.getCompanyId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setPremiseId(final IndividualPigletStatusMapper individualPigletMapper, ProcessDTO processDto) {
		try {
			individualPigletMapper.setDerivePremiseId(processDto.getPremiseId()); 
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}
	
	private void setLitterId(final IndividualPigletStatusMapper individualPigletMapper) {
		try {
			individualPigletMapper.setDeriveLitterId(Integer.parseInt(individualPigletMapper.getLitterId())); 
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
	
	private void setWtAtFirstMonth(final IndividualPigletStatusMapper individualPigletMapper)
	{
		try {
			individualPigletMapper.setDeriveWtAtFirstMonth(Double.parseDouble(individualPigletMapper.getWtAtFirstMonth()));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setWtAtSecondMonth(final IndividualPigletStatusMapper individualPigletMapper)
	{
		try {
			individualPigletMapper.setDeriveWtAtSecondMonth(Double.parseDouble(individualPigletMapper.getWtAtSecondMonth()));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setWtAtThirdMonth(final IndividualPigletStatusMapper individualPigletMapper)
	{
		try {
			individualPigletMapper.setDeriveWtAtThirdMonth(Double.parseDouble(individualPigletMapper.getWtAtThirdMonth()));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setWtAtFourthMonth(final IndividualPigletStatusMapper individualPigletMapper)
	{
		try {
			individualPigletMapper.setDeriveWtAtFourthMonth(Double.parseDouble(individualPigletMapper.getWtAtFourthMonth()));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setWtAtFifthMonth(final IndividualPigletStatusMapper individualPigletMapper)
	{
		try {
			individualPigletMapper.setDeriveWtAtFifthMonth(Double.parseDouble(individualPigletMapper.getWtAtFifthMonth()));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private void setWtAtSixthMonth(final IndividualPigletStatusMapper individualPigletMapper)
	{
		try {
			individualPigletMapper.setDeriveWtAtSixthMonth(Double.parseDouble(individualPigletMapper.getWtAtSixthMonth()));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
		
	private void setFirstDate(final IndividualPigletStatusMapper individualPigletMapper)
	{
		try {
			individualPigletMapper.setDeriveFirstDate(DateUtil.getDateFromString(individualPigletMapper.getFirstDate()));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private void setSecondDate(final IndividualPigletStatusMapper individualPigletMapper)
	{
		try {
			individualPigletMapper.setDeriveSecondDate(DateUtil.getDateFromString(individualPigletMapper.getSecondDate()));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setThirdDate(final IndividualPigletStatusMapper individualPigletMapper)
	{
		try {
			individualPigletMapper.setDeriveThirdDate(DateUtil.getDateFromString(individualPigletMapper.getThirdDate()));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setFourthDate(final IndividualPigletStatusMapper individualPigletMapper)
	{
		try {
			individualPigletMapper.setDeriveFourthDate(DateUtil.getDateFromString(individualPigletMapper.getFourthDate()));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setFifthDate(final IndividualPigletStatusMapper individualPigletMapper)
	{
		try {
			individualPigletMapper.setDeriveFifthDate(DateUtil.getDateFromString(individualPigletMapper.getFifthDate()));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setSixthDate(final IndividualPigletStatusMapper individualPigletMapper)
	{
		try {
			individualPigletMapper.setDeriveSixthDate(DateUtil.getDateFromString(individualPigletMapper.getSixthDate()));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
