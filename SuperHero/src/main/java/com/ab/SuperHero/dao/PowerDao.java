package com.ab.SuperHero.dao;

import java.util.List;

import com.ab.SuperHero.dto.Power;

public interface PowerDao {
	
	Power addPower(Power power);
	
	void updatePower(Power power);
	
	void deletePower(int powerId);
	
	Power getPowerById(int powerId);
	
	List<Power> getAllPower();
	
	List<Power> getAllPowerByHero(int heroId);
	
}
