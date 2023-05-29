package com.wileyedge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.wileyedge.controller.OrderController;

@SpringBootApplication
public class FlooringMasteryApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(FlooringMasteryApplication.class, args);
		OrderController controller = context.getBean(OrderController.class);
		controller.run();
	}

}
