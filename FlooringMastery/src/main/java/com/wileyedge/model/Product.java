package com.wileyedge.model;

import java.math.BigDecimal;

public class Product {

	private String productType;
	private BigDecimal costPerSquareFoot;
	private BigDecimal labourCostPerSquareFoot;
	
	public Product(String productType, BigDecimal costPerSquareFoot, BigDecimal labourCostPerSquareFoot) {
		super();
		this.productType = productType;
		this.costPerSquareFoot = costPerSquareFoot;
		this.labourCostPerSquareFoot = labourCostPerSquareFoot;
	}

	@Override
	public String toString() {
		return String.format("%-12s | %-8s | %-15s",		
				productType,
				costPerSquareFoot.toString(),
				labourCostPerSquareFoot.toString()
				);
	}

	public String getProductType() {
		return productType;
	}

	public BigDecimal getCostPerSquareFoot() {
		return costPerSquareFoot;
	}

	public BigDecimal getLabourCostPerSquareFoot() {
		return labourCostPerSquareFoot;
	}
	
}
