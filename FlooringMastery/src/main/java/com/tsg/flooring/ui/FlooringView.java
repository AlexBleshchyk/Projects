package com.tsg.flooring.ui;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tsg.flooring.dto.Order;
import com.tsg.flooring.dto.Product;
import com.tsg.flooring.dto.Tax;
import com.tsg.flooring.service.NoStateException;

@Component
public class FlooringView {
	UserIO io;
	@Autowired
	public FlooringView(UserIO io) {
		this.io = io;
	}
	
	/*--------MAIN MENU------------*/
	public int printMenuGetSelection() {
		io.print("+----------------------------+");
		io.print("|    <<Flooring Program>>    |");
		io.print("+----------------------------+");
		io.print("|   1. Display orders        |");
		io.print("|   2. Add an order          |");
		io.print("|   3. Edit an order         |");
		io.print("|   4. Remove an order       |");
		io.print("|   5. Export All Data       |");
		io.print("|   6. Quit                  |");
		io.print("+----------------------------+");
		
		return io.readInt("Choose from above.", 1, 6);
	}
	
	/* display
	 * add
	 * edit
	 * remove
	 * export
	 * 
	 * */
	/*--PRINT LIST OF ORDERS---*/
	public void printOrdersList(List<Order> orderList) {
		io.print("\nNumber|CustomerName|State|TaxRate|ProductType|  Area|Cost/sq.ft.|LaborCost/sq.ft.|MaterialCost|LaborCost|   Tax|   Total");
		io.print("------+------------+-----+-------+-----------+------+-----------+----------------+------------+---------+------+--------");
		for(Order currentOrder : orderList) {
			String orderInfo = String.format("%6s|%12s|%5s|%7s|%11s|%6s|%11s|%16s|%12s|%9s|%3s|%8s",
					currentOrder.getOrderNumber(),
					currentOrder.getCustomerName(),
					currentOrder.getState(),
					currentOrder.getTaxRate(),
					currentOrder.getProductType(),
					currentOrder.getArea(),
					currentOrder.getCostPerSquareFoot(),
					currentOrder.getLaborCostPerSquareFoot(),
					currentOrder.getMaterialCost(),
					currentOrder.getLaborCost(),
					currentOrder.getTax(),
					currentOrder.getTotal());
			
			io.print(orderInfo);
			io.print("------+------------+-----+-------+-----------+------+-----------+----------------+------------+---------+------+--------");
			
		}
	}
	
	/*--PRINT ORDER---*/
	public void printOrder(Order order) {
		io.print("\nNumber|CustomerName|State|TaxRate|ProductType|  Area|Cost/sq.ft.|LaborCost/sq.ft.|MaterialCost|LaborCost|   Tax|   Total");
		io.print("------+------------+-----+-------+-----------+------+-----------+----------------+------------+---------+------+--------");
		Order currentOrder = order; 
		String orderInfo = String.format("%6s|%12s|%5s|%7s|%11s|%6s|%11s|%16s|%12s|%9s|%4s|%8s",
			currentOrder.getOrderNumber(),
			currentOrder.getCustomerName(),
			currentOrder.getState(),
			currentOrder.getTaxRate(),
			currentOrder.getProductType(),
			currentOrder.getArea(),
			currentOrder.getCostPerSquareFoot(),
			currentOrder.getLaborCostPerSquareFoot(),
			currentOrder.getMaterialCost(),
			currentOrder.getLaborCost(),
			currentOrder.getTax(),
			currentOrder.getTotal());
		
		io.print(orderInfo);
		io.print("------+------------+-----+-------+-----------+------+-----------+----------------+------------+---------+------+--------");
			
	}


	
	/*--PRINT LIST oF PRODUCT*/
	
	public void printProductList(List<Product> productList) {
		io.print("\nProduct Type | Cost/sq.ft. |  Labor_Cost/sq.ft. |");
		io.print("-------------+-------------+---------------------");
		for(Product currentProduct : productList) {
			String productInfo = String.format("%12s | %11s | %18s |",
					currentProduct.getProductType(),
					currentProduct.getCostPerSquareFoot(),
					currentProduct.getLaborCostPerSquareFoot());
			io.print(productInfo);
		}
		io.print("-------------+-------------+---------------------");
	}
	
	/*--PRINT LIST oF tax*/
	
	public void printTaxList(List<Tax> taxList) {
		io.print("\nState | State Name | Tax Rate |");
		io.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		for(Tax currentTax : taxList) {
			String productInfo = String.format("%5s | %10s | %8s |",
					currentTax.getState(),
					currentTax.getStateName(),
					currentTax.getTaxRate());
			io.print(productInfo);
		}
	}
	
	/*---print available States*/
	public void printAvailableStates(List<Tax> taxList) {
		io.print("Available for this States:");
		for(Tax currentTax : taxList) {
			String productInfo = String.format("%1s", currentTax.getState());
			io.print(productInfo);
		}
	}
	
	
	/*----GET INFO FOR NEW ORDER----*/
	public Order getNewOrderInfo(List<Product> prod, List<Tax> tax) throws NoStateException {
		// name
		String customerName = io.readString("Please enter name");
		// state
		String state = null;
		List<String> states = new ArrayList<>() ;
		for(Tax currentTax : tax) { 
			states.add(currentTax.getState());
		}
		do {
			state = io.readString("Please enter state(abbreviation)");
		}while(!states.contains(state.toUpperCase()));
		//product type
		String productType;
		List<String> products = new ArrayList<>();
		for(Product currentProduct : prod) {
			products.add(currentProduct.getProductType());
		}
		do {
			productType = io.readString("Please enter type of product");
		}while(!products.contains(productType));
		
		
		// area
		BigDecimal area = io.readBigDecimal("Please enter area(min 100 sq.ft.)", new BigDecimal("100"));
		Order currentOrder = new Order();
		currentOrder.setCustomerName(customerName);
		currentOrder.setState(state.toUpperCase());
		currentOrder.setProductType(productType);
		currentOrder.setArea(area);
		
		return currentOrder;
		
	}
	/*--GET Selection for Place Order--*/
	public Character getSelectionPlaceOrder() {
		Character c;
		do {
			c = io.readChar("Do you want to place your order?[Y/n]");
		}while((!c.equals('y'))&&(!c.equals('Y'))&&(!c.equals('n'))&&(!c.equals('N')));
		return c;
	}
	/*---GET Date of Orders--- */
	public LocalDate dateOfOrder() {
		return io.readLocalDate("\nEnter date[yyyy-MM-dd]");
	}
	/*---GET Date for new order--- */
	public LocalDate dateForNewOrder() {
		return io.readLocalDate("\nEnter new order date[yyyy-MM-dd](Must be in the future)", LocalDate.now().plusDays(1));
	}
	
	/*----PRINT ORDER ADDING RESULT-----*/
	public void printAddingResult(boolean result) {
		if(result) {
			io.print("Your order was successfully placed.");
		}else {
			io.print("The operation was cancelled by the user.");
		}
	} 
	/*-------print msg PRESS ENTER FOR CONTINUE----*/
	public void printWait() {
		io.print(" ");
		io.readString("Press Enter for continue...");
	}
	
	/*--Order is canceled--*/
	public void printCancel() {
		io.print("\n The operation is cancelled.");
	}
	
	public void displayUnknownCommand() {
		io.print("UNKNOWN COMMAND");
	}

	public void goodByeMessage() {
		io.print("Good Bye!");
	}
	
	public void errorMessage() {
		io.print("Error!");
	}
}
