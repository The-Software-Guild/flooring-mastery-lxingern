package com.wileyedge.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Order {

	private int orderNo;
	private static int nextOrderNo;
	private LocalDate orderDate;
	private String customerName;
	private String stateAbbrev;
	private BigDecimal taxRate;
	private String productType;
	private BigDecimal costPerSquareFoot;
	private BigDecimal labourCostPerSquareFoot;
	private BigDecimal area;
	private BigDecimal materialCost;
	private BigDecimal labourCost;
	private BigDecimal tax;
	private BigDecimal total;

	public Order(String customerName, String stateAbbrev, BigDecimal taxRate, String productType, BigDecimal area,
			BigDecimal costPerSquareFoot, BigDecimal labourCostPerSquareFoot) {
		super();
		this.customerName = customerName;
		this.stateAbbrev = stateAbbrev;
		this.taxRate = taxRate;
		this.productType = productType;
		this.costPerSquareFoot = costPerSquareFoot;
		this.labourCostPerSquareFoot = labourCostPerSquareFoot;
		this.area = area;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	public void setArea(BigDecimal area) {
		this.area = area;
	}

	public void setMaterialCost(BigDecimal materialCost) {
		this.materialCost = materialCost;
	}

	public void setLabourCost(BigDecimal labourCost) {
		this.labourCost = labourCost;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}

	public void calculateDerivedFields() {
		this.materialCost = this.area.multiply(this.costPerSquareFoot);
		this.labourCost = this.area.multiply(this.labourCostPerSquareFoot);
		this.tax = (this.materialCost.add(this.materialCost)).multiply(this.taxRate.divide(new BigDecimal("100")));
		this.total = this.materialCost.add(this.labourCost).add(this.tax);
	}

//	@Override
//	public String toString() {
//		return "Order [orderNo=" + orderNo + ", orderDate=" + orderDate + ", customerName=" + customerName
//				+ ", stateAbbrev=" + stateAbbrev + ", taxRate=" + taxRate + ", productType=" + productType
//				+ ", costPerSquareFoot=" + costPerSquareFoot + ", labourCostPerSquareFoot=" + labourCostPerSquareFoot
//				+ ", area=" + area + ", materialCost=" + materialCost + ", labourCost=" + labourCost + ", tax=" + tax
//				+ ", total=" + total + "]";
//	}

	@Override
	public String toString() {
		return String.format("%-9d | %-20s | %-5s | %-8s | %-12s | %-8s | %-15s | %-10s | %-13s | %-11s | %-8s | %-8s",
							orderNo,
							customerName,
							stateAbbrev,
							taxRate.toString(),
							productType,
							costPerSquareFoot.toString(),
							labourCostPerSquareFoot.toString(),
							area.toString(),
							materialCost.toString(),
							labourCost.toString(),
							tax.toString(),
							total.toString()
							);

	}
	
	
}
