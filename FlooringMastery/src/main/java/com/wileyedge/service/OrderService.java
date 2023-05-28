package com.wileyedge.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wileyedge.dao.OrderDao;
import com.wileyedge.model.Order;
import com.wileyedge.model.Product;
import com.wileyedge.model.State;

@Service
public class OrderService {

	@Autowired
	private OrderDao dao;

	public List<Order> getOrdersForDate(LocalDate date) {
		List<Order> orders;
		
		try {
			orders = dao.getOrdersForDate(date);			
		} catch (FileNotFoundException e) {
			return null;
		}
		
		return orders;
	}
	
	public List<Product> getProducts() {
		return dao.getProducts();
	}

	public State findState(String stateString) {
		List<State> states = dao.getStates();
		Optional<State> state = states.stream().filter(s -> s.getStateAbbrev().equals(stateString)).findFirst();
		if (state.isPresent()) return state.get();
		return null;
	}

	public Product findProduct(String productString) {
		List<Product> products = getProducts();
		Optional<Product> product = products.stream().filter(p -> p.getProductType().equals(productString)).findFirst();
		if (product.isPresent()) return product.get();
		return null;
	}

	public boolean createOrder(Order newOrder) {
		try {
			dao.createOrder(newOrder);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
}
