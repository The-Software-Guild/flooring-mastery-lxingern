package com.wileyedge.service;

import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wileyedge.dao.ProductDao;
import com.wileyedge.model.Product;

@Service
public class ProductService {

	@Autowired
	ProductDao dao;

	public List<Product> getProducts() throws FileNotFoundException {
		return dao.getProducts();
	}

	public Product findProduct(String productString) throws FileNotFoundException {
		return dao.getProductByProductType(productString);
	}
	
}
