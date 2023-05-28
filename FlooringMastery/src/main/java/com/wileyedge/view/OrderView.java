package com.wileyedge.view;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import com.wileyedge.model.Order;
import com.wileyedge.model.Product;

public interface OrderView {

	void displayMenu();
	void displayOrders(List<Order> orders);
	void displayOrder(Order order);
	void displayProducts(List<Product> products);
	String getInput(String prompt);
	<T> T getInput(String prompt, Predicate<String> validator, Function<String, T> converter);
	String getInputForUpdate(String prompt, String currData);
	<T> T getInputForUpdate(String prompt, String currData, Predicate<String> validator, Function<String, T> converter);
	void print(String text);
	
}
