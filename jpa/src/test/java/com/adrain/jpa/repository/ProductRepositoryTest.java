package com.adrain.jpa.repository;

import com.adrain.jpa.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class ProductRepositoryTest {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Test
	@DisplayName("观察数据库中的ElementCollection变化")
	void insertData() {
		Product product = new Product("tea", new BigDecimal(1.5), "nodiscount");
		Map<String, String> attributes = new HashMap<>();
		product.setAttribute("category", "red tea");
		product.setAttribute("producer", "Chaozhou");
		productRepository.save(product);
	}
	
	@Test
	@DisplayName("观察数据被删后，级联的数据是否一起也删除了")
	void deleteData() {
		this.productRepository.deleteById(1L);
	}
	
	@Test
	void findByDescriptionContaining() {
	
	}
	
	@Test
	void findByAttributeAndValue() {
	}
}