package com.tsg.flooring.service;

import java.time.LocalDate;
import java.util.List;

import com.tsg.flooring.dao.FlooringPersistenceException;
import com.tsg.flooring.dto.Order;
import com.tsg.flooring.dto.Product;
import com.tsg.flooring.dto.Tax;

public interface FlooringServiceLayer {
	
	
	List<Order> getOrderListByDate(LocalDate ld) throws FlooringPersistenceException;
	
	Order getParticularOrder(LocalDate date, Integer ordNum) throws FlooringPersistenceException;
	
	List<Product> getProductList() throws FlooringPersistenceException;
	
	List<Tax> getTaxList() throws FlooringPersistenceException;
	
	Order createOrder(LocalDate date, Order order) throws FlooringPersistenceException, NoStateException, NoProductException;
	
	boolean placeOrder(LocalDate date, Integer num, Order order, Character select) throws FlooringPersistenceException;
	
	Order editOrder(Integer ordNumber, Order order, LocalDate date) throws NoProductException;
	
	boolean replaceEditedOrder(LocalDate date, Integer num, Order order, Character select) throws FlooringPersistenceException;
}
