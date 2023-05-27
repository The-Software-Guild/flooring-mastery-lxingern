package com.wileyedge.view;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

import com.wileyedge.model.Order;

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
				"5. Export All Data\n" +
				"6. Quit\n");
	}

	@Override
	public void print(String text) {
		System.out.println(text);
	}

	@Override
	public int getAction() {
		boolean inputIsValid = false;
		int action = 0;
		
		while (!inputIsValid) {
			System.out.print("Your selection: ");
			
			try {
				action = Integer.parseInt(sc.nextLine());
				if (action < 1 || action > 6) throw new InvalidInputException();
				inputIsValid = true;
				System.out.println();
			} catch (NumberFormatException e) {
				System.out.println("Invalid input: Input must be an integer.\n");
			} catch (InvalidInputException e) {
					System.out.println("Invalid input: Input must be between 1 and 6 (inclusive).\n");
			} 
		}
		
		return action;
	}
	
	@PreDestroy
	private void closeScanner() {
		sc.close();
	}

	@Override
	public String getDate() {
		boolean inputIsValid = false;
		String date = null;
		
		while (!inputIsValid) {
			System.out.print("Please provide the date in the format MMDDYYYY: ");
			date = sc.nextLine();
			try {
				LocalDate.parse(date, DateTimeFormatter.ofPattern("MMddyyyy"));			
				inputIsValid = true;
			} catch (DateTimeException e) {
				System.out.println("Invalid input: Date must be a valid date in the format MMDDYYYY.\n");
			}
		}
		
		return date;
	}

	@Override
	public void displayOrders(List<Order> orders) {
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
		orders.stream().forEach(order -> System.out.println(order));
		System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println();
	}

}
