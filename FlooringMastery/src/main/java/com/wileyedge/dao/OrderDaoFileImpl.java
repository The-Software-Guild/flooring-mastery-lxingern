package com.wileyedge.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Repository;

import com.wileyedge.model.Order;
import com.wileyedge.model.Product;
import com.wileyedge.model.State;

@Repository
public class OrderDaoFileImpl implements OrderDao {
	
	private String productsFilePath;
	private String taxesFilePath;
	private String ordersFilePath;
	private String nextOrderNoFileName;
	private String ordersFileNamePrefix;
	
	public OrderDaoFileImpl() throws FileNotFoundException {
		this.productsFilePath = "data/data/Products.txt";
		this.taxesFilePath = "data/data/Taxes.txt";
		this.ordersFilePath = "data/orders/";
		this.nextOrderNoFileName = "next_order_no.txt";
		this.ordersFileNamePrefix = "Orders_";
		loadNextOrderNo();
	}

	public List<Product> getProducts() throws FileNotFoundException {
		return loadProducts();
	}

	public List<State> getStates() throws FileNotFoundException {
		return loadStates();
	}
	
	@Override
	public List<Order> getOrdersForDate(LocalDate date) throws FileNotFoundException {
		String orderFilePath = ordersFilePath + ordersFileNamePrefix + date.format(DateTimeFormatter.ofPattern("MMddyyyy")) + ".txt";
		List<Order> orders = new ArrayList<>();
		
		try (Scanner sc = new Scanner(new BufferedReader(new FileReader(orderFilePath)))) {
			sc.nextLine();
			
			while (sc.hasNextLine()) {
				String currentLine = sc.nextLine();
				String[] fields = currentLine.split("::");
				State state = new State(fields[2], new BigDecimal(fields[3]));
				Product product = new Product(fields[4], new BigDecimal(fields[5]), new BigDecimal(fields[6]));
				Order order = new Order(
								date,
								fields[1], 
								state, 
								product, 
								new BigDecimal(fields[7])
								);
				order.setOrderNo(Integer.parseInt(fields[0]));
				order.setMaterialCost(new BigDecimal(fields[8]));
				order.setLabourCost(new BigDecimal(fields[9]));
				order.setTax(new BigDecimal(fields[10]));
				order.setTotal(new BigDecimal(fields[11]));

				orders.add(order);
			}			
		}

		return orders;
	}
	
	@Override
	public void createOrder(Order newOrder) throws IOException {
		List<Order> ordersForDate = new ArrayList<>();
		try {
			ordersForDate = getOrdersForDate(newOrder.getOrderDate());
		} catch (FileNotFoundException e) {
		}
		
		ordersForDate.add(newOrder);
		saveOrders(ordersForDate, newOrder.getOrderDate());
	}
	
	private List<Product> loadProducts() throws FileNotFoundException {
		List<Product> products = new ArrayList<>();
		
		try (Scanner sc = new Scanner(new BufferedReader(new FileReader(productsFilePath)))) {
			sc.nextLine();
			
			while (sc.hasNextLine()) {
				String currentLine = sc.nextLine();
				String[] fields = currentLine.split(",");
				Product product = new Product(fields[0], new BigDecimal(fields[1]), new BigDecimal(fields[2]));
				products.add(product);
			}			
		}
		
		return products;
	}
	
	private List<State> loadStates() throws FileNotFoundException {	
		List<State> states = new ArrayList<>();
		
		try (Scanner sc = new Scanner(new BufferedReader(new FileReader(taxesFilePath)))) {
			sc.nextLine();
			
			while (sc.hasNextLine()) {
				String currentLine = sc.nextLine();
				String[] fields = currentLine.split(",");
				State state = new State(fields[0], fields[1], new BigDecimal(fields[2]));
				states.add(state);
			}			
		}
		
		return states;
	}

	private void loadNextOrderNo() throws FileNotFoundException {
		try (Scanner sc = new Scanner(new BufferedReader(new FileReader(ordersFilePath + nextOrderNoFileName)))) {
			String nextOrderNoStr = sc.nextLine();
			
			Order.setNextOrderNo(Integer.parseInt(nextOrderNoStr));		
		}
	}
	
	private void saveOrders(List<Order> orders, LocalDate date) throws IOException {
		String filePath = ordersFilePath + ordersFileNamePrefix + date.format(DateTimeFormatter.ofPattern("MMddyyyy"))+ ".txt"; 

		try (PrintWriter out = new PrintWriter(new FileWriter(filePath))) {
			out.println("OrderNumber::CustomerName::State::TaxRate::ProductType::CostPerSquareFoot::LaborCostPerSquareFoot::Area::MaterialCost::LaborCost::Tax::Total");
			for (Order order : orders) {
				out.println(order.getOrderNo() + "::" +
						order.getCustomerName() + "::" +
						order.getState().getStateAbbrev() + "::" +
						order.getState().getTaxRate() + "::" +
						order.getProduct().getProductType() + "::" +
						order.getProduct().getCostPerSquareFoot() + "::" +
						order.getProduct().getLabourCostPerSquareFoot() + "::" +
						order.getArea() + "::" +
						order.getMaterialCost() + "::" +
						order.getLabourCost() + "::" +
						order.getTax() + "::" +
						order.getTotal()
						);
			}
			out.flush();
		}

		try (PrintWriter out = new PrintWriter(new FileWriter(ordersFilePath + nextOrderNoFileName))) {
			out.println(Order.getNextOrderNo());
		}
	}

}
