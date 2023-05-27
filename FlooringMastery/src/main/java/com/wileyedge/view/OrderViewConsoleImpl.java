package com.wileyedge.view;

import java.util.Scanner;

import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

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
			} catch (NumberFormatException | InvalidInputException e1) {
				try {
					throw new InvalidInputException("Invalid input: Input must be an integer between 1 and 6 (inclusive).\n");					
				} catch (InvalidInputException e2) {
					System.out.println(e2.getMessage());
				}
			} 
		}
		
		return action;
	}
	
	@PreDestroy
	private void closeScanner() {
		sc.close();
	}

}
