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

import com.ab.SuperHero.dto.Location;

@Repository
public class LocationDaoDB implements LocationDao{
	
	@Autowired
	JdbcTemplate jdbc;
	
	@Override
	@Transactional
	public Location addLocation(Location location) {
		final String INSERT_LOCATION = "INSERT INTO Location("
				+ "locationName, street, city, zip, locationDescription,"
				+ "latitude, longitude) VALUES(?,?,?,?,?,?,?)";
		jdbc.update(INSERT_LOCATION,
				location.getLocationName(),
				location.getStreet(),
				location.getCity(),
				location.getZip(),
				location.getDescription(),
				location.getLatitude(),
				location.getLongitude());
		int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
		location.setLocationId(newId);
		return location;
	}

	@Override
	public void updateLocation(Location location) {
		final String UPDATE_LOCATION = "UPDATE Location SET "
				+ "locationName = ?, street = ?, city = ?, zip = ?,"
				+ "locationDescription = ?, latitude = ?, longitude = ?"
				+ "WHERE locationId = ?";
		jdbc.update(UPDATE_LOCATION,
				location.getLocationName(),
				location.getStreet(),
				location.getCity(),
				location.getZip(),
				location.getDescription(),
				location.getLatitude(),
				location.getLongitude(),
				location.getLocationId());
	}

	@Override
	public void deleteLocation(int locationId) {
		final String DELETE_LOCATION = "DELETE FROM Location WHERE locationId = ?";
		jdbc.update(DELETE_LOCATION, locationId);
	}

	@Override
	public Location getLocationById(int locatioId) {
		try {
			final String SELECT_LOCATION_BY_ID = "SELECT * FROM Location WHERE locationId = ?";
			return jdbc.queryForObject(SELECT_LOCATION_BY_ID, new LocationMapper(), locatioId);
		}catch(DataAccessException ex) {
			return null;
		}	
	}

	@Override
	public List<Location> getAllLocation() {
		final String SELECT_ALL_LOCATIONS = "SELECT * FROM Location";
		return jdbc.query(SELECT_ALL_LOCATIONS, new LocationMapper());
	}

	@Override
	public List<Location> getAllLocationsByHero(int heroId) {
		final String SELECT_LOCATION_BY_HERO = "SELECT * FROM Location l "
				+ "JOIN heroSighting hs "
				+ "ON hs.Sighting_Location_locationId = l.locationId "
				+ "WHERE Hero_heroId = ?";
		return jdbc.query(SELECT_LOCATION_BY_HERO, new LocationMapper(), heroId);
	}

	
	public static final class LocationMapper implements RowMapper<Location>{

		@Override
		public Location mapRow(ResultSet rs, int rowNum) throws SQLException {
			Location location = new Location();
			location.setLocationId(rs.getInt("locationId"));
			location.setLocationName(rs.getString("locationName"));
			location.setStreet(rs.getString("street"));
			location.setCity(rs.getString("city"));
			location.setZip(rs.getString("zip"));
			location.setDescription(rs.getString("locationDescription"));
			location.setLatitude(rs.getDouble("latitude"));
			location.setLongitude(rs.getDouble("longitude"));
			return location;
		}
		
	}


	
}
