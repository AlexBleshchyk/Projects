package com.ab.SuperHero.dao;

import java.util.List;

import com.ab.SuperHero.dto.Location;

public interface LocationDao {

	Location addLocation(Location location);

	void updateLocation(Location location);

	void deleteLocation(int locationId);

	Location getLocationById(int locatioId);

	List<Location> getAllLocation();
	
	List<Location> getAllLocationsByHero(int heroId);
	
}
