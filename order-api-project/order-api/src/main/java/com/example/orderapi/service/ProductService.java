package com.example.orderapi.service;

import com.example.orderapi.dto.CreateProductRequest;
import com.example.orderapi.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse create(CreateProductRequest request);
    ProductResponse findById(Long id);
    List<ProductResponse> findAll();
}
