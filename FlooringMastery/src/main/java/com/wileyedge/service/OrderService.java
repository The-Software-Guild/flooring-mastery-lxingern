package com.wileyedge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wileyedge.dao.OrderDao;

@Service
public class OrderService {

	@Autowired
	private OrderDao dao;
	
}
