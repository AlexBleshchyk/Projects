package com.tsg.flooring.ui;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tsg.flooring.dto.Order;
import com.tsg.flooring.dto.Product;

@Component
public class FlooringView {
	UserIO io;
	@Autowired
	public FlooringView(UserIO io) {
		this.io = io;
	}
	
	/*--------MAIN MENU------------*/
	public int printMenuGetSelection() {
		io.print("~~~~~~~~~~~~~~~~~~~~~~~~~~");
		io.print("|  <<Flooring Program>>  |");
		io.print("~~~~~~~~~~~~~~~~~~~~~~~~~~");
		io.print("| 1. Display orders      |");
		io.print("| 2. Add an order        |");
		io.print("| 3. Edit an order       |");
		io.print("| 4. Remove an order     |");
		io.print("| 5. Export All Data     |");
		io.print("| 6. Quit                |");
		io.print("~~~~~~~~~~~~~~~~~~~~~~~~~~");
		
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
		io.print("------------------------------------------------------------------------------------------------------------------------");
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
			io.print("------------------------------------------------------------------------------------------------------------------------");
			
		}
	}
	
	/*--PRINT LIST oF PRODUCT*/
	
	public void printProductList(List<Product> productList) {
		io.print("\nProduct Type | Cost/sq.ft. |  Labor_Cost/sq.ft. |");
		io.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		for(Product currentProduct : productList) {
			String productInfo = String.format("%12s | %11s | %18s |",
					currentProduct.getProductType(),
					currentProduct.getCostPerSquareFoot(),
					currentProduct.getLaborCostPerSquareFoot());
			io.print(productInfo);
		}
	}
	
	
	/*---ASK Date of Orders--- */
	public LocalDate dateOfOrder() {
		return io.readLocalDate("Enter date[yyyy-MM-dd]");
	}
	
	/*-------print msg PRESS ENTER FOR CONTINUE----*/
	public void printWait() {
		io.print(" ");
		io.readString("Press Enter for continue...");
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
