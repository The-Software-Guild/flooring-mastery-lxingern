package com.wileyedge.view;

import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;

import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

import com.wileyedge.model.Order;
import com.wileyedge.model.Product;

@Component
public class OrderViewConsoleImpl implements OrderView {

	private Scanner sc;
	
	
	public OrderViewConsoleImpl() {
		this.sc = new Scanner(System.in);
	}

	@Override
	public void displayMenu() {
		System.out.println("What would you like to do? Provide the number corresponding with the action.");
		System.out.println("1. Display Orders\n" + 
				"2. Add an Order\n" + 
				"3. Edit an Order\n" +
				"4. Remove an Order\n" + 
//				"5. Export All Data\n" +
				"5. Quit\n");
	}

	@Override
	public void displayOrders(List<Order> orders) {
		displayOrderHeader();
		orders.stream().forEach(order -> System.out.println(order));
		System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println();
	}
	
	@Override
	public void displayOrder(Order order) {
		displayOrderHeader();
		System.out.println(order);
		System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println();
	}
	
	
	@Override
	public void displayProducts(List<Product> products) {
		System.out.println("-----------------------------------------");
		System.out.printf("%-12s | %-8s | %-15s\n", 
							"Product Type",
							"Cost PSF",
							"Labour Cost PSF"
							);
		System.out.println("-----------------------------------------");
		products.stream().forEach(product -> System.out.println(product));
		System.out.println("-----------------------------------------");
		System.out.println();
	}
	
	@Override
	public String getInput(String prompt) {
		System.out.printf(prompt);
		String input = sc.nextLine();
		return input.trim();
	}

	@Override
	public <T> T getInput(String prompt, Predicate<String> validator, Function<String, T> converter) {
		boolean isValid = false;
		String input = null;
		T result = null;
		
		while (!isValid) {
			System.out.printf(prompt);
			input = sc.nextLine();
			isValid = validator.test(input);
		}
		
		result = converter.apply(input);
		
		return result;
	}
	
	@Override
	public String getInputForUpdate(String prompt, String currData) {
		System.out.printf(prompt + " (" + currData + "): ");
		String input = sc.nextLine();
		return input.trim();
	}
	
	@Override
	public <T> T getInputForUpdate(String prompt, String currData, Predicate<String> validator, Function<String, T> converter) {
		boolean isValid = false;
		String input = null;
		T result = null;
		
		while (!isValid) {
			System.out.printf(prompt + " (" + currData + "): ");
			input = sc.nextLine();
			if (input.trim().length() == 0) return null;
			isValid = validator.test(input);
		}
		
		result = converter.apply(input);
		
		return result;
	}
	

	@Override
	public void print(String text) {
		System.out.println(text);
	}
	
	private void displayOrderHeader() {
		System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.printf("%-9s | %-20s | %-5s | %-8s | %-12s | %-8s | %-15s | %-10s | %-13s | %-11s | %-8s | %-8s\n", 
							"Order No.",
							"Customer Name",
							"State",
							"Tax Rate",
							"Product Type",
							"Cost PSF",
							"Labour Cost PSF",
							"Area",
							"Material Cost",
							"Labour Cost",
							"Tax",
							"Total"
							);
		System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
	}
	
	
	@PreDestroy
	private void closeScanner() {
		sc.close();
	}

}
