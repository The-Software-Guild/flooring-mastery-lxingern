package com.wileyedge.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

public final class OrderConverters {
	
	public static class ConvertToInt implements Function<String, Integer> {

		@Override
		public Integer apply(String input) {
			return Integer.parseInt(input);
		}
		
	}

	public static class ConvertToDate implements Function<String, LocalDate> {

		@Override
		public LocalDate apply(String dateString) {
			return LocalDate.parse(dateString, DateTimeFormatter.ofPattern("MMddyyyy"));
		}
		
	}
	
	public static class ConvertToString implements Function<String, String> {

		@Override
		public String apply(String input) {
			return input.trim();
		}
		
	}
	
	public static class ConvertToBigDecimal implements Function<String, BigDecimal> {

		@Override
		public BigDecimal apply(String input) {
			return new BigDecimal(input);
		}
		
	}
	
	public static class ConvertToBoolean implements Function<String, Boolean> {

		@Override
		public Boolean apply(String input) {
			if (input.trim().toLowerCase().equals("y")) {
				return true;
			}
			return false;
		}
		
	}
}
