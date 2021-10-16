package com.tsg.flooring.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tsg.flooring.FlooringAuditDaoStubImpl;
import com.tsg.flooring.dao.FlooringAuditDao;
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
	private static FlooringAuditDao testAuditDao;
	
	
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
		testTaxDao = new FlooringTaxDaoImpl();
		testProductDao = new FlooringProductDaoImpl();
		testOrderDao = new FlooringOrderDaoImpl();
		testAuditDao = new FlooringAuditDaoStubImpl();
		testService = new FlooringServiceLayerImpl(testAuditDao, testOrderDao, testProductDao, testTaxDao);
	}
	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetOrderListByDate() throws FlooringPersistenceException {
		//arrange
		LocalDate ld = LocalDate.parse("2021-10-15");
		//act
		List<Order> testOrderList = testService.getOrderListByDate(ld);
		//assert
		assertNotNull(testOrderList);
	}
	
	@Test
	void testGetParticularOrder() throws FlooringPersistenceException {
		//arrange
		LocalDate ld = LocalDate.parse("2021-10-15");
		//act
		Order testOrder = testService.getParticularOrder(ld, 1);
		//assert
		assertNotNull(testOrder);
	}
	
	@Test
	void testGetProductList() throws FlooringPersistenceException {
		//act
		List<Product> testList = testService.getProductList();
		//assert
		assertNotNull(testList);
		assertFalse(testList.isEmpty());
	}
	
	@Test
	void testGetTaxList() throws FlooringPersistenceException {
		List<Tax> testTaxList = testService.getTaxList();
		assertNotNull(testTaxList);
		assertFalse(testTaxList.isEmpty());
	}
	
	@Test
	void testCreatePlaceOrder() throws FlooringPersistenceException, NoStateException, NoProductException {
		//arrange
		LocalDate addDate = LocalDate.now().plusDays(1);
		Order testOrder = new Order();
		testOrder.setOrderNumber(1);
		testOrder.setCustomerName("John");
		testOrder.setProductType("Laminate");
		testOrder.setState("ON");
		testOrder.setArea(new BigDecimal("200"));
		testOrder.setTaxRate(new BigDecimal("11"));
		testOrder.setCostPerSquareFoot(new BigDecimal("2"));
		testOrder.setLaborCostPerSquareFoot(new BigDecimal("3"));
		testOrder.setMaterialCost(new BigDecimal("875"));
		testOrder.setLaborCost(new BigDecimal("1050"));
		testOrder.setTax(new BigDecimal("211"));
		testOrder.setTotal(new BigDecimal("2200"));		
		//act
		Order createdOrder = testService.createOrder(addDate, testOrder);
		boolean placeResult = testService.placeOrder(addDate, createdOrder.getOrderNumber(), createdOrder, 'y');
		//assert
		assertNotNull(createdOrder);
		assertEquals(testOrder.getCustomerName(), createdOrder.getCustomerName());
		assertTrue(placeResult);
	}
	
	@Test
	void testEditReplaceOrder() throws NoProductException, FlooringPersistenceException {
		//arrange
		LocalDate editDate = LocalDate.now().plusDays(1);
		Order testOrder = new Order();
		testOrder.setOrderNumber(1);
		testOrder.setCustomerName("John");
		testOrder.setProductType("Laminate");
		testOrder.setState("ON");
		testOrder.setArea(new BigDecimal("200"));
		testOrder.setTaxRate(new BigDecimal("11"));
		testOrder.setCostPerSquareFoot(new BigDecimal("2"));
		testOrder.setLaborCostPerSquareFoot(new BigDecimal("3"));
		testOrder.setMaterialCost(new BigDecimal("875"));
		testOrder.setLaborCost(new BigDecimal("1050"));
		testOrder.setTax(new BigDecimal("211"));
		testOrder.setTotal(new BigDecimal("2200"));
		//act
		Order editedOrder = testService.editOrder(testOrder.getOrderNumber(), testOrder, editDate);
		boolean replaceResult = testService.replaceEditedOrder(editDate, editedOrder.getOrderNumber(), editedOrder, 'y');
		//assert
		assertNotNull(editedOrder);
		assertEquals(testOrder.getCustomerName(), editedOrder.getCustomerName());
		assertTrue(replaceResult);
	}
	
	@Test
	void testRemoveOrder() throws FlooringPersistenceException {
		//arrange
		LocalDate removeDate = LocalDate.parse("2021-10-16");
		int num = testService.getOrderListByDate(removeDate).size();
		Order remOrder = testService.getParticularOrder(removeDate, num);
		//act
		boolean removeResult = testService.removeOrder(removeDate, num, remOrder, 'y');
		//assert
		assertTrue(removeResult);
	}
	
	@Test
	void testExportData() throws FileNotFoundException, FlooringPersistenceException {
		//act
		boolean exportResult = testService.exportData('y');
		//assert
		assertTrue(exportResult);
	}
	
	
	
	
	
}
