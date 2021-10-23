package com.ab.SuperHero.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ab.SuperHero.dto.Power;

@Repository
public class PowerDaoDB implements PowerDao {
	
	@Autowired
	JdbcTemplate jdbc;
	
	@Override
	@Transactional
	public Power addPower(Power power) {
		final String INSERT_POWER = "INSERT INTO Power("
				+ "superPowerName,superPowerDescription) VALUES(?,?)";
		jdbc.update(INSERT_POWER,
				power.getPowerName(),
				power.getPowerDescription());
		int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
		power.setPowerId(newId);
		return power;
	}

	@Override
	public void updatePower(Power power) {
		final String UPDATE_POWER = "UPDATE Power SET"
				+ "superPowerName = ?,"
				+ "superPowerDescription = ? "
				+ "WHERE superPowerId = ?";
		jdbc.update(UPDATE_POWER,
				power.getPowerName(),
				power.getPowerDescription(),
				power.getPowerId());
	}

	@Override
	public void deletePower(int powerId) {
		final String DELETE_POWER = "DELETE FROM Power WHERE superPowerId = ?";
		jdbc.update(DELETE_POWER, powerId);
	}

	@Override
	public Power getPowerById(int powerId) {
		try {
			final String SELECT_POWER_BY_ID = "SELECT * FROM Power WHERE superPowerId = ?";
			return jdbc.queryForObject(SELECT_POWER_BY_ID, new PowerMapper(), powerId);
		}catch(DataAccessException ex) {
			return null;
		}
	}

	@Override
	public List<Power> getAllPower() {
		final String SELECT_ALL_POWERS = "SELECT * FROM Power";
		return jdbc.query(SELECT_ALL_POWERS, new PowerMapper());
	}

	@Override
	public List<Power> getAllPowerByHero(int heroId) {
		final String SELECT_POWER_BY_HERO = "SELECT * FROM Power p"
				+ "JOIN Hero h ON h.SuperPower_superPowerId = p.superPowerId "
				+ "WHERE heroId = ?";
		return jdbc.query(SELECT_POWER_BY_HERO, new PowerMapper(), heroId);
	}
	
	public static final class PowerMapper implements RowMapper<Power>{

		@Override
		public Power mapRow(ResultSet rs, int rowNum) throws SQLException {
			Power power = new Power();
			power.setPowerId(rs.getInt("superPowerId"));
			power.setPowerName(rs.getString("superPowerName"));
			power.setPowerDescription(rs.getString("superPowerDescription"));
			return power;
		}
		
	}
	
}
