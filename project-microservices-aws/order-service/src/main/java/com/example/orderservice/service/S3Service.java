package com.example.orderservice.service;

import org.springframework.stereotype.Service;

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class S3Service {

	private final S3Client s3Client;

	public S3Service(S3Client s3Client) {
		this.s3Client = s3Client;
	}

	public void upload(String bucket, String key, byte[] content) {
		PutObjectRequest request = PutObjectRequest.builder().bucket(bucket).key(key).build();
		s3Client.putObject(request, RequestBody.fromBytes(content));

		// download
		GetObjectRequest getRequest = GetObjectRequest.builder().bucket(bucket).key(key).build();
		ResponseInputStream<GetObjectResponse> file = s3Client.getObject(getRequest);
		System.out.println(file.toString());
	}

}