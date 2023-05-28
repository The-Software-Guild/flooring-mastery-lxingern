package com.wileyedge.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.wileyedge.model.Order;
import com.wileyedge.model.OrderConverters;
import com.wileyedge.model.OrderValidators;
import com.wileyedge.model.Product;
import com.wileyedge.model.State;
import com.wileyedge.service.OrderService;
import com.wileyedge.view.InvalidInputException;
import com.wileyedge.view.OrderView;

@Controller
public class OrderController {

	@Autowired
	private OrderService service;
	
	@Autowired
	private OrderView view;

	public void run() {
		view.print("\n<<Flooring Program>>\n");
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
					break;
				case 4:
					break;
				case 5: 
					break;
				case 6: 
					appRunning = false;
			}
		}
		
		view.print("See you again!");
	}

	private void displayOrders() {
		LocalDate date = view.getInput("View orders for date (in format MMDDYYYY): ", new OrderValidators.ValidateDate(), new OrderConverters.ConvertToDate());;
		List<Order> orders = service.getOrdersForDate(date);
		if (orders == null) {
			view.print("No orders found for that date.\n");
			return;
		}
		view.displayOrders(orders);
	}
	
	private void addNewOrder() {
		Order newOrder = getOrderDetails();
		newOrder.setOrderNo();
		newOrder.calculateDerivedFields();
		view.displayOrder(newOrder);
		boolean confirmed = view.getInput("Confirm addition of this order? ", new OrderValidators.ValidateConfirmation(), new OrderConverters.ConvertToBoolean());
		if (!confirmed) {
			view.print("");
			return;
		}
		
		boolean saveSuccessful = service.createOrder(newOrder);
		if (saveSuccessful) {
			view.print("Order saved successfully.");
		} else {
			view.print("Error occurred while trying to save order.");
		}
		view.print("");
	}

	private Order getOrderDetails() {
		view.print("Please provide the order details:\n");
		LocalDate orderDate = view.getInput("Order date (in format MMDDYYYY): ", new OrderValidators.ValidateDateForNewOrder(), new OrderConverters.ConvertToDate());
		String customerName = view.getInput("Customer name: ", new OrderValidators.ValidateName(), new OrderConverters.ConvertToString());
		
		State state = null;
		while (state == null) {
			String stateString = view.getInput("State abbreviation: ");
			try {
				state = service.findState(stateString);			
				if (state == null) throw new InvalidInputException("Invalid input: State not recognised or we do not sell in that state.\n");
			} catch (InvalidInputException e) {
				System.out.println(e.getLocalizedMessage());
			}
		}
		
		List<Product> availableProducts = service.getProducts();
		view.print("");
		view.displayProducts(availableProducts);
		Product product = null;
		while (product == null) {
			String productString = view.getInput("Product type: ");
			try {
				product = service.findProduct(productString);			
				if (product == null) throw new InvalidInputException("Invalid input: Product is not recognised.\n");
			} catch (InvalidInputException e) {
				System.out.println(e.getLocalizedMessage());
			}
		}
		
		BigDecimal area = view.getInput("Area (min 100sqft): ", new OrderValidators.ValidateArea(), new OrderConverters.ConvertToBigDecimal());
		view.print("");

		return new Order(orderDate, customerName, state, product, area);
	}

}
