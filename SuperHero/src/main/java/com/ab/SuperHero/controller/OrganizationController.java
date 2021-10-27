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
import com.ab.SuperHero.dto.Hero;
import com.ab.SuperHero.dto.Location;
import com.ab.SuperHero.dto.Organization;

@Controller
public class OrganizationController {
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
	
	@GetMapping("organizations")
	public String displayOrganizations(Model model) {
		List<Organization> organizations = organizationDao.getAllOrganization();
		List<Location> locations = locationDao.getAllLocation();
		model.addAttribute("organizations", organizations);
		model.addAttribute("locations", locations);
		return "organizations";	
	}
	
	@PostMapping("addOrganization")
	public String addOrganization(HttpServletRequest request) {
		String organizationName = request.getParameter("organizationName");
		String contact = request.getParameter("contact");
		String description = request.getParameter("description");
		String membersNumber = request.getParameter("membersNumber");
		String location = request.getParameter("location");
		
		Organization organiz= new Organization();
		organiz.setOrganizationName(organizationName);
		organiz.setContact(contact);
		organiz.setDescription(description);
		organiz.setMembersNumber(Integer.valueOf(membersNumber));
		organiz.setLocationId(Integer.valueOf(location));
		
		organizationDao.addOrganization(organiz);
		
		return "redirect:/organizations";
	}
	
	@GetMapping("deleteOrganization")
	public String deleteOrganization(Integer id) {
		organizationDao.deleteOrganization(id);
		return "redirect:/organizations";
	}
	
	@GetMapping("editOrganization")
	public String editOrganization(Integer id, Model model) {
		Organization organization = organizationDao.getOrganizationById(id);
		List<Location> locations = locationDao.getAllLocation();
		model.addAttribute("organization", organization);
		model.addAttribute("locations", locations);
		
		return "editOrganization";
	}
	
	@PostMapping("editOrganization")
	public String performEditOrganization(Organization organization, HttpServletRequest request) {
		String organizationName = request.getParameter("organizationName");
		String contact = request.getParameter("contact");
		String description = request.getParameter("description");
		String membersNumber = request.getParameter("membersNumber");
		String location = request.getParameter("location");
		
		organization.setOrganizationName(organizationName);
		organization.setContact(contact);
		organization.setDescription(description);
		organization.setMembersNumber(Integer.valueOf(membersNumber));
		organization.setLocationId(Integer.valueOf(location));
		
		organizationDao.updateOrganization(organization);
		
		
		return "redirect:/organizations";
	}
	
	@GetMapping("organizationDetails")
	public String getOrganizationDetails(Integer id, Model model) {
		List<Hero> heroes = heroDao.getAllHeroesByOrganization(id);
		Organization organization = organizationDao.getOrganizationById(id);
		Location location = locationDao.getLocationById(organization.getLocationId());
		
		model.addAttribute("heroes", heroes);
		model.addAttribute("organization", organization);
		
		model.addAttribute("location", location);
		
		return "organizationDetails";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
