package com.example.orderapi.controller;

import com.example.orderapi.entity.Customer;
import com.example.orderapi.entity.Product;
import com.example.orderapi.repository.CustomerRepository;
import com.example.orderapi.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private ProductRepository productRepository;

	@Test
	void shouldCreateOrderAndReturn201() throws Exception {
		Customer c = new Customer("customer1", "customer1@example.com");
		Customer customer = customerRepository.save(c);

		Product p = new Product("Mouse", new BigDecimal("50.00"));
		Product product = productRepository.save(p);

		String body = String.format("""
				{
				  "customerId": %d,
				  "items": [
				    {
				      "productId": %d,
				      "quantity": 2
				    }
				  ]
				}
				""", customer.getId(), product.getId());

		mockMvc.perform(post("/orders").contentType(MediaType.APPLICATION_JSON).content(body))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.customerId").value(customer.getId()))
				.andExpect(jsonPath("$.status").value("CREATED")).andExpect(jsonPath("$.totalAmount").value(100.00));
	}

	@Test
	void shouldReturn400WhenCreateOrderPayloadIsInvalid() throws Exception {
		String body = """
				{
				  "items": []
				}
				""";

		mockMvc.perform(post("/orders").contentType(MediaType.APPLICATION_JSON).content(body))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.message").value("Validation error"));
	}

	@Test
	void shouldReturn404WhenOrderDoesNotExist() throws Exception {
		mockMvc.perform(get("/orders/9999")).andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").value("Order not found with id=9999"));
	}

}
