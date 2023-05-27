package com.wileyedge.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.wileyedge.model.Order;
import com.wileyedge.service.OrderService;
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
			int action = view.getAction();
			switch (action) {
				case 1:
					displayOrders();
					break;
				case 2:
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
		String date = view.getDate();
		if (date == null) return;
		List<Order> orders = service.getOrdersForDate(date);
		if (orders == null) {
			view.print("No orders found for that date.\n");
			return;
		}
		view.displayOrders(orders);
	}
}
