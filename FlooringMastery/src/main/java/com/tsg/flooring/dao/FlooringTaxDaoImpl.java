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

import com.tsg.flooring.dto.Tax;
import com.tsg.flooring.ui.UserIO;
import com.tsg.flooring.ui.UserIOConsoleImpl;

@Component
public class FlooringTaxDaoImpl implements FlooringTaxDao {
	UserIO io = new UserIOConsoleImpl();
	private static final String TAX_FILE = "data/Taxes.txt";
	private static final String DELIMITER = ",";
	
	private Map<String, Tax> statesTaxes = new HashMap<>();
	
	@Override
	public List<Tax> getTaxList() {
		loadTaxes();
		return new ArrayList<Tax>(statesTaxes.values());
	}

	@Override
	public Tax getParticularTax(String state) {
		loadTaxes();
		return statesTaxes.get(state);
	}
	
	
	/*----unmarshal----*/
	private Tax unmarshalTaxes(String taxesAsText) {
		String[] taxTokens = taxesAsText.split(DELIMITER);
		Tax taxesFromfile = new Tax();
		taxesFromfile.setState(taxTokens[0]);
		taxesFromfile.setStateName(taxTokens[1]);
		taxesFromfile.setTaxRate(new BigDecimal(taxTokens[2]));
				
		return taxesFromfile;	
	}
	
	/*----load taxes from file into memory(statesTaxes)---*/
	private void loadTaxes() {
		Scanner scan = null;
		try {
			scan = new Scanner(new BufferedReader(new FileReader(TAX_FILE)));
		}catch(FileNotFoundException e) {
			if(scan == null) {
				io.print("The product types file missing or damaged. ");
				return;
			}
		}
		
		scan.nextLine();
		
		String currentLine;
		Tax currentTax;
		
		while(scan.hasNextLine()) {
			currentLine = scan.nextLine();
			currentTax = unmarshalTaxes(currentLine);
			statesTaxes.put(currentTax.getState(), currentTax);	
		}
	}
	
	
	
}
