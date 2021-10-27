package com.ab.SuperHero.controller;

import java.time.LocalDate;
import java.util.ArrayList;
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
import com.ab.SuperHero.dto.Hero;
import com.ab.SuperHero.dto.Location;
import com.ab.SuperHero.dto.Sighting;

@Controller
public class SightingController {
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
	
	@GetMapping("sightings")
	public String displaySightings(Model model) {
		List<Sighting> sightings = sightingDao.getAllSightings();
		List<Location> locations = locationDao.getAllLocation();
		List<Hero> heroes = heroDao.getAllHeroes();
		
		model.addAttribute("sightings", sightings);
		model.addAttribute("locations", locations);
		model.addAttribute("heroes", heroes);
		
		return "sightings";
	}
//	@GetMapping("getHero")
//	public String getHero(Integer id, Model model) {
//		List<Hero> hero1 = sightingDao.getAllHeroesBySighting(id);
//		List<Location> loc = locationDao.getAllLocationsByHero(id);
//		model.addAttribute("hero1", hero1);
//		model.addAttribute("loc", loc);
//		return "redirect:/sightings";
//	}
	
	@PostMapping("addSighting")
	public String addSighting(HttpServletRequest request) {
		String date = request.getParameter("date");
		String location = request.getParameter("location");
		String heroName = request.getParameter("heroName");
		
		Hero hero1 = heroDao.getHeroById(Integer.valueOf(heroName));
		List<Hero> sightingHeroes = new ArrayList<>();
		sightingHeroes.add(hero1);
		
		Sighting sighting = new Sighting();
		sighting.setDate(LocalDate.parse(date));
		sighting.setLocationId(Integer.valueOf(location));
		sighting.setHeroes(sightingHeroes);
		
		sightingDao.addSighting(sighting);
		
		return "redirect:/sightings";
	} 
	
	@GetMapping("deleteSighting")
	public String deleteSighting(Integer id) {
		sightingDao.deleteSighting(id);
		return "redirect:/sightings";
	}
	
	@GetMapping("editSighting")
	public String editSighting(Integer id, Model model) {
		Sighting sighting = sightingDao.getSightingById(id);
		Location location = locationDao.getLocationById(sighting.getLocationId());
		List<Location> locations = locationDao.getAllLocation();
		
		List<Hero> heroes = heroDao.getAllHeroes();
		
		model.addAttribute("sighting", sighting);
		model.addAttribute("location", location);
		model.addAttribute("locations", locations);
		model.addAttribute("heroes", heroes);
		return "editSighting";
	}
	
	@PostMapping("editSighting")
	public String performEditSighting(Sighting sighting, HttpServletRequest request) {
		String date = request.getParameter("date1");
		String location = request.getParameter("location");
		String heroName = request.getParameter("heroName");
		
		Hero hero1 = heroDao.getHeroById(Integer.valueOf(heroName));
		List<Hero> sightingHeroes = new ArrayList<>();
		sightingHeroes.add(hero1);
		
			
		sighting.setDate(LocalDate.parse(date));
		sighting.setLocationId(Integer.valueOf(location));
		sighting.setHeroes(sightingHeroes);
		
		sightingDao.updateSighting(sighting);
		
		return "redirect:/sightings";
	}
	
	@GetMapping("sightingDetails")
	public String getSightingDetails(Integer id, Model model) {
		Sighting sighting = sightingDao.getSightingById(id);
		List<Hero> heroes = sightingDao.getAllHeroesBySighting(id);
		Location location = locationDao.getLocationById(sighting.getLocationId());
		model.addAttribute("heroes", heroes);
		model.addAttribute("sighting", sighting);
		model.addAttribute("location", location);
		
		return "sightingDetails";
	}
		
	@GetMapping("/")
	public String getIndex(Model model) {
		List<Sighting> sightings = sightingDao.getAllSightings();
		List<Location> locations = locationDao.getAllLocation();
		
		model.addAttribute("sightings", sightings);
		model.addAttribute("locations", locations);
		return "index";
	}
	
	@GetMapping("latest")
	public String getLast(Integer sightId, Model model) {
		Sighting sight = sightingDao.getSightingById(sightId);
		List<Hero> heroes = sightingDao.getAllHeroesBySighting(sightId);
		Location location = locationDao.getLocationById(sight.getLocationId());
		
		model.addAttribute("sight", sight);
		model.addAttribute("heroes", heroes);
		model.addAttribute("location", location);
		
		return "index";
	}
	
	
	
	
	
}
