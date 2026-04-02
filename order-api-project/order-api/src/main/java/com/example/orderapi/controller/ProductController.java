package com.example.orderapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.orderapi.dto.CreateProductRequest;
import com.example.orderapi.dto.ProductResponse;
import com.example.orderapi.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {

	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProductResponse create(@Valid @RequestBody CreateProductRequest request) {
		return productService.create(request);
	}

	@GetMapping
	public List<ProductResponse> findAll() {
		return productService.findAll();
	}

	@GetMapping("/{id}")
	public ProductResponse findById(@PathVariable Long id) {
		return productService.findById(id);
	}

}
