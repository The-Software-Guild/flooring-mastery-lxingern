package com.wileyedge.model;

import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Predicate;

import com.wileyedge.exceptions.InvalidInputException;

public final class OrderValidators {
	
	public static class ValidateOrderNo implements Predicate<String> {

		@Override
		public boolean test(String orderNoString) {
			try {
				int orderNo = Integer.parseInt(orderNoString);
				if (orderNo < 0) throw new InvalidInputException("Invalid input: Order no. cannot be negative.\n");
				return true;
			} catch (NumberFormatException e) {
				System.out.println("Invalid input: Input must be an integer.\n");
			} 
			return false;
		}

	}

	public static class ValidateAction implements Predicate<String> {

		@Override
		public boolean test(String actionString) {
			try {
				int action = Integer.parseInt(actionString);
				if (action < 1 || action > 5) throw new InvalidInputException("Invalid input: Input must be between 1 and 5 (inclusive).\n");
				return true;
			} catch (NumberFormatException e) {
				System.out.println("Invalid input: Input must be an integer.\n");
			} catch (InvalidInputException e) {
					System.out.println(e.getLocalizedMessage());
			} 
			return false;
		}
		
	}
	
	public static class ValidateDate implements Predicate<String> {

		@Override
		public boolean test(String dateString) {
			return isDateValid(dateString);
		}
		
	}
	
	public static class ValidateDateForNewOrder implements Predicate<String> {

		@Override
		public boolean test(String dateString) {
			if (isDateValid(dateString)) {
				try {
					LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("MMddyyyy"));
					if (date.isBefore(LocalDate.now())) {
						throw new InvalidInputException("Invalid input: Date must be in the future.\n");
					}					
					return true;
				} catch (InvalidInputException e) {
					System.out.println(e.getLocalizedMessage());
				}
			}
			return false;
		}
		
	}
	
	public static class ValidateName implements Predicate<String> {

		@Override
		public boolean test(String name) {
			try {
				if (name.trim().length() == 0) {
					throw new InvalidInputException("Invalid input: Name cannot be blank.\n");
				} else if (!name.matches("[a-zA-z0-9 .,]+")) {
					throw new InvalidInputException("Invalid input: Name can only have alphanumeric, period and comma characters.\n");
				}
				return true;
			} catch (InvalidInputException e) {
				System.out.println(e.getLocalizedMessage());
			}
			return false;
		}
		
	}
	
	public static class ValidateArea implements Predicate<String> {

		@Override
		public boolean test(String areaString) {
			try {
				BigDecimal area = new BigDecimal(areaString);
				if (area.compareTo(new BigDecimal("0")) == -1) {
					throw new InvalidInputException("Invalid input: Input cannot be negative.\n");
				} else if (area.compareTo(new BigDecimal("100")) == -1) {
					throw new InvalidInputException("Invalid input: Minimum area is 100.\n");
				}
				return true;
			} catch (NumberFormatException e) {
				System.out.println("Invalid input: Input must be a decimal number.\n");
			}catch (InvalidInputException e) {
				System.out.println(e.getLocalizedMessage());
			}
			return false;
		}
		
	}
	
	public static class ValidateConfirmation implements Predicate<String> {

		@Override
		public boolean test(String input) {
			try {
				String parsedInput = input.trim().toLowerCase();
				if (!(parsedInput.equals("y") || parsedInput.equals("n"))) throw new InvalidInputException("Invalid input: Input must be either 'Y' or 'N'.\n");
				return true;
			} catch (InvalidInputException e) {
					System.out.println(e.getLocalizedMessage());
			} 
			return false;
		}
		
	}
	
	private static boolean isDateValid(String dateString) {
		try {
			LocalDate.parse(dateString, DateTimeFormatter.ofPattern("MMddyyyy"));				
			return true;
		} catch (DateTimeException e) {
			System.out.println("Invalid input: Date must be a valid date in the format MMDDYYYY.\n");
		} 
		return false;
	}

}
