package com.tsg.flooring.controller;

import java.io.FileNotFoundException;
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

	public void run() throws FlooringPersistenceException, NoStateException, NoProductException, FileNotFoundException {
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
				remove();
				break;
			case 5:
				export();
				break;
			case 6:
				System.exit(0);
				break;
			}
		}
	}
	
	private void list() throws FlooringPersistenceException {
		List<Order> orderList = service.getOrderListByDate(view.dateOfOrder());
		view.printOrdersList(orderList);
		view.printWait();
	}
	private void add() throws FlooringPersistenceException, NoStateException, NoProductException {
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
	private void edit() throws FlooringPersistenceException, NoStateException, NoProductException {
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
			view.printOrder(forEditOrder);   // show him to user
			view.printProductList(prod2);    // show available products
			view.printAvailableStates(tax2); // show in which state the product is available
			//ask new info
			Order editedOrder = view.getEditOrderInfo(forEditOrder, prod2, tax2);
			//edit
			Order fullEditedOrder = service.editOrder(editOrdNumber, editedOrder, editDate);
			//ask if sure for edit
			Character editSelection = view.getSelectionEditOrder();// y/n
			boolean replace = service.replaceEditedOrder(editDate, editOrdNumber, fullEditedOrder, editSelection);
			//print editing result
			view.printEditingResult(replace);
		}
	}
	private void remove() throws FlooringPersistenceException {
		LocalDate orderDate = view.dateOfOrder(); //date of order
		List<Order> orderList = service.getOrderListByDate(orderDate); //list of orders for this date
		if (!orderList.isEmpty()) {
			view.printOrdersList(orderList); // display list of orders
			Integer remOrderNumber = view.orderRemoveNumber(); // number of order
			Order remOrder = service.getParticularOrder(orderDate, remOrderNumber); // order to remove
			view.printOrder(remOrder); // show order to remove to user
			Character removeSelection = view.getSelectionRemoveOrder();//y/n
			boolean remove = service.removeOrder(orderDate, remOrderNumber, remOrder, removeSelection);
			view.printRemovingResult(remove);//result
		}
	}
	private void export() throws FileNotFoundException, FlooringPersistenceException {
		view.printDataExportResult(service.exportData(view.getExportConfirmation()));
	}
	
}
