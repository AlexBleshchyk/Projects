package com.ab.SuperHero.dto;

import java.util.Objects;

public class Power {
	
	private int powerId;
	private String powerName;
	private String powerDescription;
		
	public int getPowerId() {
		return powerId;
	}
	public void setPowerId(int powerId) {
		this.powerId = powerId;
	}
	public String getPowerName() {
		return powerName;
	}
	public void setPowerName(String powerName) {
		this.powerName = powerName;
	}
	public String getPowerDescription() {
		return powerDescription;
	}
	public void setPowerDescription(String powerDescription) {
		this.powerDescription = powerDescription;
	}
//	@Override
//	public int hashCode() {
//		return Objects.hash(powerDescription, powerId, powerName);
//	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Power other = (Power) obj;
		return Objects.equals(powerDescription, other.powerDescription) && powerId == other.powerId
				&& Objects.equals(powerName, other.powerName);
	}
	

	
	
}
