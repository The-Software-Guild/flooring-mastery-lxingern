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
		return "Product [productType=" + productType + ", costPerSquareFoot=" + costPerSquareFoot
				+ ", labourCostPerSquareFoot=" + labourCostPerSquareFoot + "]";
	}

	public BigDecimal getCostPerSquareFoot() {
		return costPerSquareFoot;
	}

	public BigDecimal getLabourCostPerSquareFoot() {
		return labourCostPerSquareFoot;
	}
	
}
