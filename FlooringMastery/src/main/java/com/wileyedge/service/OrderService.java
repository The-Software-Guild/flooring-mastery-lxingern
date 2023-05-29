package com.wileyedge.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wileyedge.dao.OrderDao;
import com.wileyedge.exceptions.OrderNotFoundException;
import com.wileyedge.model.Order;

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
