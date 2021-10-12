package com.tsg.flooring.dto;
/**
 * @author Alexandr Bleshchyk
 */

import java.math.BigDecimal;
import java.util.Objects;

public class Order {
	Integer orderNumber;
	String customerName;
	String state;
	BigDecimal taxRate;
	String productType;
	BigDecimal area;
	BigDecimal costPerSquareFoot;
	BigDecimal laborCostPerSquareFoot;
	BigDecimal materialCost;
	BigDecimal laborCost;
	BigDecimal tax;
	BigDecimal total;
	
	public Order(Integer orderNumber) {
	}
	
	public Order() {
	}

	public Integer getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public BigDecimal getTaxRate() {
		return taxRate;
	}
	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public BigDecimal getArea() {
		return area;
	}
	public void setArea(BigDecimal area) {
		this.area = area;
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
	public BigDecimal getMaterialCost() {
		return materialCost;
	}
	public void setMaterialCost(BigDecimal materialCost) {
		this.materialCost = materialCost;
	}
	public BigDecimal getLaborCost() {
		return laborCost;
	}
	public void setLaborCost(BigDecimal laborCost) {
		this.laborCost = laborCost;
	}
	public BigDecimal getTax() {
		return tax;
	}
	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	@Override
	public int hashCode() {
		return Objects.hash(area, costPerSquareFoot, customerName, laborCost, laborCostPerSquareFoot, materialCost,
				orderNumber, productType, state, tax, taxRate, total);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		return Objects.equals(area, other.area) && Objects.equals(costPerSquareFoot, other.costPerSquareFoot)
				&& Objects.equals(customerName, other.customerName) && Objects.equals(laborCost, other.laborCost)
				&& Objects.equals(laborCostPerSquareFoot, other.laborCostPerSquareFoot)
				&& Objects.equals(materialCost, other.materialCost) && Objects.equals(orderNumber, other.orderNumber)
				&& Objects.equals(productType, other.productType) && Objects.equals(state, other.state)
				&& Objects.equals(tax, other.tax) && Objects.equals(taxRate, other.taxRate)
				&& Objects.equals(total, other.total);
	}
	
}
