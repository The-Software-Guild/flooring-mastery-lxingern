package com.wileyedge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.wileyedge.service.OrderService;
import com.wileyedge.view.OrderView;

@Controller
public class OrderController {

	@Autowired
	private OrderService service;
	
	@Autowired
	private OrderView view;
}
