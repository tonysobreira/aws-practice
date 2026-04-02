package com.example.orderapi.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.orderapi.dto.CreateProductRequest;
import com.example.orderapi.dto.ProductResponse;
import com.example.orderapi.entity.Product;
import com.example.orderapi.exception.ResourceNotFoundException;
import com.example.orderapi.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;

	public ProductServiceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	@Transactional
	public ProductResponse create(CreateProductRequest request) {
		Product p = new Product(request.name(), request.price());
		Product product = productRepository.save(p);
		return toResponse(product);
	}

	@Override
	@Transactional(readOnly = true)
	public ProductResponse findById(Long id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found with id=" + id));

		return toResponse(product);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductResponse> findAll() {
		return productRepository.findAll().stream().map(this::toResponse).toList();
	}

	private ProductResponse toResponse(Product product) {
		return new ProductResponse(product.getId(), product.getName(), product.getPrice());
	}

}
