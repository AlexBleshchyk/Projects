package com.tsg.flooring.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tsg.flooring.dto.Product;

class FlooringProductDaoImplTest {
	
	private static FlooringProductDao testProductDao;
	
	public FlooringProductDaoImplTest() {
	}

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		testProductDao = new FlooringProductDaoImpl();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetProductList() {
		//arrange
		//act
		List<Product> testList = testProductDao.getProductList();
		//assert
		assertNotNull(testList, "Should not be null.");
		assertFalse(testList.isEmpty());
	}
	
	@Test
	void testGetParticularProduct() {
		//arrange
		  //positive
		String testProdType1 = "Carpet";
		String testProdType2 = "Wood";
		  //negative
		String testProdType3 = "Parquet";
		//act
		Product testType1 = testProductDao.getParticularProduct(testProdType1);
		Product testType2 = testProductDao.getParticularProduct(testProdType2);
		Product testType3 = testProductDao.getParticularProduct(testProdType3);
		//assert
		assertEquals("Carpet", testType1.getProductType());
		assertEquals("Wood", testType2.getProductType());
		assertNull(testType3);
	}

}
