package com.tsg.flooring.service;

import java.time.LocalDate;
import java.util.List;

import com.tsg.flooring.dao.FlooringPersistenceException;
import com.tsg.flooring.dto.Order;
import com.tsg.flooring.dto.Product;

public interface FlooringServiceLayer {
	
	
	List<Order> getOrderListByDate(LocalDate ld) throws FlooringPersistenceException;
	
	List<Product> getProductList() throws FlooringPersistenceException;
}
