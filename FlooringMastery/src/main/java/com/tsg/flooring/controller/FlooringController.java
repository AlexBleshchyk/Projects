package com.tsg.flooring.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tsg.flooring.dao.FlooringPersistenceException;
import com.tsg.flooring.dto.Order;
import com.tsg.flooring.dto.Product;
import com.tsg.flooring.dto.Tax;
import com.tsg.flooring.service.FlooringServiceLayer;
import com.tsg.flooring.service.NoProductException;
import com.tsg.flooring.service.NoStateException;
import com.tsg.flooring.ui.FlooringView;

/**
 * @author Alexandr Bleshchyk
 *
 */
@Component
public class FlooringController {
	private FlooringView view;
	private FlooringServiceLayer service;
	@Autowired
	public FlooringController(FlooringView view, FlooringServiceLayer service) {
		this.view = view;
		this.service = service;
	}
	
	public void run() throws FlooringPersistenceException, NoStateException, NoProductException {
		boolean keepGoing = true;
		int selection = 0;
		
		while(keepGoing) {
			selection = view.printMenuGetSelection();
			
			switch(selection) {
			case 1:
				List<Order> orderList = service.getOrderListByDate(view.dateOfOrder());
				view.printOrdersList(orderList);
				view.printWait();
				break;
			case 2:
				List<Product> prod = service.getProductList();
				List<Tax> tax = service.getTaxList();
				view.printProductList(prod);       //product list
				view.printAvailableStates(tax);    // states
				
				LocalDate date = view.dateForNewOrder(); // ask date
				Order newOrder = service.createOrder(date, view.getNewOrderInfo(prod, tax)); // create new order
				
				view.printOrder(newOrder); //display new order
				
				Character placeSelection = view.getSelectionPlaceOrder(); // ask if want place an order
							
				boolean place = service.placeOrder(date, newOrder.getOrderNumber(), newOrder, placeSelection); //placing of order
				view.printAddingResult(place); // placing result 
				view.printWait();
				break;
			case 3:
				//edit order
				break;
			case 4:
				//remove order
				break;
			case 5:
				//export all data
				break;
			case 6:
				//Quit
				System.exit(0);
				break;
			}
		}
	}
	
	
}
