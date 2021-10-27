package com.ab.SuperHero.controller;

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
import com.ab.SuperHero.dto.Organization;
import com.ab.SuperHero.dto.Power;


@Controller
public class HeroController {
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
	
	@GetMapping("heroes")
	public String displayHeroes(Model model) {
		List<Hero> heroes = heroDao.getAllHeroes();
		List<Organization> organizations = organizationDao.getAllOrganization();
		
		List<Location> locations = locationDao.getAllLocation();
		List<Power> powers = powerDao.getAllPower();
		
		model.addAttribute("heroes", heroes);
		model.addAttribute("organizations", organizations);
		model.addAttribute("locations", locations);
		model.addAttribute("powers", powers);
		return "heroes";	
	}
	
	@PostMapping("addHero")
    public String addHero(HttpServletRequest request) {
        String heroName = request.getParameter("heroName");
        String superPower = request.getParameter("superPower");
        String description = request.getParameter("description");
        String ishero = request.getParameter("isHero");
        String organization = request.getParameter("organization"); 
               
        Power power = powerDao.getPowerById(Integer.valueOf(superPower));
        powerDao.updatePower(power);
        
        
        List<Organization> organizations = new ArrayList<>();
        Organization org1 = organizationDao.getOrganizationById(Integer.valueOf(organization));
        org1.setOrganizationName(String.valueOf(organization));
        organizations.add(org1);
        
        Hero hero = new Hero();
        hero.setHeroName(heroName);
        hero.setSuperPowerId(power.getPowerId());
        hero.setHeroDescription(description);
        hero.setHero(Boolean.valueOf(ishero));
        //hero.setOrganizationId(Integer.valueOf(organization));
        hero.setOrganizations(organizations);
                
        heroDao.addHero(hero);
               
        return "redirect:/heroes";
    }
	
	@GetMapping("deleteHero")
	public String deleteHero(Integer id) {
		heroDao.deleteHeroById(id);
		return "redirect:/heroes";
	}
	
	@GetMapping("editHero")
	public String editHero(Integer id, Model model) {
		Hero hero = heroDao.getHeroById(id);
		List<Organization> organizations = organizationDao.getAllOrganization();
		Organization organization = organizationDao.getOrganizationById(hero.getOrganizationId());
		List<Power> powers = powerDao.getAllPower();
		Power power = powerDao.getPowerById(hero.getSuperPowerId());
		model.addAttribute("hero", hero);
		model.addAttribute("organizations", organizations);
		model.addAttribute("organization", organization);
		model.addAttribute("powers", powers);
		
		model.addAttribute("power", power);
		
		return "editHero";
	}
	
	@PostMapping("editHero")
	public String performEditHero(Hero hero, HttpServletRequest request) {
		String heroName = request.getParameter("heroName");
        String superPower = request.getParameter("superPower");
        String description = request.getParameter("description");
        String isHero = request.getParameter("isHero");
        String organizationName = request.getParameter("organization");     
        
        Power power = powerDao.getPowerById(Integer.valueOf(superPower));
        power.setPowerName(String.valueOf(superPower));
        powerDao.updatePower(power);
        
        List<Organization> organizations = new ArrayList<>();
        
        Organization org1 = organizationDao.getOrganizationById(Integer.valueOf(organizationName));
        org1.setOrganizationName(String.valueOf(organizationName));
        organizations.add(org1); 
        
        hero.setHeroName(heroName);
        hero.setSuperPowerId(power.getPowerId());
        hero.setHeroDescription(description);
        hero.setHero(Boolean.valueOf(isHero));
       // hero.setOrganizationId(organz.getOrganizationId());
        hero.setOrganizations(organizations);
        
        heroDao.updateHero(hero);
        
		return "redirect:/heroes";
	}
	@GetMapping("heroDetails")
	public String getHeroDetails(Integer id, Model model) {
		List<Hero> heroes = heroDao.getAllHeroes();
		List<Organization> organizations = organizationDao.getAllOrganization();
		List<Location> locations = locationDao.getAllLocation();
		List<Power> powers = powerDao.getAllPowerByHero(id);
		Hero hero = heroDao.getHeroById(id);
		List<Organization> heroOrganization = organizationDao.getAllOrganizationByHero(hero.getHeroId());
		Power power = powerDao.getPowerById(hero.getSuperPowerId());
		
		model.addAttribute("heroes", heroes);
		model.addAttribute("organizations", organizations);
		model.addAttribute("locations", locations);
		model.addAttribute("powers", powers);
		model.addAttribute("hero", hero);
		model.addAttribute("heroOrganization", heroOrganization);
		model.addAttribute("power", power);
		
		return "heroDetails";
	}

	
	
	
	
}
