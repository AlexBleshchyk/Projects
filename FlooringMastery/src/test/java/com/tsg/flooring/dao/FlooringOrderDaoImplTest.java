package com.tsg.flooring.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tsg.flooring.dto.Order;

class FlooringOrderDaoImplTest {

	private static FlooringOrderDao testOrderDao;

	public FlooringOrderDaoImplTest() {
	}

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		testOrderDao = new FlooringOrderDaoImpl();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetOrderList() throws FlooringPersistenceException {
		/*------positive--------*/
		// arrange
		LocalDate ld1 = LocalDate.parse("2013-06-01");
		LocalDate ld2 = LocalDate.parse("2013-06-02");
		// act
		List<Order> testList1 = testOrderDao.getOrderList(ld1);
		List<Order> testList2 = testOrderDao.getOrderList(ld2);
		// assert
		assertNotNull(testList1, "Should be not null");
		assertNotNull(testList2, "Should be not null");
		assertFalse(testList1.isEmpty(), "Should not be empty");
		assertFalse(testList2.isEmpty(), "Should not be empty");

		/*----negative------*/
		// arrange
		LocalDate ld3 = LocalDate.parse("2013-06-03"); // file for this date does not exist
		LocalDate ld4 = LocalDate.parse("2013-06-04"); // file for this date does not exist
		// act
		List<Order> testList3 = testOrderDao.getOrderList(ld3);
		List<Order> testList4 = testOrderDao.getOrderList(ld4);
		// assert
		assertTrue(testList3.isEmpty(), "Should be empty");
		assertTrue(testList4.isEmpty(), "Should be empty");
	}

	@Test
	void testGetParticularOrder() throws FlooringPersistenceException {
		/*---POSITIVE---*/
		// arrange
		LocalDate ld1 = LocalDate.parse("2013-06-01");
		// act
		Order testOrder1 = testOrderDao.getParticularOrder(ld1, 1);
		// assert
		assertNotNull(testOrder1);
		assertEquals("Ada Lovelace", testOrder1.getCustomerName());

		/*---NEGATIVE---*/
		// arrange
		LocalDate ld2 = LocalDate.parse("2014-06-01"); // file for this date does not exist
		// act
		Order testOrder2 = testOrderDao.getParticularOrder(ld2, 1);
		// assert
		assertNull(testOrder2);
	}

	@Test
	void testGetCurrentOrderList() throws FlooringPersistenceException {
		// arrange
		LocalDate ld1 = LocalDate.parse("2013-06-01");
		testOrderDao.getOrderList(ld1);
		// act
		List<Order> curList = testOrderDao.getCurrentOrdersList();
		// assert
		assertNotNull(curList);
		assertEquals(2, curList.size());
		assertEquals(1, curList.get(0).getOrderNumber());
		assertEquals(2, curList.get(1).getOrderNumber());
	}

	@Test
	void testAddOrder() throws FlooringPersistenceException {
		// arrange
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

		// act
		testOrderDao.addOrder(testOrder.getOrderNumber(), testOrder, addDate);
		Order retrivedOrder = testOrderDao.getParticularOrder(addDate, testOrder.getOrderNumber());
		// asset
		assertNotNull(retrivedOrder);
		assertEquals(testOrder.getCustomerName(), retrivedOrder.getCustomerName());
	}

	@Test
	void testEditOrder() throws FlooringPersistenceException {
		// arrange
		LocalDate editDate = LocalDate.parse("2021-10-15");
		int num = testOrderDao.getOrderList(editDate).size();
		Order testOrder = testOrderDao.getParticularOrder(editDate, num);
		testOrder.setCustomerName("testName");
		// act
		testOrderDao.editOrder(num, testOrder, editDate);
		Order retrievedOrder = testOrderDao.getParticularOrder(editDate, num);
		// assert
		assertNotNull(retrievedOrder);
		assertEquals(testOrder.getCustomerName(), retrievedOrder.getCustomerName());
	}

	@Test
	void testRemoveOrder() throws FlooringPersistenceException {
		// arrange
		LocalDate removeDate = LocalDate.parse("2021-10-15");
		int num = testOrderDao.getOrderList(removeDate).size();
		Order testOrder = testOrderDao.getParticularOrder(removeDate, num);
		// act
		testOrderDao.removeOrder(num, testOrder, removeDate);
		// assert
		assertNotEquals(num, testOrderDao.getOrderList(removeDate).size());
	}
	
	@Test
	void testExportData() throws FileNotFoundException {
		testOrderDao.dataExport();
		File file = new File("Backup/DataExport.txt");
		assertTrue(file.exists());
	}

}
