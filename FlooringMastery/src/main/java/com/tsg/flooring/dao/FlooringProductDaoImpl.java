package com.tsg.flooring.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.springframework.stereotype.Component;

import com.tsg.flooring.dto.Product;
import com.tsg.flooring.ui.UserIO;
import com.tsg.flooring.ui.UserIOConsoleImpl;

@Component
public class FlooringProductDaoImpl implements FlooringProductDao{
	UserIO io = new UserIOConsoleImpl();
	
	private static final String PRODUCT_FILE = "data/Products.txt" ;
	private static final String DELIMITER = ",";
	
	private Map<String, Product> productTypes = new HashMap<>();
	
	/*-----GET PRODUCT LIST------------*/
	@Override
	public List<Product> getProductList() {
		loadProducts();
		return new ArrayList<Product>(productTypes.values());
	}
	
	/*-----GET PARTICULAR PRODUCT--------*/
	@Override
	public Product getParticularProduct(String productType) {
		loadProducts();
		return productTypes.get(productType);
	}
	
	/*---unmarshal-----*/
	private Product unmarshalProduct(String productAsText) {
		String[] productTokens = productAsText.split(DELIMITER);
		String productType = productTokens[0];
		Product productFromFile = new Product(productType);
		productFromFile.setProductType(productType);
		productFromFile.setCostPerSquareFoot(new BigDecimal(productTokens[1]));
		productFromFile.setLaborCostPerSquareFoot(new BigDecimal(productTokens[2]));
		
		return productFromFile;
	}
	
	/*--load products from file into memory--*/
	private void loadProducts() {
		Scanner scan = null;
		try {
			scan = new Scanner(new BufferedReader(new FileReader(PRODUCT_FILE)));
		}catch(FileNotFoundException e) {
			if(scan == null) {
				io.print("The product types file missing or damaged. ");
				return;
			}
		}
		scan.nextLine();
		
		String currentLine;
		Product currentProduct;
		
		while(scan.hasNextLine()) {
			currentLine = scan.nextLine();
			currentProduct = unmarshalProduct(currentLine);
			productTypes.put(currentProduct.getProductType(), currentProduct);
		}
	}


}
