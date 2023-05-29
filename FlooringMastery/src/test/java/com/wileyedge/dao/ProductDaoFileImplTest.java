package com.wileyedge.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.wileyedge.model.Product;

class ProductDaoFileImplTest {
	
	private ProductDaoFileImpl dao;
	
	public ProductDaoFileImplTest() throws FileNotFoundException {
		this.dao = new ProductDaoFileImpl("test-data/data/Products.txt");
	}

	@Test
	void getProductsReturnsProducts() throws FileNotFoundException {
		Product product = new Product("Carpet", new BigDecimal("2.25"), new BigDecimal("2.10"));
	
		List<Product> products = dao.getProducts();
		
		assertEquals(products.size(), 4);
		assertTrue(products.contains(product));
	}
	
	@Test
	void getProductByProductTypeReturnsExpectedProduct() throws FileNotFoundException {
		Product expectedProduct = new Product("Carpet", new BigDecimal("2.25"), new BigDecimal("2.10"));
		
		Product actualProduct = dao.getProductByProductType("Carpet");
		
		assertEquals(actualProduct, expectedProduct);
	}

	@Test
	void getProductByProductTypeReturnsNullIfProductNotFound() throws FileNotFoundException {
		Product actualProduct = dao.getProductByProductType("Stone");
		
		assertNull(actualProduct);
	}
}
