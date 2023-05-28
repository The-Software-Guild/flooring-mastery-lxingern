package com.wileyedge.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import com.wileyedge.model.Order;
import com.wileyedge.model.Product;
import com.wileyedge.model.State;

public interface OrderDao {

	List<Order> getOrdersForDate(LocalDate date) throws FileNotFoundException;
	Order getOrder(LocalDate date, int orderNo) throws FileNotFoundException;
	List<Product> getProducts() throws FileNotFoundException;
	List<State> getStates() throws FileNotFoundException;
	void createOrder(Order newOrder) throws IOException;
	void updateOrder(Order order) throws IOException;
	void deleteOrder(Order orderToRemove) throws IOException;

}
