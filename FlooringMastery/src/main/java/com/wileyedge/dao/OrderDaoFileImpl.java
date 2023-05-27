package com.wileyedge.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Repository;

import com.wileyedge.model.Product;
import com.wileyedge.model.State;

@Repository
public class OrderDaoFileImpl implements OrderDao {
	
//	@Value("${products-file-path}")
	private String productsFile;
	private List<Product> products;
//	
//	@Value("${taxes-file-path}")
	private String taxesFile;
	private List<State> states;
	
	public OrderDaoFileImpl() throws FileNotFoundException {
		this.productsFile = "data/data/Products.txt";
		this.products = new ArrayList<>();
		loadProducts();
		
		this.taxesFile = "data/data/Taxes.txt";
		this.states = new ArrayList<>();
		loadStates();
	}
	
	private void loadProducts() throws FileNotFoundException {
		Scanner sc = new Scanner(new BufferedReader(new FileReader(productsFile)));
		sc.nextLine();
		
		while (sc.hasNextLine()) {
			String currentLine = sc.nextLine();
			String[] fields = currentLine.split(",");
			Product product = new Product(fields[0], new BigDecimal(fields[1]), new BigDecimal(fields[1]));
			this.products.add(product);
		}
	}
	
	private void loadStates() throws FileNotFoundException {
		Scanner sc = new Scanner(new BufferedReader(new FileReader(taxesFile)));
		sc.nextLine();
		
		while (sc.hasNextLine()) {
			String currentLine = sc.nextLine();
			String[] fields = currentLine.split(",");
			State state = new State(fields[0], fields[1], new BigDecimal(fields[2]));
			this.states.add(state);
		}
	}
}
