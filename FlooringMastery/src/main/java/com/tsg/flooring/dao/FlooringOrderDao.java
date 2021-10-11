package com.tsg.flooring.dao;

import java.time.LocalDate;
import java.util.List;

import com.tsg.flooring.dto.Order;

public interface FlooringOrderDao {
	
	List<Order> getOrderList(LocalDate orderDate) throws FlooringPersistenceException;
	
	Order getParticularOrder(LocalDate orderDate, Integer orderNumber) throws FlooringPersistenceException;
	
	Order addOrder(Integer orderNumber);
}