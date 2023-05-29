package com.wileyedge.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.stereotype.Repository;

import com.wileyedge.model.Product;

@Repository
public class ProductDaoFileImpl implements ProductDao {
	
	private String productsFilePath;
	
	public ProductDaoFileImpl() {
		this.productsFilePath = "data/data/Products.txt";
	}

	@Override
	public List<Product> getProducts() throws FileNotFoundException {
		List<Product> products = new ArrayList<>();
		
		try (Scanner sc = new Scanner(new BufferedReader(new FileReader(productsFilePath)))) {
			sc.nextLine();
			
			while (sc.hasNextLine()) {
				String currentLine = sc.nextLine();
				String[] fields = currentLine.split(",");
				Product product = new Product(fields[0], new BigDecimal(fields[1]), new BigDecimal(fields[2]));
				products.add(product);
			}			
		}
		
		return products;
	}
	
	public Product getProductByProductType(String productType) throws FileNotFoundException {
		List<Product> products = getProducts();
		Optional<Product> product = products.stream().filter(p -> p.getProductType().equals(productType)).findFirst();
		if (product.isPresent()) return product.get();
		return null;
	}

}
