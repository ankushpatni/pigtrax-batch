package com.pigtrax.batch.drivable;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pigtrax.batch.config.RefData;
import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.dao.interfaces.BarnDao;
import com.pigtrax.batch.dao.interfaces.CompanyDao;
import com.pigtrax.batch.dao.interfaces.OriginDao;
import com.pigtrax.batch.dao.interfaces.PenDao;
import com.pigtrax.batch.dao.interfaces.PremisesDao;
import com.pigtrax.batch.dao.interfaces.RoomDao;
import com.pigtrax.batch.drivable.interfaces.Derivable;
import com.pigtrax.batch.mapper.PigInfoMapper;
import com.pigtrax.batch.mapper.interfaces.Mapper;
import com.pigtrax.batch.util.DateUtil;

@Component
public class PiginfoDerivable implements Derivable {

	@Autowired
	private BarnDao barnDao;

	@Autowired
	private PenDao penDao;

	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private OriginDao originDao;
	
	@Autowired
	PremisesDao premiseDao;

	@Autowired
	RoomDao roomDao;
	
	@Override
	public void derive(final List<Mapper> list, final ProcessDTO processDTO) {
		if (list != null) {
			for (Mapper mapper : list) {
				PigInfoMapper pigInfoMapper = (PigInfoMapper) mapper;
				if(!pigInfoMapper.isEmpty())
				{
					setCompanyId(pigInfoMapper, processDTO);
					setPremiseId(pigInfoMapper, processDTO);
					setEntryDate(pigInfoMapper);
					setBirthdate(pigInfoMapper);
					setParity(pigInfoMapper);				
					setSexId(pigInfoMapper);
					setGfunctionTypeId(pigInfoMapper);
					setFarrowEventDate(pigInfoMapper);
					setBarnId(pigInfoMapper);
					setRoomId(pigInfoMapper);
					setGline(pigInfoMapper);
					setGCompany(pigInfoMapper);
					setOrigin(pigInfoMapper);
				}
			}
		}
	}
	
	
	private void setGline(final PigInfoMapper pigInfoMapper) {	
		try {
			Integer glineDerived = RefData.GLINE.getId(pigInfoMapper.getGeneticLine());
			if (glineDerived > -1) {
				pigInfoMapper.setDeriveGline(glineDerived);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setOrigin(final PigInfoMapper pigInfoMapper) {	
		try {
			Integer originDerived = originDao.getOriginId(pigInfoMapper.getGeneticOrigin());
			if (originDerived != null && originDerived > 0) {
				pigInfoMapper.setDeriveOriginId(originDerived);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setGCompany(final PigInfoMapper pigInfoMapper) {
		try {
			Integer gcompanyIdDerived = RefData.GCOMPANY.getId(pigInfoMapper.getGeneticCompany());
			if (gcompanyIdDerived > -1) {
				pigInfoMapper.setDeriveGCompany(gcompanyIdDerived);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setEntryDate(final PigInfoMapper pigInfoMapper) {
		try {
			pigInfoMapper.setDeriveEntryDate(DateUtil.getDateFromString(pigInfoMapper.getEntryDate()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setBirthdate(final PigInfoMapper pigInfoMapper) {
		try {
			pigInfoMapper.setDeriveBirthDate(DateUtil.getDateFromString(pigInfoMapper.getBirthDate()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setParity(final PigInfoMapper pigInfoMapper) {
		try {
			pigInfoMapper.setDeriveParity(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setFarrowEventDate(final PigInfoMapper pigInfoMapper) {
		pigInfoMapper.setDeriveFarrowEventDate(DateUtil.getCurrentDate());
	}

	private void setBarnId(final PigInfoMapper pigInfoMapper) {
		if (pigInfoMapper.getBarnId() != null) {
			try {
				pigInfoMapper.setDeriveBarnId(barnDao.getBarnPKId(pigInfoMapper.getBarnId()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void setCompanyId(final PigInfoMapper pigInfoMapper, ProcessDTO processDTO) {
		try {
			pigInfoMapper.setDeriveCompanyId(processDTO.getCompanyId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setPremiseId(final PigInfoMapper pigInfoMapper, ProcessDTO processDTO) {
		try {
			pigInfoMapper.setDerivePremiseId(processDTO.getPremiseId()); 
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}
  
	private void setSexId(final PigInfoMapper pigInfoMapper) {
		try {
			Integer sexIdFromRefData = RefData.SEX.getId(pigInfoMapper.getSexTypeId());
			if (sexIdFromRefData > -1) {
				pigInfoMapper.setDeriveSexId(sexIdFromRefData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setRoomId(final PigInfoMapper pigInfoMapper) {
		if (pigInfoMapper.getRoomId() != null) {
			try {
				pigInfoMapper.setDeriveRoomId(roomDao.getRoomPkId(pigInfoMapper.getRoomId(), pigInfoMapper.getDeriveCompanyId(), pigInfoMapper.getDerivePremiseId()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void setGfunctionTypeId(final PigInfoMapper pigInfoMapper) {
		try {
			Integer gFunctionIdFromRefData = RefData.GFUNCTION.getId(pigInfoMapper.getGeneticFunction());
			if (gFunctionIdFromRefData > -1) {
				pigInfoMapper.setDeriveFfunctionTypeId(gFunctionIdFromRefData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
