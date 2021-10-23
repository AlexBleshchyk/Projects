package com.ab.SuperHero.dao;

import java.time.LocalDate;
import java.util.List;

import com.ab.SuperHero.dto.Sighting;

public interface SightingDao {
	
	Sighting addSighting(Sighting sighting);
	
	void updateSighting(Sighting sighting);
	
	void deleteSighting(int sightingId);
	
	Sighting getSightingById(int sightingId);
	
	List<Sighting> getAllSighting();
	
	List<Sighting> getAllSightingByDate(LocalDate date);
	
	List<Sighting> getAllSightingByHero(int heroId);
	
	List<Sighting> getAllSightingByLocation(int locationId);
	
}
