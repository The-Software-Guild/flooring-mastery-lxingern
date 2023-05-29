package com.wileyedge.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.wileyedge.exceptions.InvalidInputException;
import com.wileyedge.exceptions.OrderNotFoundException;
import com.wileyedge.model.Order;
import com.wileyedge.model.OrderConverters;
import com.wileyedge.model.OrderValidators;
import com.wileyedge.model.Product;
import com.wileyedge.model.State;
import com.wileyedge.service.OrderService;
import com.wileyedge.service.ProductService;
import com.wileyedge.service.StateService;
import com.wileyedge.view.OrderView;

@Controller
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ProductService productService; 
	
	@Autowired
	private StateService stateService;
	
	@Autowired
	private OrderView view;

	public void run() {
		view.print("<<Flooring Program>>\n");
		boolean appRunning = true;
		
		while (appRunning) {
			view.displayMenu();		
			int action = view.getInput("Your selection: ", new OrderValidators.ValidateAction(), new OrderConverters.ConvertToInt());
			view.print("");
			switch (action) {
				case 1:
					displayOrders();
					break;
				case 2:
					addNewOrder();
					break;
				case 3:
					editOrder();
					break;
				case 4:
					removeOrder();
					break;
				case 5: 
					appRunning = false;
			}
		}
		
		view.print("See you again!");
	}

	private void displayOrders() {
		LocalDate date = view.getInput("View orders for date (in format MMDDYYYY): ", new OrderValidators.ValidateDate(), new OrderConverters.ConvertToDate());
		List<Order> orders;
		try {
			orders = orderService.getOrdersForDate(date);
		} catch (FileNotFoundException | OrderNotFoundException e) {
			view.print("No orders found for that date.\n");
			return;
		}
		view.displayOrders(orders);
	}
	
	private void addNewOrder() {
		Order newOrder = null;
		try {
			newOrder = getOrderDetails();
			newOrder.setOrderNo();
			newOrder.calculateDerivedFields();
		} catch (FileNotFoundException e) {
			System.out.println("Error occurred. Unable to add order.");
			return;
		}
		
		view.displayOrder(newOrder);
		boolean confirmed = view.getInput("Confirm addition of this order? ", new OrderValidators.ValidateConfirmation(), new OrderConverters.ConvertToBoolean());
		if (!confirmed) {
			view.print("");
			return;
		}
			
		try {
			orderService.createOrder(newOrder);			
			view.print("Order saved successfully.\n");
		} catch (IOException e) {
			view.print("Error occurred while trying to save order.\n");			
		}
	}
	
	private void editOrder() {
		Order order;
		try {
			order = getOrder();
		} catch (FileNotFoundException | OrderNotFoundException e) {
			view.print("Order not found.\n");
			return;
		}
		
		try {
			getOrderDetailsForUpdate(order);
		} catch (FileNotFoundException e) {
			System.out.println("Error occurred. Unable to edit order.\n");
			return;
		}

		order.calculateDerivedFields();
		view.displayOrder(order);
		boolean confirmed = view.getInput("Confirm update of this order? ", new OrderValidators.ValidateConfirmation(), new OrderConverters.ConvertToBoolean());
		if (!confirmed) {
			view.print("");
			return;
		}
		
		try {
			orderService.updateOrder(order);
			view.print("Order saved successfully.\n");			
		} catch (IOException e) {
			view.print("Error occurred while trying to save order.\n");			
		}
	}
	
	private void removeOrder() {
		Order orderToRemove;
		try {
			orderToRemove = getOrder();
		} catch (FileNotFoundException | OrderNotFoundException e) {
			view.print("Order not found.\n");
			return;
		}
		
		view.displayOrder(orderToRemove);
		boolean confirmed = view.getInput("Confirm deletion of this order? ", new OrderValidators.ValidateConfirmation(), new OrderConverters.ConvertToBoolean());
		if (!confirmed) {
			view.print("");
			return;
		}
		
		try {
			orderService.deleteOrder(orderToRemove);
			view.print("Order deleted successfully.\n");
		} catch (IOException e) {
			view.print("Error occurred while trying to delete order.\n");		
		}
	}
	
	private Order getOrder() throws FileNotFoundException, OrderNotFoundException {
		LocalDate date = view.getInput("Order date (in format MMDDYYYY): ", new OrderValidators.ValidateDate(), new OrderConverters.ConvertToDate());
		int orderNo = view.getInput("Order no.: ", new OrderValidators.ValidateOrderNo(), new OrderConverters.ConvertToInt());
		view.print("");
		return orderService.getOrder(date, orderNo);
	}

	private Order getOrderDetails() throws FileNotFoundException {
		view.print("Please provide the order details:\n");
		LocalDate orderDate = view.getInput("Order date (in format MMDDYYYY): ", new OrderValidators.ValidateDateForNewOrder(), new OrderConverters.ConvertToDate());
		String customerName = view.getInput("Customer name: ", new OrderValidators.ValidateName(), new OrderConverters.ConvertToString());
		
		State state = null;
		while (state == null) {
			String stateString = view.getInput("State abbreviation: ");
			try {
				state = stateService.findState(stateString);			
				if (state == null) throw new InvalidInputException("Invalid input: State not recognised or we do not sell in that state.\n");
			} catch (InvalidInputException e) {
				System.out.println(e.getLocalizedMessage());
			}
		}
		
		List<Product> availableProducts = productService.getProducts();
		view.displayProducts(availableProducts);
		Product product = null;
		while (product == null) {
			String productString = view.getInput("Product type: ");
			try {
				product = productService.findProduct(productString);			
				if (product == null) throw new InvalidInputException("Invalid input: Product is not recognised.\n");
			} catch (InvalidInputException e) {
				System.out.println(e.getLocalizedMessage());
			}
		}
		
		BigDecimal area = view.getInput("Area (min 100sqft): ", new OrderValidators.ValidateArea(), new OrderConverters.ConvertToBigDecimal());
		view.print("");

		return new Order(orderDate, customerName, state, product, area);
	}
	
	private void getOrderDetailsForUpdate(Order order) throws FileNotFoundException {
		view.print("Please provide the updated order details (leave blank to keep existing data):\n");
		String customerName = view.getInputForUpdate("Customer name", order.getCustomerName(), new OrderValidators.ValidateName(), new OrderConverters.ConvertToString());
		if (customerName != null) order.setCustomerName(customerName);
		
		State state = null;
		while (state == null) {
			String stateString = view.getInputForUpdate("State abbreviation", order.getState().getStateAbbrev());
			if (stateString.length() == 0) break;
			try {
				state = stateService.findState(stateString);			
				if (state == null) throw new InvalidInputException("Invalid input: State not recognised or we do not sell in that state.\n");
			} catch (InvalidInputException e) {
				System.out.println(e.getLocalizedMessage());
			}
		}
		if (state != null) order.setState(state);
		
		List<Product> availableProducts = productService.getProducts();
		view.displayProducts(availableProducts);
		Product product = null;
		while (product == null) {
			String productString = view.getInputForUpdate("Product type", order.getProduct().getProductType());
			if (productString.length() == 0) break;
			try {
				product = productService.findProduct(productString);			
				if (product == null) throw new InvalidInputException("Invalid input: Product is not recognised.\n");
			} catch (InvalidInputException e) {
				System.out.println(e.getLocalizedMessage());
			}
		}
		if (product != null) order.setProduct(product);
		
		BigDecimal area = view.getInputForUpdate("Area", order.getArea().toString(), new OrderValidators.ValidateArea(), new OrderConverters.ConvertToBigDecimal());
		if (area != null) order.setArea(area);
		view.print("");
	}

}
