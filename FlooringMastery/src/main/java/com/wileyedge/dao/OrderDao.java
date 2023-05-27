package com.wileyedge.dao;

import java.io.FileNotFoundException;
import java.util.List;

import com.wileyedge.model.Order;

public interface OrderDao {

	List<Order> getOrdersForDate(String date) throws FileNotFoundException;

}
