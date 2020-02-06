package com.adrain.jpa;

import com.adrain.jpa.core.EmailAddress;
import com.adrain.jpa.entity.Address;
import com.adrain.jpa.entity.Customer;
import com.adrain.jpa.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * @Author AdrainHuang
 * @Date 2019/8/4 12:24
 **/
@SpringBootTest
@ActiveProfiles("test")
@Slf4j
public class TestCase {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Test
	@DisplayName("测试@Table(uniqueConstraints={@UniqueConstraint(columnNames={\"email\"})})唯一性约束")
	@Disabled
	public void testConstraintKey() {
		Customer customer = new Customer("adrain", "Huang");
		Address address = new Address("TianHe", "Gz", "China");
		customer.add(address);
		customer.setEmailAddress(new EmailAddress("silent@163.com"));
		this.customerRepository.save(customer);
//
		Customer customer1 = new Customer("adrain", "Huang");
		Address address1 = new Address("TianHe", "Gz", "China");
		customer1.add(address1);
		customer1.setEmailAddress(new EmailAddress("silent@163.com"));
		Assertions.assertThrows(Exception.class, () -> {
			this.customerRepository.save(customer1);
		});
	}
	
	@Test
	@DisplayName("邮箱格式不符")
	@Disabled
	public void testEmbeddable() {
		Customer customer = new Customer("adrain", "Huang");
		Address address = new Address("TianHe", "Gz", "China");
		customer.add(address);
		Assertions.assertThrows(Exception.class, () -> {
			customer.setEmailAddress(new EmailAddress("silent163.com"));
		}, "Invalid email address");
	}
	
	
	@Test
	@DisplayName("测试级联")
	@Order(1)
	void testCascade() {
		Customer customer = new Customer("adrain", "Huang");
		Address address = new Address("TianHe", "Gz", "China");
		customer.add(address);
		customer.setEmailAddress(new EmailAddress("silent@163.com"));
		this.customerRepository.save(customer);
		
	}
	
	@Test
	@DisplayName("测试orphanRemoval = true")
	@Order(2)
	void testOrphanRemoval() {
		long customerId = 1;
		this.customerRepository.deleteById(customerId);
	}
}
