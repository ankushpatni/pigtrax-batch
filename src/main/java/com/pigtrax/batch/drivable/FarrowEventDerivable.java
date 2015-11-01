package com.pigtrax.batch.drivable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.dao.PenDaoImpl;
import com.pigtrax.batch.dao.interfaces.CompanyDao;
import com.pigtrax.batch.dao.interfaces.EmployeeGroupDao;
import com.pigtrax.batch.dao.interfaces.PigInfoDao;
import com.pigtrax.batch.drivable.interfaces.Derivable;
import com.pigtrax.batch.mapper.FarrowEventMapper;
import com.pigtrax.batch.mapper.interfaces.Mapper;
import com.pigtrax.batch.util.DateUtil;

public class FarrowEventDerivable implements Derivable {

	@Autowired
	private CompanyDao companyDao;

	@Autowired
	private PigInfoDao pigInfoDao;

	@Autowired
	private PenDaoImpl penDao;

	@Autowired
	private EmployeeGroupDao employeeGroupDao;

	@Override
	public void derive(List<Mapper> list, ProcessDTO processDTO) {
		if (list != null) {
			for (Mapper mapper : list) {
				FarrowEventMapper farrowEventMapper = (FarrowEventMapper) mapper;
				setPigIDPK(farrowEventMapper);
				setServiceDate(farrowEventMapper);
				setPenId(farrowEventMapper);
				setFarrowDate(farrowEventMapper);
				setStillBorns(farrowEventMapper);
				setLiveBorns(farrowEventMapper);
				setMaleBorns(farrowEventMapper);
				setFemaleBorns(farrowEventMapper);
				setMummies(farrowEventMapper);
				setweightInKGs(farrowEventMapper);
				setEmployeeGroupId(farrowEventMapper);
				setTeats(farrowEventMapper);
				setSowCondition(farrowEventMapper);

			}
		}

	}

	private void setTeats(final FarrowEventMapper farrowEventMapper) {
		try {
			farrowEventMapper.setDeriveTeasts(Integer.parseInt(farrowEventMapper.getTeasts()));
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

	private void setPigIDPK(final FarrowEventMapper farrowEventMapper) {
		try {
			Integer derivedCompanyId = companyDao.getCompanyId(farrowEventMapper.getCompanyId());
			farrowEventMapper.setDeriveCompanyId(derivedCompanyId);
			Map<String, String> searchCriteria = new HashMap<String, String>();
			searchCriteria.put("companyId", derivedCompanyId != null ? derivedCompanyId.toString() : null);
			searchCriteria.put("pigId", farrowEventMapper.getPigInfoId());
			farrowEventMapper.setDerivePigInfoId(pigInfoDao.getPKfromPigId(searchCriteria));
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

	private void setweightInKGs(final FarrowEventMapper farrowEventMapper) {
		try {
			farrowEventMapper.setDeriveWeightInKGs(Integer.parseInt(farrowEventMapper.getWeightInKGs()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setEmployeeGroupId(final FarrowEventMapper farrowEventMapper) {
		if (farrowEventMapper.getEmployeeGrpId() != null) {
			try {
				farrowEventMapper.setDeriveEmployeeGrpId(employeeGroupDao.getEmployeeGroupPKId(farrowEventMapper.getEmployeeGrpId()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
