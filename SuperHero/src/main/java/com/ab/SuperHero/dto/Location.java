package com.ab.SuperHero.dto;

import java.util.Objects;

public class Location {
	
	private int locationId;
	private String locationName;
	private String street;
	private String city;
	private String zip;
	private String description;
	private double latitude;
	private double longitude;
		
	public int getLocationId() {
		return locationId;
	}
	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
//	@Override
//	public int hashCode() {
//		return Objects.hash(city, description, latitude, locationId, locationName, longitude, street, zip);
//	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		return Objects.equals(city, other.city) && Objects.equals(description, other.description)
				&& Double.doubleToLongBits(latitude) == Double.doubleToLongBits(other.latitude)
				&& locationId == other.locationId && Objects.equals(locationName, other.locationName)
				&& Double.doubleToLongBits(longitude) == Double.doubleToLongBits(other.longitude)
				&& Objects.equals(street, other.street) && Objects.equals(zip, other.zip);
	}
		
}
