package com.wileyedge.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.wileyedge.exceptions.OrderNotFoundException;
import com.wileyedge.model.Order;
import com.wileyedge.model.Product;
import com.wileyedge.model.State;

class OrderDaoFileImplTest {
	
	private OrderDaoFileImpl dao;
	
	public OrderDaoFileImplTest() throws FileNotFoundException {
		this.dao = new OrderDaoFileImpl("test-data/orders/", "next_order_no.txt", "Orders_");
	}

//	Orders_06012013.txt => Only has header, no data
//	Orders_06022013.txt => Has 2 rows of data
//	Orders_06032013.txt => Does not exist
//	Orders_06042013.txt => For testing creation/update/delete

	@Test
	void getOrdersForDateReturnsOrders() throws FileNotFoundException, OrderNotFoundException {
		LocalDate date = LocalDate.parse("06022013", DateTimeFormatter.ofPattern("MMddyyyy"));
		State state = new State("WA", new BigDecimal("9.25"));
		Product product = new Product("Wood", new BigDecimal("5.15"), new BigDecimal("4.75"));
		Order expectedOrder = new Order(date, "Doctor Who", state, product, new BigDecimal("243.00"));
		expectedOrder.setOrderNo(2);
		expectedOrder.setMaterialCost(new BigDecimal("1251.45"));
		expectedOrder.setLabourCost(new BigDecimal("1154.25"));
		expectedOrder.setTax(new BigDecimal("216.51"));
		expectedOrder.setTotal(new BigDecimal("2622.21"));
		
		List<Order> ordersForDate = dao.getOrdersForDate(date);
		
		assertEquals(2, ordersForDate.size());
		assertTrue(ordersForDate.contains(expectedOrder));
	}
	
	@Test
	void getOrdersForDateThrowsFileNotFoundException() throws FileNotFoundException, OrderNotFoundException {
		assertThrows(FileNotFoundException.class, () -> {
			LocalDate date = LocalDate.parse("06032013", DateTimeFormatter.ofPattern("MMddyyyy"));
			
			dao.getOrdersForDate(date);			
		});
	}
	
	@Test
	void getOrdersForDateThrowsOrderNotFoundException() throws FileNotFoundException, OrderNotFoundException {
		assertThrows(OrderNotFoundException.class, () -> {
			LocalDate date = LocalDate.parse("06012013", DateTimeFormatter.ofPattern("MMddyyyy"));
			
			dao.getOrdersForDate(date);			
		});
	}
	
	@Test
	void getOrderReturnsExpectedOrder() throws FileNotFoundException, OrderNotFoundException {
		LocalDate date = LocalDate.parse("06022013", DateTimeFormatter.ofPattern("MMddyyyy"));
		int orderNo = 2;
		State state = new State("WA", new BigDecimal("9.25"));
		Product product = new Product("Wood", new BigDecimal("5.15"), new BigDecimal("4.75"));
		Order expectedOrder = new Order(date, "Doctor Who", state, product, new BigDecimal("243.00"));
		expectedOrder.setOrderNo(2);
		expectedOrder.setMaterialCost(new BigDecimal("1251.45"));
		expectedOrder.setLabourCost(new BigDecimal("1154.25"));
		expectedOrder.setTax(new BigDecimal("216.51"));
		expectedOrder.setTotal(new BigDecimal("2622.21"));
		
		Order actualOrder = dao.getOrder(date, orderNo);
		assertEquals(expectedOrder, actualOrder);
	}
	
	@Test
	void getOrderThrowsFileNotFoundException() throws FileNotFoundException, OrderNotFoundException {
		assertThrows(FileNotFoundException.class, () -> {
			LocalDate date = LocalDate.parse("06032013", DateTimeFormatter.ofPattern("MMddyyyy"));
			int orderNo = 1;
			
			dao.getOrder(date, orderNo);			
		});
	}
	
	@Test
	void getOrderThrowsOrderNotFoundException() throws FileNotFoundException, OrderNotFoundException {
		assertThrows(OrderNotFoundException.class, () -> {
			LocalDate date = LocalDate.parse("06022013", DateTimeFormatter.ofPattern("MMddyyyy"));
			int orderNo = 1;
			
			dao.getOrder(date, orderNo);			
		});
	}
	
