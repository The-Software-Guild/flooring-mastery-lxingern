package com.wileyedge.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
	
//	@Value("${products-file-path}")
	private String productsFilePath;
	private List<Product> products;
//	
//	@Value("${taxes-file-path}")
	private String taxesFilePath;
	private List<State> states;
	
	private String ordersFilePath;
	
	public OrderDaoFileImpl() throws FileNotFoundException {
		this.productsFilePath = "data/data/Products.txt";
		this.products = new ArrayList<>();
		loadProducts();
		
		this.taxesFilePath = "data/data/Taxes.txt";
		this.states = new ArrayList<>();
		loadStates();
		
		this.ordersFilePath = "data/orders";
	}
	
	private void loadProducts() throws FileNotFoundException {
		try (Scanner sc = new Scanner(new BufferedReader(new FileReader(productsFilePath)))) {
			sc.nextLine();
			
			while (sc.hasNextLine()) {
				String currentLine = sc.nextLine();
				String[] fields = currentLine.split(",");
				Product product = new Product(fields[0], new BigDecimal(fields[1]), new BigDecimal(fields[1]));
				this.products.add(product);
			}			
		}
	}
	
	private void loadStates() throws FileNotFoundException {	
		try (Scanner sc = new Scanner(new BufferedReader(new FileReader(taxesFilePath)))) {
			sc.nextLine();
			
			while (sc.hasNextLine()) {
				String currentLine = sc.nextLine();
				String[] fields = currentLine.split(",");
				State state = new State(fields[0], fields[1], new BigDecimal(fields[2]));
				this.states.add(state);
			}			
		}
	}

	@Override
	public List<Order> getOrdersForDate(String date) throws FileNotFoundException {
		String orderFilePath = ordersFilePath + "/Orders_" + date + ".txt";
		List<Order> orders = new ArrayList<>();
		
		try (Scanner sc = new Scanner(new BufferedReader(new FileReader(orderFilePath)))) {
			sc.nextLine();
			
			while (sc.hasNextLine()) {
				String currentLine = sc.nextLine();
				String[] fields = currentLine.split(",");
				Order order = new Order(
								fields[1], 
								fields[2], 
								new BigDecimal(fields[3]), 
								fields[4], 
								new BigDecimal(fields[5]), 
								new BigDecimal(fields[6]), 
								new BigDecimal(fields[7])
								);
				order.setOrderDate(LocalDate.parse(date, DateTimeFormatter.ofPattern("MMddyyyy")));
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
}
