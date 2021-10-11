package com.tsg.flooring.dao;

import java.util.List;

import com.tsg.flooring.dto.Product;

public interface FlooringProductDao {
	
	List<Product> getProductList();
	
	Product getParticularProduct(String productType);
}
