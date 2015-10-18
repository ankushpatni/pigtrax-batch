package com.pigtrax.batch.drivable;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pigtrax.batch.config.RefData;
import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.dao.interfaces.BarnDao;
import com.pigtrax.batch.dao.interfaces.CompanyDao;
import com.pigtrax.batch.dao.interfaces.PenDao;
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

	@Override
	public void derive(final List<Mapper> list, final ProcessDTO processDTO) {
		if (list != null) {
			for (Mapper mapper : list) {
				PigInfoMapper pigInfoMapper = (PigInfoMapper) mapper;
				setEntryDate(pigInfoMapper);
				setBirthdate(pigInfoMapper);
				setParity(pigInfoMapper);
				setCompanyId(pigInfoMapper);
				setSexId(pigInfoMapper);
				setGfunctionTypeId(pigInfoMapper);
				setFarrowEventDate(pigInfoMapper);
				setBarnId(pigInfoMapper);
				setPenId(pigInfoMapper);
			}
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
			pigInfoMapper.setDeriveParity(Integer.parseInt(pigInfoMapper.getParity()));
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

	private void setCompanyId(final PigInfoMapper pigInfoMapper) {
		try {
			pigInfoMapper.setDeriveCompanyId(companyDao.getCompanyId(pigInfoMapper.getCompanyId()));
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

	private void setPenId(final PigInfoMapper pigInfoMapper) {
		if (pigInfoMapper.getPenId() != null) {
			try {
				pigInfoMapper.setDerivePenId(penDao.getPenPKId(pigInfoMapper.getPenId()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void setGfunctionTypeId(final PigInfoMapper pigInfoMapper) {
		try {
			Integer gFunctionIdFromRefData = RefData.GFUNCTION.getId(pigInfoMapper.getGfunctionTypeId());
			if (gFunctionIdFromRefData > -1) {
				pigInfoMapper.setDeriveFfunctionTypeId(gFunctionIdFromRefData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
