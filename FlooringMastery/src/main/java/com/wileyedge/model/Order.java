package com.wileyedge.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Order {

	private int orderNo;
	private static int nextOrderNo;
	private LocalDate orderDate;
	private String customerName;
	private State state;
	private Product product;
	private BigDecimal area;
	private BigDecimal materialCost;
	private BigDecimal laborCost;
	private BigDecimal tax;
	private BigDecimal total;
	
	public Order(LocalDate orderDate, String customerName, State state, Product product, BigDecimal area) {
		super();
		this.orderDate = orderDate;
		this.customerName = customerName;
		this.state = state;
		this.product = product;
		this.area = area;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public void setState(State state) {
		this.state = state;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public void setArea(BigDecimal area) {
		this.area = area;
	}

	@Override
	public String toString() {
		return "Order [orderNo=" + orderNo + ", orderDate=" + orderDate + ", customerName=" + customerName + ", state="
				+ state + ", product=" + product + ", area=" + area + ", materialCost=" + materialCost + ", laborCost="
				+ laborCost + ", tax=" + tax + ", total=" + total + "]";
	}
	
}