	@Test
	void createOrderCreatesOrder() throws IOException {
		try (PrintWriter out = new PrintWriter(new FileWriter(dao.getOrdersFilePath() + dao.getNextOrderNoFileName()))) {
			out.println("1");
		}
		try (PrintWriter out = new PrintWriter(new FileWriter(dao.getOrdersFilePath() + dao.getOrdersFileNamePrefix() + "06042013.txt"))) {
			out.println("OrderNumber::CustomerName::State::TaxRate::ProductType::CostPerSquareFoot::LaborCostPerSquareFoot::Area::MaterialCost::LaborCost::Tax::Total");
			out.println("2::Doctor Who::WA::9.25::Wood::5.15::4.75::243.00::1251.45::1154.25::216.51::2622.21\r\n"
					+ "3::Albert Einstein::KY::6.00::Carpet::2.25::2.10::217.00::488.25::455.70::56.64::1000.59");
		}
		LocalDate orderDate = LocalDate.parse("06042013", DateTimeFormatter.ofPattern("MMddyyyy"));
		int orderNo = 1;
		State state = new State("WA", new BigDecimal("9.25"));
		Product product = new Product("Wood", new BigDecimal("5.15"), new BigDecimal("4.75"));
		Order newOrder = new Order(orderDate, "Doctor Who", state, product, new BigDecimal("243.00"));
		newOrder.setOrderNo(orderNo);
		newOrder.calculateDerivedFields();
		
		dao.createOrder(newOrder);
		
		Order orderFromFile = dao.getOrder(orderDate, orderNo);
		assertEquals(newOrder, orderFromFile);
		List<Order> ordersFromFile = dao.getOrdersForDate(orderDate);
		assertEquals(ordersFromFile.size(), 3);
	}
	
	@Test
	void updateOrderUpdatesOrder() throws IOException, CloneNotSupportedException {
		try (PrintWriter out = new PrintWriter(new FileWriter(dao.getOrdersFilePath() + dao.getOrdersFileNamePrefix() + "06042013.txt"))) {
			out.println("OrderNumber::CustomerName::State::TaxRate::ProductType::CostPerSquareFoot::LaborCostPerSquareFoot::Area::MaterialCost::LaborCost::Tax::Total");
			out.println("2::Doctor Who::WA::9.25::Wood::5.15::4.75::243.00::1251.45::1154.25::216.51::2622.21\r\n"
					+ "3::Albert Einstein::KY::6.00::Carpet::2.25::2.10::217.00::488.25::455.70::56.64::1000.59");
		}
		LocalDate orderDate = LocalDate.parse("06042013", DateTimeFormatter.ofPattern("MMddyyyy"));
		Order orderBeforeUpdate = dao.getOrder(orderDate, 3);
		State state = new State("CA", new BigDecimal("25.00"));
		Order updatedOrder = orderBeforeUpdate.clone();
		updatedOrder.setState(state);
		updatedOrder.setArea(new BigDecimal("168.23"));
		updatedOrder.calculateDerivedFields();
		
		dao.updateOrder(updatedOrder);
		
		Order updatedOrderFromFile = dao.getOrder(orderDate, 3);
		assertNotEquals(orderBeforeUpdate, updatedOrderFromFile);
		assertEquals(updatedOrderFromFile, updatedOrder);
		List<Order> ordersFromFile = dao.getOrdersForDate(orderDate);
		assertEquals(ordersFromFile.size(), 2);
	}
	
	@Test
	void deleteOrderDeletesOrder() throws IOException {
		try (PrintWriter out = new PrintWriter(new FileWriter(dao.getOrdersFilePath() + dao.getOrdersFileNamePrefix() + "06042013.txt"))) {
			out.println("OrderNumber::CustomerName::State::TaxRate::ProductType::CostPerSquareFoot::LaborCostPerSquareFoot::Area::MaterialCost::LaborCost::Tax::Total");
			out.println("2::Doctor Who::WA::9.25::Wood::5.15::4.75::243.00::1251.45::1154.25::216.51::2622.21\r\n"
					+ "3::Albert Einstein::KY::6.00::Carpet::2.25::2.10::217.00::488.25::455.70::56.64::1000.59");
		}
		LocalDate orderDate = LocalDate.parse("06042013", DateTimeFormatter.ofPattern("MMddyyyy"));
		int orderNo = 3;
		Order orderToDelete = dao.getOrder(orderDate, orderNo);
		
		dao.deleteOrder(orderToDelete);
		
		List<Order> ordersFromFile = dao.getOrdersForDate(orderDate);
		assertEquals(ordersFromFile.size(), 1);
		assertThrows(OrderNotFoundException.class, () -> {
			dao.getOrder(orderDate, orderNo);			
		});
	}
}
