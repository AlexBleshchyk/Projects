package com.tsg.flooring.dao;

import java.util.List;

import com.tsg.flooring.dto.Tax;

public interface FlooringTaxDao {
	
	List<Tax> getTaxList();
	
	Tax getParticularTax(String state);
}
