package com.tsg.flooring.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tsg.flooring.dao.FlooringProductDao;
import com.tsg.flooring.dao.FlooringProductDaoImpl;
import com.tsg.flooring.dao.FlooringTaxDao;
import com.tsg.flooring.dao.FlooringTaxDaoImpl;


class FlooringServiceLayerImplTest {
	private static FlooringServiceLayer testService;
	private static FlooringTaxDao testTaxDao;
	private static FlooringProductDao testProductDao;
	
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
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
//		
	}

}
