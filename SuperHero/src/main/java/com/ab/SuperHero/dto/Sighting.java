package com.ab.SuperHero.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Sighting {
	
	private int sightingId;
	private LocalDate date;
	private int locationId;
	private List<Hero> heroes;
	
	public int getSightingId() {
		return sightingId;
	}
	public void setSightingId(int sightingId) {
		this.sightingId = sightingId;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public int getLocationId() {
		return locationId;
	}
	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}
	public List<Hero> getHeroes() {
		return heroes;
	}
	public void setHeroes(List<Hero> heroes) {
		this.heroes = heroes;
	}
//	@Override
//	public int hashCode() {
//		return Objects.hash(date, heroes, locationId, sightingId);
//	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sighting other = (Sighting) obj;
		return Objects.equals(date, other.date) && Objects.equals(heroes, other.heroes)
				&& Objects.equals(locationId, other.locationId) && sightingId == other.sightingId;
	}
	
}
