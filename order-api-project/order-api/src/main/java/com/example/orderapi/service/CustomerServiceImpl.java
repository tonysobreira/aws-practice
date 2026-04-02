package com.example.orderapi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.orderapi.dto.CreateCustomerRequest;
import com.example.orderapi.dto.CustomerResponse;
import com.example.orderapi.entity.Customer;
import com.example.orderapi.exception.BusinessException;
import com.example.orderapi.exception.ResourceNotFoundException;
import com.example.orderapi.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository customerRepository;

	public CustomerServiceImpl(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@Override
	@Transactional
	public CustomerResponse create(CreateCustomerRequest request) {
		if (customerRepository.existsByEmail(request.email())) {
			throw new BusinessException("Customer email already exists");
		}

		Customer c = new Customer(request.name(), request.email());

		Customer customer = customerRepository.save(c);

		return new CustomerResponse(customer.getId(), customer.getName(), customer.getEmail());
	}

	@Override
	@Transactional(readOnly = true)
	public CustomerResponse findById(Long id) {
		Customer customer = customerRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found with id=" + id));

		return new CustomerResponse(customer.getId(), customer.getName(), customer.getEmail());
	}

}
