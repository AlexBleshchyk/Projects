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
				list();
				break;
			case 2:
				add();
				break;
			case 3:
				edit();
				break;
			case 4:
				//remove order
				break;
			case 5:
				//export all data
				break;
			case 6:
				System.exit(0);
				break;
			}
		}
	}
	
	
	
	public void list() throws FlooringPersistenceException {
		List<Order> orderList = service.getOrderListByDate(view.dateOfOrder());
		view.printOrdersList(orderList);
		view.printWait();
	}
	
	public void add() throws FlooringPersistenceException, NoStateException, NoProductException {
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
	}
	
	public void edit() throws FlooringPersistenceException, NoStateException, NoProductException {
		List<Product> prod2 = service.getProductList();
		List<Tax> tax2 = service.getTaxList();
		//ask date
		LocalDate editDate = view.dateOfOrder();
		//List orders on this date
		List<Order> dateList = service.getOrderListByDate(editDate);
		if (!dateList.isEmpty()) {		
			view.printOrdersList(dateList);
			//ask number of order which want to edit
			Integer editOrdNumber = view.orderNumber();
			//order for edit
			Order forEditOrder = service.getParticularOrder(editDate, editOrdNumber);
			view.printOrder(forEditOrder); //show him to user
			//ask new info
			Order editedOrder = view.getEditOrderInfo(forEditOrder, prod2, tax2, null);
			//edit
			Order fullEditedOrder = service.editOrder(editOrdNumber, editedOrder, editDate);
			//ask if sure for edit
			Character editSelection = view.getSelectionEditOrder();
			boolean replace = service.replaceEditedOrder(editDate, editOrdNumber, fullEditedOrder, editSelection);
			//print editing result
			view.printEditingResult(replace);
		}
	}
	
}
