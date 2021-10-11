package com.tsg.flooring.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.springframework.stereotype.Component;

import com.tsg.flooring.dto.Order;
import com.tsg.flooring.ui.UserIO;
import com.tsg.flooring.ui.UserIOConsoleImpl;

@Component
public class FlooringOrderDaoImpl implements FlooringOrderDao{
	UserIO io = new UserIOConsoleImpl();
	
	private String ORDER_FILE;
	private static final String DELIMITER = ",";
	
	private Map<Integer, Order> ordersOnDate = new HashMap<>();
	
	/*--GET ORDERS LIST BY DATE--*/
	@Override
	public List<Order> getOrderList(LocalDate orderDate) throws FlooringPersistenceException{
		ordersOnDate.clear();
		loadOrders(orderDate);
		return new ArrayList<Order>(ordersOnDate.values());
	}
	/*---GET PARTICULAR ORDER--- */
	@Override
	public Order getParticularOrder(LocalDate orderDate, Integer orderNumber) throws FlooringPersistenceException {
		ordersOnDate.clear();
		loadOrders(orderDate);
		return ordersOnDate.get(orderNumber);
	}
	
	/*----ADD ORDER----*/
	@Override
	public Order addOrder(Integer orderNumber) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	/*--UNMARSHAL--*/
	private Order unmarshalOrder(String orderAsText) {
		String[] orderTokens = orderAsText.split(DELIMITER);
		Integer orderNumber = Integer.parseInt(orderTokens[0]);
		Order orderFromFile = new Order(orderNumber);
		orderFromFile.setOrderNumber(orderNumber);
		orderFromFile.setCustomerName(orderTokens[1]);
		orderFromFile.setState(orderTokens[2]);
		orderFromFile.setTaxRate(new BigDecimal(orderTokens[3]));
		orderFromFile.setProductType(orderTokens[4]);
		orderFromFile.setArea(new BigDecimal(orderTokens[5]));
		orderFromFile.setCostPerSquareFoot(new BigDecimal(orderTokens[6]));
		orderFromFile.setLaborCostPerSquareFoot(new BigDecimal(orderTokens[7]));
		orderFromFile.setMaterialCost(new BigDecimal(orderTokens[8]));
		orderFromFile.setLaborCost(new BigDecimal(orderTokens[9]));
		orderFromFile.setTax(new BigDecimal(orderTokens[10]));
		orderFromFile.setTotal(new BigDecimal(orderTokens[11]));
				
		return orderFromFile;
	}
	
	
	/*--LOAD ORDERS from file into memory--*/
	private void loadOrders(LocalDate orderDate) throws FlooringPersistenceException {
		Scanner scan = null;
		DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("MMddyyyy");
		String formatted = orderDate.format(myFormat);
		ORDER_FILE = "Orders/Orders_" + formatted + ".txt";
		try {
			scan = new Scanner(new BufferedReader(new FileReader(ORDER_FILE))); 
		}catch(FileNotFoundException e) {
			if(scan == null) {
				io.print("\nThere are no orders for this date.");
				return;
			}
		}
		scan.nextLine();
		
		String currentLine;
		Order currentOrder;
		
		while(scan.hasNextLine()) {
			currentLine = scan.nextLine();
			currentOrder = unmarshalOrder(currentLine);
			ordersOnDate.put(currentOrder.getOrderNumber(), currentOrder);
		}
	}




	
}
