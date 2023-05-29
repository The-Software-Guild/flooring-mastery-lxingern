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
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.wileyedge.exceptions.OrderNotFoundException;
import com.wileyedge.model.Order;
import com.wileyedge.model.Product;
import com.wileyedge.model.State;

@Repository
public class OrderDaoFileImpl implements OrderDao {
	
	private String ordersFilePath;
	private String nextOrderNoFileName;
	private String ordersFileNamePrefix;
	
	public OrderDaoFileImpl() throws FileNotFoundException {
		this.ordersFilePath = "data/orders/";
		this.nextOrderNoFileName = "next_order_no.txt";
		this.ordersFileNamePrefix = "Orders_";
		loadNextOrderNo();
	}
 
	public OrderDaoFileImpl(String ordersFilePath,
			String nextOrderNoFileName, String ordersFileNamePrefix) throws FileNotFoundException {
		this.ordersFilePath = ordersFilePath;
		this.nextOrderNoFileName = nextOrderNoFileName;
		this.ordersFileNamePrefix = ordersFileNamePrefix;
		loadNextOrderNo();
	}
	
	@Override
	public List<Order> getOrdersForDate(LocalDate date) throws FileNotFoundException, OrderNotFoundException {
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

		if (orders.size() == 0) throw new OrderNotFoundException();
		return orders;
	}
	 
	@Override
	public Order getOrder(LocalDate date, int orderNo) throws FileNotFoundException, OrderNotFoundException {
		List<Order> ordersForDate = getOrdersForDate(date);
		Optional<Order> order = ordersForDate.stream().filter(o -> o.getOrderNo() == orderNo).findFirst();
		if (!order.isPresent()) throw new OrderNotFoundException();
		return order.get();
	}

	@Override
	public void createOrder(Order newOrder) throws IOException {
		List<Order> ordersForDate = new ArrayList<>();
		try {
			ordersForDate = getOrdersForDate(newOrder.getOrderDate());			
		} catch (FileNotFoundException | OrderNotFoundException e) {
		}
		ordersForDate.add(newOrder);
		
		saveOrders(ordersForDate, newOrder.getOrderDate());
	}
	
	@Override
	public void updateOrder(Order order) throws IOException {
		LocalDate date = order.getOrderDate();
		List<Order> ordersForDate = new ArrayList<>();
		try {
			ordersForDate = getOrdersForDate(date);
		} catch (FileNotFoundException | OrderNotFoundException e) {}
		Order orderToUpdate = ordersForDate.stream().filter(o -> o.getOrderNo() == order.getOrderNo()).findFirst().get();
		orderToUpdate.setCustomerName(order.getCustomerName());
		orderToUpdate.setState(order.getState());
		orderToUpdate.setProduct(order.getProduct());
		orderToUpdate.setArea(order.getArea());
		orderToUpdate.calculateDerivedFields();
				
		saveOrders(ordersForDate, date);
	}
	
	@Override
	public void deleteOrder(Order orderToRemove) throws IOException {
		LocalDate date = orderToRemove.getOrderDate();
		List<Order> ordersForDate = getOrdersForDate(date);
		ordersForDate = ordersForDate.stream().filter(o -> o.getOrderNo() != orderToRemove.getOrderNo()).collect(Collectors.toList());
		
		saveOrders(ordersForDate, date);
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

	public String getOrdersFilePath() {
		return ordersFilePath;
	}

	public String getNextOrderNoFileName() {
		return nextOrderNoFileName;
	}

	public String getOrdersFileNamePrefix() {
		return ordersFileNamePrefix;
	}

}
