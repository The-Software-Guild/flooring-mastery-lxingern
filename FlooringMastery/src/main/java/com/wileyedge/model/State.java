package com.wileyedge.model;

import java.math.BigDecimal;
import java.util.Objects;

public class State {

	private String stateAbbrev;
	private String stateName;
	private BigDecimal taxRate;
	
	public State(String stateAbbrev, BigDecimal taxRate) {
		this.stateAbbrev = stateAbbrev;
		this.taxRate = taxRate;
	}
	
	public State(String stateAbbrev, String stateName, BigDecimal taxRate) {
		this.stateAbbrev = stateAbbrev;
		this.stateName = stateName;
		this.taxRate = taxRate;
	}

	public BigDecimal getTaxRate() {
		return taxRate;
	}

	public String getStateAbbrev() {
		return stateAbbrev;
	}

	@Override
	public int hashCode() {
		return Objects.hash(stateAbbrev, stateName, taxRate);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		State other = (State) obj;
		return Objects.equals(stateAbbrev, other.stateAbbrev) && Objects.equals(stateName, other.stateName)
				&& Objects.equals(taxRate, other.taxRate);
	}
	
}
