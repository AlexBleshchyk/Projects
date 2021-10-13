package com.tsg.flooring.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tsg.flooring.dao.FlooringOrderDaoImpl;
import com.tsg.flooring.dao.FlooringPersistenceException;
import com.tsg.flooring.dao.FlooringProductDao;
import com.tsg.flooring.dao.FlooringProductDaoImpl;
import com.tsg.flooring.dao.FlooringTaxDao;
import com.tsg.flooring.dao.FlooringTaxDaoImpl;
import com.tsg.flooring.dto.Order;
import com.tsg.flooring.dto.Product;
import com.tsg.flooring.dto.Tax;


class FlooringServiceLayerImplTest {
	private static FlooringServiceLayer testService;
	private static FlooringTaxDao testTaxDao;
	private static FlooringOrderDaoImpl testOrderDao;
	private static FlooringProductDao testProductDao;
	
	
	public static final String AUDIT_FILE = "test_audit.txt"; 
	
	public FlooringServiceLayerImplTest() {
	}
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		testService = new FlooringServiceLayerImpl();
		testTaxDao = new FlooringTaxDaoImpl();
		testProductDao = new FlooringProductDaoImpl();
		testOrderDao = new FlooringOrderDaoImpl();
	}
	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetProductList() throws FlooringPersistenceException {
		//act
		List<Product> testList = testProductDao.getProductList();
		//assert
		assertNotNull(testList);
		assertFalse(testList.isEmpty());
	}
	
	@Test
	void testGetTaxList() {
		List<Tax> testTaxList = testTaxDao.getTaxList();
		assertNotNull(testTaxList);
		assertFalse(testTaxList.isEmpty());
	}
	
	@Test
	void testCreateOrder() throws FlooringPersistenceException, NoStateException, NoProductException {
		//arrange
		
		//Map<Integer, Order> ordersOnDate = new HashMap<>();
		
		Order testNewOrder = new Order();
		testNewOrder.setCustomerName("Alex");
		testNewOrder.setState("CA");
		testNewOrder.setProductType("Wood");
		testNewOrder.setArea(new BigDecimal("120"));
		LocalDate ld = LocalDate.parse("2021-12-12");
		//act
		testOrderDao.getOrderList(ld);
		Order createdOrder = testService.createOrder(ld, testNewOrder);
		//assert
		assertNotNull(createdOrder);
		
	}

	
	
	
	
	
}
