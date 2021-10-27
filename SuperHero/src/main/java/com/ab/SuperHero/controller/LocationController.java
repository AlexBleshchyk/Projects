package com.ab.SuperHero.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.ab.SuperHero.dao.HeroDao;
import com.ab.SuperHero.dao.LocationDao;
import com.ab.SuperHero.dao.OrganizationDao;
import com.ab.SuperHero.dao.PowerDao;
import com.ab.SuperHero.dao.SightingDao;
import com.ab.SuperHero.dto.Location;

@Controller
public class LocationController {
	@Autowired
	HeroDao heroDao;
	@Autowired
	OrganizationDao organizationDao;
	@Autowired
	LocationDao locationDao;
	@Autowired
	PowerDao powerDao;
	@Autowired
	SightingDao sightingDao;
	
	@GetMapping("locations")
	public String displayLocations(Model model) {
		List<Location> locations = locationDao.getAllLocation();
		model.addAttribute("locations", locations);
		return "locations";
	}
	
	@PostMapping("addLocation")
	public String addLocation(HttpServletRequest request) {
		String name = request.getParameter("locationName");
		String street = request.getParameter("street");
		String city = request.getParameter("city");
		String zip = request.getParameter("zip");
		String description = request.getParameter("description");
		String latitude = request.getParameter("latitude");
		String longitude = request.getParameter("longitude");
		
		Location loc = new Location();
		loc.setLocationName(name);
		loc.setStreet(street);
		loc.setCity(city);
		loc.setZip(zip);
		loc.setDescription(description);
		loc.setLatitude(Double.valueOf(latitude));
		loc.setLongitude(Double.valueOf(longitude));
		
		locationDao.addLocation(loc);
		
		return "redirect:/locations";
		
	}
	
	@GetMapping("deleteLocation")
	public String deleteLocation(Integer id) {
		locationDao.deleteLocation(id);
		return "redirect:locations";
	}
	
	@GetMapping("editLocation")
	public String editLocation(Integer id, Model model) {
		Location location = locationDao.getLocationById(id);
		model.addAttribute("location", location);
		return "editLocation";
	}
	
	@PostMapping("editLocation")
	public String performEditLocation(Location location, HttpServletRequest request) {
		String name = request.getParameter("locationName");
		String street = request.getParameter("street");
		String city = request.getParameter("city");
		String zip = request.getParameter("zip");
		String description = request.getParameter("description");
		String latitude = request.getParameter("latitude");
		String longitude = request.getParameter("longitude");
		
		location.setLocationName(name);
		location.setStreet(street);
		location.setCity(city);
		location.setZip(zip);
		location.setDescription(description);
		location.setLatitude(Double.valueOf(latitude));
		location.setLongitude(Double.valueOf(longitude));
		
		locationDao.updateLocation(location);
		
		return "redirect:/locations";
	}
	
	
	
	
	
	
	
	
}
