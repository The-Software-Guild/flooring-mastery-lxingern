package com.wileyedge.model;

import java.math.BigDecimal;
import java.util.Objects;

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

	@Override
	public int hashCode() {
		return Objects.hash(costPerSquareFoot, labourCostPerSquareFoot, productType);
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
				&& Objects.equals(labourCostPerSquareFoot, other.labourCostPerSquareFoot)
				&& Objects.equals(productType, other.productType);
	}
	
}
