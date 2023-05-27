package com.wileyedge.service;

import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wileyedge.dao.OrderDao;
import com.wileyedge.model.Order;

@Service
public class OrderService {

	@Autowired
	private OrderDao dao;

	public List<Order> getOrdersForDate(String date) {
		List<Order> orders;
		
		try {
			orders = dao.getOrdersForDate(date);			
		} catch (FileNotFoundException e) {
			return null;
		}
		
		return orders;
	}
	
}
