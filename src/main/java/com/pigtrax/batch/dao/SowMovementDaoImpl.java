package com.pigtrax.batch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.batch.beans.SowMovement;
import com.pigtrax.batch.dao.interfaces.SowMovementDao;

@Repository
@Transactional
public class SowMovementDaoImpl implements SowMovementDao{
	
private static final Logger logger = Logger.getLogger(SowMovementDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate; 
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	
	@Override
	public int addSowMovement(final SowMovement sowMovement) throws SQLException {
		final String Qry = "insert into pigtrax.\"SowMovement\"(\"id_PigInfo\", \"id_Room\", \"id_Premise\", \"movementDate\","
				+ "  \"lastUpdated\",\"userUpdated\",\"id_Company\") "
				+ "values(?,?,?,current_timestamp,current_timestamp,?,?)";	
		

		KeyHolder holder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement(Qry,new String[] { "id" });
				ps.setInt(1, sowMovement.getPigInfoId());
				ps.setObject(2, sowMovement.getRoomId(), java.sql.Types.INTEGER);
				ps.setInt(3, sowMovement.getPremiseId());
				ps.setString(4, sowMovement.getUserUpdated());
				ps.setInt(5, sowMovement.getCompanyId());
				return ps;
			
			}
		}, holder);
		int keyVal = holder.getKey().intValue();
		logger.info("Key generated = " + keyVal);
		return keyVal;
	}
	
	
}
