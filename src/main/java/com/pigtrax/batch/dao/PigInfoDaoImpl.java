package com.pigtrax.batch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.batch.beans.PigInfo;
import com.pigtrax.batch.dao.interfaces.PigInfoDao;

@Repository
@Transactional
public class PigInfoDaoImpl implements PigInfoDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final Logger logger = Logger.getLogger(PigInfoDaoImpl.class);

	@Override
	public int insertPigInformation(final PigInfo pigInfo) throws SQLException, DuplicateKeyException {
		final String Qry = "insert into pigtrax.\"PigInfo\"(\"pigId\", \"sireId\", \"damId\", \"entryDate\", \"origin\", \"gline\", \"gcompany\", "
				+ "\"birthDate\", \"tattoo\", \"alternateTattoo\", \"remarks\", \"lastUpdated\", \"userUpdated\", \"id_Company\", \"id_Pen\", "
				+ "\"id_Barn\", \"id_SexType\", \"parity\",\"isActive\",\"id_GfunctionType\") "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,current_timestamp,?,?,?,?,?,?,?,?)";
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(Qry, new String[] { "id" });
				ps.setString(1, pigInfo.getPigId());
				ps.setString(2, pigInfo.getSireId());
				ps.setString(3, pigInfo.getDamId());
				ps.setDate(4, new java.sql.Date(pigInfo.getEntryDate().getTime()));
				ps.setString(5, pigInfo.getOrigin());
				ps.setString(6, pigInfo.getGline());
				ps.setString(7, pigInfo.getGcompany());
				if (pigInfo.getBirthDate() != null) {
					ps.setDate(8, new java.sql.Date(new DateTime(pigInfo.getBirthDate()).toLocalDate().toDateMidnight().getMillis()));
				} else {
					ps.setNull(8, java.sql.Types.DATE);
				}
				ps.setString(9, pigInfo.getTattoo());
				ps.setString(10, pigInfo.getAlternateTattoo());
				ps.setString(11, pigInfo.getRemarks());
				ps.setString(12, pigInfo.getUserUpdated());
				ps.setInt(13, pigInfo.getCompanyId());
				if (pigInfo.getPenId() != null && pigInfo.getPenId() != 0) {
					ps.setObject(14, pigInfo.getPenId());
				} else {
					ps.setNull(14, java.sql.Types.INTEGER);
				}
				ps.setObject(15, pigInfo.getBarnId(), java.sql.Types.INTEGER);
				ps.setObject(16, pigInfo.getSexTypeId(), java.sql.Types.INTEGER);
				ps.setInt(17, pigInfo.getParity());
				ps.setBoolean(18, pigInfo.isActive());
				ps.setObject(19, pigInfo.getGfunctionTypeId(), java.sql.Types.INTEGER);
				return ps;
			}
		}, holder);
		int keyVal = holder.getKey().intValue();
		logger.info("Key generated = " + keyVal);
		return keyVal;

	}

}
