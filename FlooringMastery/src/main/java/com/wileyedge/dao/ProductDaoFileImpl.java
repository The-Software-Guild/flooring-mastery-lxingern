package com.wileyedge.dao;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

import com.wileyedge.model.Product;

@Repository
public class ProductDaoFileImpl extends DaoFileImpl implements ProductDao {
	
	private String productsFilePath;
	
	public ProductDaoFileImpl() {
		this.productsFilePath = "data/data/Products.txt";
	}

	public ProductDaoFileImpl(String productsFilePath) {
		this.productsFilePath = productsFilePath;
	}

	@Override
	public List<Product> getProducts() throws FileNotFoundException {		
		return loadListFromFile(productsFilePath, ",", (fields) -> {
			return new Product(fields[0], new BigDecimal(fields[1]), new BigDecimal(fields[2]));
		});		
	}
	
	public Product getProductByProductType(String productType) throws FileNotFoundException {
		List<Product> products = getProducts();
		Optional<Product> product = products.stream().filter(p -> p.getProductType().equals(productType)).findFirst();
		if (product.isPresent()) return product.get();
		return null;
	}

}
