package com.wileyedge.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Objects;

public class Order implements Cloneable {

	private static int nextOrderNo = 1;
	private int orderNo;
	private LocalDate orderDate;
	private String customerName;
	private State state;
	private Product product;
	private BigDecimal area;
	private BigDecimal materialCost;
	private BigDecimal labourCost;
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

	public static int getNextOrderNo() {
		return nextOrderNo;
	}

	public static void setNextOrderNo(int nextOrderNo) {
		Order.nextOrderNo = nextOrderNo;
	}

	public int getOrderNo() {
		return orderNo;
	}
	
	public LocalDate getOrderDate() {
		return orderDate;
	}

	public String getCustomerName() {
		return customerName;
	}

	public State getState() {
		return state;
	}

	public Product getProduct() {
		return product;
	}

	public BigDecimal getArea() {
		return area;
	}

	public BigDecimal getMaterialCost() {
		return materialCost;
	}

	public BigDecimal getLabourCost() {
		return labourCost;
	}

	public BigDecimal getTax() {
		return tax;
	}

	public BigDecimal getTotal() {
		return total;
	}
	
	public void setOrderNo() {
		orderNo = nextOrderNo;
		nextOrderNo++;
	}
	
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
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
		materialCost = area.multiply(product.getCostPerSquareFoot()).setScale(2, RoundingMode.HALF_UP);
		labourCost = area.multiply(product.getLabourCostPerSquareFoot()).setScale(2, RoundingMode.HALF_UP);
		tax = (materialCost.add(labourCost)).multiply(state.getTaxRate().divide(new BigDecimal("100"))).setScale(2, RoundingMode.HALF_UP);
		total = materialCost.add(labourCost).add(tax).setScale(2, RoundingMode.HALF_UP);
	}

	@Override
	public String toString() {
		return String.format("%-9s | %-20s | %-5s | %-8s | %-12s | %-8s | %-15s | %-10s | %-13s | %-11s | %-8s | %-8s",
							orderNo == 0 ? "" : String.valueOf(orderNo),
							customerName,
							state.getStateAbbrev(),
							state.getTaxRate().toString(),
							product.getProductType(),
							product.getCostPerSquareFoot().toString(),
							product.getLabourCostPerSquareFoot().toString(),
							area.toString(),
							materialCost.toString(),
							labourCost.toString(),
							tax.toString(),
							total.toString()
							);

	}

	@Override
	public int hashCode() {
		return Objects.hash(area, customerName, labourCost, materialCost, orderDate, orderNo, product, state, tax,
				total);
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
		return Objects.equals(area, other.area) && Objects.equals(customerName, other.customerName)
				&& Objects.equals(labourCost, other.labourCost) && Objects.equals(materialCost, other.materialCost)
				&& Objects.equals(orderDate, other.orderDate) && orderNo == other.orderNo
				&& Objects.equals(product, other.product) && Objects.equals(state, other.state)
				&& Objects.equals(tax, other.tax) && Objects.equals(total, other.total);
	}
	
	@Override
	public Order clone() throws CloneNotSupportedException {
		return (Order) super.clone();
	}
	
}
