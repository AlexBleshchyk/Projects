package com.tsg.flooring.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
	private static final String BACKUP_FILE = "Backup/DataExport.txt";
	private static final String DELIMITER = ",";
	
	private Map<Integer, Order> ordersOnDate = new HashMap<>();
	
	/*---------get ORDERS LIST by date------------*/
	@Override
	public List<Order> getOrderList(LocalDate orderDate) throws FlooringPersistenceException{
		ordersOnDate.clear();
		loadOrders(orderDate);
		return new ArrayList<Order>(ordersOnDate.values());
	}
	
	/*---------LIST of current orders(in memory)-----------*/
	@Override
	public List<Order> getCurrentOrdersList() {
		return new ArrayList<>(ordersOnDate.values());
	}

	/*-------------get PARTICULAR ORDER------------ */
	@Override
	public Order getParticularOrder(LocalDate orderDate, Integer orderNumber) throws FlooringPersistenceException {
		ordersOnDate.clear();
		loadOrders(orderDate);
		return ordersOnDate.get(orderNumber);
	}
	
	/*----------------------ADD ORDER------------------------*/
	@Override
	public Order addOrder(Integer orderNumber, Order order, LocalDate date) {
		Order addedOrder = ordersOnDate.put(orderNumber, order);
		writeOrder(date);
		return addedOrder;
	}
	
	/*----------------------EDIT--------------------------*/
	@Override
	public Order editOrder(Integer orderNumber, Order order, LocalDate date) throws FlooringPersistenceException {
		loadOrders(date);
		Order editedOrder = ordersOnDate.replace(orderNumber, order);
		writeOrder(date);
		return editedOrder;
	}
	
	/*----------------------REMOVE-------------------------*/
	@Override
	public boolean removeOrder(Integer orderNumber, Order order, LocalDate date) throws FlooringPersistenceException {
		loadOrders(date);
		boolean removedOrder = ordersOnDate.remove(orderNumber, order);
		writeOrder(date);
		return removedOrder;
	}
	
	/*----------------------EXPORT------------------------------------*/
	@Override
	public void dataExport() throws FileNotFoundException {
		dataReadWrite();
	}

/*----------------------------------------------------------PRIVATE--METHODS-------------------------------------------------------------------------*/
	/*------------UNMARSHALL--------------------*/
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
	
	/*-------LOAD ORDERS from file into memory---------*/
	private void loadOrders(LocalDate orderDate) throws FlooringPersistenceException {
		Scanner scan = null;
		DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("MMddyyyy");
		String formatted = orderDate.format(myFormat);
		ORDER_FILE = "Orders/Orders_" + formatted + ".txt";
		try {
			scan = new Scanner(new BufferedReader(new FileReader(ORDER_FILE))); 
		}catch(FileNotFoundException e) {
			if(scan == null) {
				io.print("\nThere are no orders for this date[" + orderDate + "].");
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
	
	/*---------------MARSHALL-------------------------------------*/
	private String marshal(Order adOrder) {
		String orderAsText = adOrder.getOrderNumber() + DELIMITER;
		orderAsText += adOrder.getCustomerName() + DELIMITER;
		orderAsText += adOrder.getState() + DELIMITER;
		orderAsText += adOrder.getTaxRate() + DELIMITER;
		orderAsText += adOrder.getProductType() + DELIMITER;
		orderAsText += adOrder.getArea() + DELIMITER;
		orderAsText += adOrder.getCostPerSquareFoot() + DELIMITER;
		orderAsText += adOrder.getLaborCostPerSquareFoot() + DELIMITER;
		orderAsText += adOrder.getMaterialCost() + DELIMITER;
		orderAsText += adOrder.getLaborCost() + DELIMITER;
		orderAsText += adOrder.getTax() + DELIMITER;
		orderAsText += adOrder.getTotal();
		
		return orderAsText;
		
	}
		
	/*----------------Write order into file------------------------*/
	private void writeOrder(LocalDate date) {
		PrintWriter out = null;
		DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("MMddyyyy");
		String formatted = date.format(myFormat);
		ORDER_FILE = "Orders/Orders_" + formatted + ".txt";
		
		try {
			out = new PrintWriter(new FileWriter(ORDER_FILE));
		}catch(IOException e) {
			if(out == null) {
				io.print("Could not save order.");
				return;
			}
		}
		String firstLine = "OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total";
		out.println(firstLine);

		String orderAsText;
		List<Order> orderList = this.getCurrentOrdersList();
		for(Order curOrd : orderList) {
			orderAsText = marshal(curOrd);
			out.println(orderAsText);
			out.flush();
		}
		out.close();
		
	}
	
	/*---------------------------DATA EXPORT------------------------*/
	
	private void dataReadWrite() throws FileNotFoundException {
		Scanner scan = null;
		PrintWriter out = null;
		File folder = new File("Orders");
		File[] listOfFiles = folder.listFiles();
		
		// writer
		try {
			out = new PrintWriter(new FileWriter(BACKUP_FILE));
		}catch(IOException e) {
			if(out == null) {
                io.print("Could not export data.");
                return;
            }
		}
		
		String firstLine = "OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total,OrderDate";
		out.println(firstLine);
		
		for (File file : listOfFiles) {
			if (file.isFile()) {
				String fileName = file.getName().replaceAll("\\D+", "");
				LocalDate fileDate = LocalDate.parse(fileName,DateTimeFormatter.ofPattern("MMddyyyy"));
				
				scan = new Scanner(new BufferedReader(new FileReader(file)));
				scan.nextLine(); // skip first line
				
				String currentLine;
				while (scan.hasNextLine()) {
					currentLine = scan.nextLine();
					String orderToPrint = currentLine + DELIMITER + fileDate.toString();
					out.println(orderToPrint);
				}	
			}
		}
		out.flush();
		out.close();
		scan.close();
	}
}
