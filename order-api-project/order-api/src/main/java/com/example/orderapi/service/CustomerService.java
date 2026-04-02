package com.example.orderapi.service;

import com.example.orderapi.dto.CreateCustomerRequest;
import com.example.orderapi.dto.CustomerResponse;

public interface CustomerService {
    CustomerResponse create(CreateCustomerRequest request);
    CustomerResponse findById(Long id);
}
