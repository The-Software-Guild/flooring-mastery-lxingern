package com.wileyedge.view;

import java.util.List;

import com.wileyedge.model.Order;

public interface OrderView {

	void displayMenu();
	void print(String text);
	int getAction();
	String getDate();
	void displayOrders(List<Order> orders);
	
}
