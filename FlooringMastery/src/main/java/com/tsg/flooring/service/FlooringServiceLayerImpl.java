package com.tsg.flooring.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tsg.flooring.dao.FlooringAuditDao;
import com.tsg.flooring.dao.FlooringOrderDao;
import com.tsg.flooring.dao.FlooringPersistenceException;
import com.tsg.flooring.dao.FlooringProductDao;
import com.tsg.flooring.dao.FlooringTaxDao;
import com.tsg.flooring.dto.Order;
import com.tsg.flooring.dto.Product;
import com.tsg.flooring.dto.Tax;

@Component
public class FlooringServiceLayerImpl implements FlooringServiceLayer{
	private FlooringAuditDao auditDao;
	private FlooringOrderDao orderDao;
	private FlooringProductDao productDao;
	private FlooringTaxDao taxDao;
	
	@Autowired
	public FlooringServiceLayerImpl(
			FlooringAuditDao auditDao,
			FlooringOrderDao orderDao,
			FlooringProductDao productDao,
			FlooringTaxDao taxDao) {
		this.auditDao = auditDao;
		this.orderDao = orderDao;
		this.productDao = productDao;
		this.taxDao = taxDao;
	}

	public FlooringServiceLayerImpl() {
	}

	@Override
	public List<Order> getOrderListByDate(LocalDate orderDate) throws FlooringPersistenceException {
		auditDao.writeAuditEntry("The list of orders on " + orderDate + " is loaded.");
		return orderDao.getOrderList(orderDate);
	}
	
	@Override
	public Order getParticularOrder(LocalDate date, Integer ordNum) throws FlooringPersistenceException {
		Order partOrder= orderDao.getParticularOrder(date, ordNum);
		return partOrder;
	}

	@Override
	public List<Product> getProductList() throws FlooringPersistenceException {
		auditDao.writeAuditEntry("The list of product types is loaded.");
		return productDao.getProductList();
	}
	
	@Override
	public List<Tax> getTaxList() throws FlooringPersistenceException {
		auditDao.writeAuditEntry("The list of taxes is loaded.");
		return taxDao.getTaxList();
	}
	
	/*------------------ADD---------------------------*/
	@Override
	public Order createOrder(LocalDate date,Order order) throws FlooringPersistenceException, NoStateException, NoProductException{
		Order currOrder = order;
		
		/*validation if exists other orders on this date and getting number*/
		List<Order> orderListOnDate = orderDao.getOrderList(date);
		Integer maxNum = 0;
		if (!orderListOnDate.isEmpty()) {
			List<Integer> nums = new ArrayList<>();
			for(Order curOrd : orderListOnDate) {
				nums.add(curOrd.getOrderNumber());
			}
			maxNum = Collections.max(nums);
		}
		Integer orderNumber = maxNum + 1;
		currOrder.setOrderNumber(orderNumber);
		
		BigDecimal taxeRate = taxDao.getParticularTax(currOrder.getState()).getTaxRate(); 
		BigDecimal costPerSqFt;
		try {
			costPerSqFt = productDao.getParticularProduct(currOrder.getProductType()).getCostPerSquareFoot();
		}catch(NullPointerException e) {
			throw new NoProductException("There is no such product type ");
		}
			BigDecimal laborCostPerSqFt = productDao.getParticularProduct(currOrder.getProductType()).getLaborCostPerSquareFoot();
		
		currOrder.setTaxRate(taxeRate);
		currOrder.setCostPerSquareFoot(costPerSqFt);
		currOrder.setLaborCostPerSquareFoot(laborCostPerSqFt);
		
		BigDecimal materialCost = currOrder.getArea().multiply(costPerSqFt).setScale(2, RoundingMode.HALF_UP);
		currOrder.setMaterialCost(materialCost);
		
		BigDecimal laborCost = currOrder.getArea().multiply(laborCostPerSqFt).setScale(2, RoundingMode.HALF_UP);
		currOrder.setLaborCost(laborCost);
		
		BigDecimal tax = materialCost.add(laborCost).multiply(taxeRate.divide(new BigDecimal("100"))).setScale(2, RoundingMode.HALF_UP);
		currOrder.setTax(tax);
		
		BigDecimal total = materialCost.add(laborCost).add(tax).setScale(2,RoundingMode.HALF_UP);
		currOrder.setTotal(total);
		
		auditDao.writeAuditEntry("Order #" + order.getOrderNumber() + " on " + date + " is created. ");
		return currOrder;
	}
	/*-----------------------PLACE--------------------------*/
	@Override
	public boolean placeOrder(LocalDate date, Integer num, Order order, Character select) throws FlooringPersistenceException {
		switch(select) {
		case 'Y':
		case 'y':
			orderDao.addOrder(num, order, date);
			auditDao.writeAuditEntry("Order #" + order.getOrderNumber() + " on " + date +" is placed.");
			return true;
		case 'N':
		case 'n':
			return false;
		default:
			return false;
		}
	}
	
	/*------------------EDIT---------------------------------------*/
	@Override
	public Order editOrder(Integer ordNumber, Order order, LocalDate date) throws NoProductException {
		Order currOrder = order;
		
		BigDecimal taxeRate = taxDao.getParticularTax(currOrder.getState()).getTaxRate(); 
		BigDecimal costPerSqFt;
		try {
			costPerSqFt = productDao.getParticularProduct(currOrder.getProductType()).getCostPerSquareFoot();
		}catch(NullPointerException e) {
			throw new NoProductException("There is no such product type ");
		}
		BigDecimal laborCostPerSqFt = productDao.getParticularProduct(currOrder.getProductType()).getLaborCostPerSquareFoot();
		
		currOrder.setTaxRate(taxeRate);
		currOrder.setCostPerSquareFoot(costPerSqFt);
		currOrder.setLaborCostPerSquareFoot(laborCostPerSqFt);
		
		BigDecimal materialCost = currOrder.getArea().multiply(costPerSqFt).setScale(2, RoundingMode.HALF_UP);
		currOrder.setMaterialCost(materialCost);
		
		BigDecimal laborCost = currOrder.getArea().multiply(laborCostPerSqFt).setScale(2, RoundingMode.HALF_UP);
		currOrder.setLaborCost(laborCost);
		
		BigDecimal tax = materialCost.add(laborCost).multiply(taxeRate.divide(new BigDecimal("100"))).setScale(2, RoundingMode.HALF_UP);
		currOrder.setTax(tax);
		
		BigDecimal total = materialCost.add(laborCost).add(tax).setScale(2,RoundingMode.HALF_UP);
		currOrder.setTotal(total);
		return currOrder;
	}
	
	@Override
	public boolean replaceEditedOrder(LocalDate date, Integer num, Order order, Character select) throws FlooringPersistenceException {
		switch(select) {
		case 'Y':
		case 'y':
			orderDao.editOrder(num, order, date);
			auditDao.writeAuditEntry("Order #" + order.getOrderNumber() + " on " + date +" is edited.");
			return true;
		case 'N':
		case 'n':
			return false;
		default:
			return false;
		}
	}

	

	



	
	
	
	
}
