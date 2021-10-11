package com.tsg.flooring.dto;
/**
 * @author Alexandr Bleshchyk
 */

import java.math.BigDecimal;
import java.util.Objects;

public class Tax {
	String state;
	String stateName;
	BigDecimal taxRate;
	
	public Tax(String stateTax) {
	}
	public Tax() {
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public BigDecimal getTaxRate() {
		return taxRate;
	}
	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(state, stateName, taxRate);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tax other = (Tax) obj;
		return Objects.equals(state, other.state) && Objects.equals(stateName, other.stateName)
				&& Objects.equals(taxRate, other.taxRate);
	}
	
	
	
	
}
