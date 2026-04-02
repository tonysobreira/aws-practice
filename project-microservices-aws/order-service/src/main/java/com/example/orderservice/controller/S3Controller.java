package com.example.orderservice.controller;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.orderservice.service.S3Service;

@RestController
public class S3Controller {

	private final S3Service s3Service;

	public S3Controller(S3Service s3Service) {
		this.s3Service = s3Service;
	}

	@PostMapping("/upload")
	public String upload() {
		String key = "teste-" + UUID.randomUUID() + ".txt";
		String content = "hello aws + - " + LocalDateTime.now().toString();
		s3Service.upload("tony-order-service-bucket", key, content.getBytes());
		return "uploaded: " + key;
	}

}
