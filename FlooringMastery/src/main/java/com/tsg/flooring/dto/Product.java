package com.tsg.flooring.dto;
/**
 * @author Alexandr Bleshchyk
 *
 */

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
	String productType;
	BigDecimal costPerSquareFoot;
	BigDecimal laborCostPerSquareFoot;
	
	public Product(String productType) {
	}
	
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public BigDecimal getCostPerSquareFoot() {
		return costPerSquareFoot;
	}
	public void setCostPerSquareFoot(BigDecimal costPerSquareFoot) {
		this.costPerSquareFoot = costPerSquareFoot;
	}
	public BigDecimal getLaborCostPerSquareFoot() {
		return laborCostPerSquareFoot;
	}
	public void setLaborCostPerSquareFoot(BigDecimal laborCostPerSquareFoot) {
		this.laborCostPerSquareFoot = laborCostPerSquareFoot;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(costPerSquareFoot, laborCostPerSquareFoot, productType);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return Objects.equals(costPerSquareFoot, other.costPerSquareFoot)
				&& Objects.equals(laborCostPerSquareFoot, other.laborCostPerSquareFoot)
				&& Objects.equals(productType, other.productType);
	}
	
	
	
}
