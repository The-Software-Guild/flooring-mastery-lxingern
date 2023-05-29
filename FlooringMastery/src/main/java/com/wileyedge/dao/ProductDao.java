package com.wileyedge.dao;

import java.io.FileNotFoundException;
import java.util.List;

import com.wileyedge.model.Product;

public interface ProductDao {

	List<Product> getProducts() throws FileNotFoundException;
	Product getProductByProductType(String productType) throws FileNotFoundException;
	
}
