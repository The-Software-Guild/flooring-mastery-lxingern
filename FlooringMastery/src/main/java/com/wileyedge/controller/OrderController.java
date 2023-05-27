package com.wileyedge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.wileyedge.service.OrderService;
import com.wileyedge.view.OrderView;

@Controller
public class OrderController {

	@Autowired
	private OrderService service;
	
	@Autowired
	private OrderView view;

	public void run() {
		view.print("<<Flooring Program>>");
		boolean appRunning = true;
		
		while (appRunning) {
			view.displayMenu();		
			int action = view.getAction();
			switch (action) {
				case 1:
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
}
