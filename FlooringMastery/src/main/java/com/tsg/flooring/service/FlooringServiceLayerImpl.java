package com.tsg.flooring.service;

import java.time.LocalDate;
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

	@Override
	public List<Order> getOrderListByDate(LocalDate orderDate) throws FlooringPersistenceException {
		auditDao.writeAuditEntry("The list of orders on " + orderDate + " is loaded.");
		return orderDao.getOrderList(orderDate);
	}

	@Override
	public List<Product> getProductList() throws FlooringPersistenceException {
		auditDao.writeAuditEntry("The list of product types is loaded.");
		return productDao.getProductList();
	}
	
	
	
}
