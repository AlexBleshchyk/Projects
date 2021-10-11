package com.tsg.flooring.dao;

import static org.junit.jupiter.api.Assertions.*;

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
		LocalDate ld3 = LocalDate.parse("2013-06-03"); //file for this date does not exist
		LocalDate ld4 = LocalDate.parse("2013-06-04"); //file for this date does not exist
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
		//arrange
		LocalDate ld1 = LocalDate.parse("2013-06-01");
		//act
		Order testOrder1 = testOrderDao.getParticularOrder(ld1, 1);
		//assert
		assertNotNull(testOrder1);
		assertEquals("Ada Lovelace", testOrder1.getCustomerName());
		
		/*---NEGATIVE---*/
		//arrange
		LocalDate ld2 = LocalDate.parse("2014-06-01"); //file for this date does not exist
		//act
		Order testOrder2 = testOrderDao.getParticularOrder(ld2, 1);
		//assert
		assertNull(testOrder2);
		
		
	}
}
