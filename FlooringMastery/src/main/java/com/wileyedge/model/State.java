package com.wileyedge.model;

import java.math.BigDecimal;

public class State {

	private String stateAbbrev;
	private String stateName;
	private BigDecimal taxRate;
	
	public State(String stateAbbrev, String stateName, BigDecimal taxRate) {
		super();
		this.stateAbbrev = stateAbbrev;
		this.stateName = stateName;
		this.taxRate = taxRate;
	}

	public BigDecimal getTaxRate() {
		return taxRate;
	}
	
}
