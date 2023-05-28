package com.wileyedge.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wileyedge.dao.OrderDao;
import com.wileyedge.exceptions.OrderNotFoundException;
import com.wileyedge.model.Order;
import com.wileyedge.model.Product;
import com.wileyedge.model.State;

@Service
public class OrderService {

	@Autowired
	private OrderDao dao;

	public List<Order> getOrdersForDate(LocalDate date) throws FileNotFoundException, OrderNotFoundException {
		return dao.getOrdersForDate(date);			
	}
	
	public Order getOrder(LocalDate date, int orderNo) throws FileNotFoundException, OrderNotFoundException {
		return dao.getOrder(date, orderNo);
	}
	
	public List<Product> getProducts() throws FileNotFoundException {
		return dao.getProducts();
	}

	public State findState(String stateString) throws FileNotFoundException {
		List<State> states = dao.getStates();
		Optional<State> state = states.stream().filter(s -> s.getStateAbbrev().equals(stateString)).findFirst();
		if (state.isPresent()) return state.get();
		return null;
	}

	public Product findProduct(String productString) throws FileNotFoundException {
		List<Product> products = getProducts();
		Optional<Product> product = products.stream().filter(p -> p.getProductType().equals(productString)).findFirst();
		if (product.isPresent()) return product.get();
		return null;
	}

	public void createOrder(Order newOrder) throws IOException {
		dao.createOrder(newOrder);
	}

	public void updateOrder(Order order) throws IOException {
		dao.updateOrder(order);
	}

	public void deleteOrder(Order orderToRemove) throws IOException {
		dao.deleteOrder(orderToRemove);
	}
	
}
