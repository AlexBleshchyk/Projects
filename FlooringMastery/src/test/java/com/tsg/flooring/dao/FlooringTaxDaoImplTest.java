package com.tsg.flooring.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tsg.flooring.dto.Tax;

class FlooringTaxDaoImplTest {
	private static FlooringTaxDao testTaxDao;
	
	public FlooringTaxDaoImplTest() {
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
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetTaxList() {
		//arrange
		//act
		List<Tax> testTaxList = testTaxDao.getTaxList();
		//assert
		assertNotNull(testTaxList);
		assertFalse(testTaxList.isEmpty());
	}
	
	@Test
	void testGetParticularTax() {
		/*--POSITIVE--*/
		//arrange
		String state1 = "TX";
		String state2 = "WA";
		//act
		Tax testTax1 = testTaxDao.getParticularTax(state1);
		Tax testTax2 = testTaxDao.getParticularTax(state2);
		//assert
		assertEquals("TX", testTax1.getState());
		assertEquals("WA", testTax2.getState());
		
		/*--NEGATIVE--*/
		//arrange
		String state3 = "QC";
		String state4 = "BC";
		//act
		Tax testTax3 = testTaxDao.getParticularTax(state3);
		Tax testTax4 = testTaxDao.getParticularTax(state4);
		//assert
		assertNull(testTax3);
		assertNull(testTax4);
		
		
	}

	
	
}
