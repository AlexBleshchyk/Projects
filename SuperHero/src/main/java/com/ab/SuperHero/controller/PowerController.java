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
import com.ab.SuperHero.dto.Power;

@Controller
public class PowerController {
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
	
	@GetMapping("powers")
	public String displayPowera(Model model) {
		List<Power> powers = powerDao.getAllPower();
		model.addAttribute("powers", powers);
		return "powers";
	}
	
	@PostMapping("addPower")
	public String addPower(HttpServletRequest request) {
		String powerName = request.getParameter("powerName");
		String powerDescription = request.getParameter("powerDescription");
		
		Power power = new Power();
		power.setPowerName(powerName);
		power.setPowerDescription(powerDescription);
		
		powerDao.addPower(power);
		
		return "redirect:/powers";
	}
	
	@GetMapping("deletePower")
	public String deletePower(Integer id) {
		powerDao.deletePower(id);
		return "redirect:/powers";
	}
	
	@GetMapping("editPower")
	public String editPower(Integer id, Model model) {
		Power power = powerDao.getPowerById(id);
		model.addAttribute("power", power);
		return "editPower";
	}
	
	@PostMapping("editPower")
	public String performEditPower(Power power, HttpServletRequest request) {
		String powerName = request.getParameter("powerName");
		String powerDescription = request.getParameter("powerDescription");
		
		power.setPowerName(powerName);
		power.setPowerDescription(powerDescription);
		
		powerDao.updatePower(power);		
		
		return "redirect:/powers";
	}
	
	
	
	
	
}
