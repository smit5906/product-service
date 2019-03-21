package com.myretail.productservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.myretail.productservice.repository.ProductRepository;

@SpringBootApplication
@EnableMongoRepositories
@ComponentScan
public class ProductServiceApplication {
	
	@Autowired
	ProductRepository productRepository;
	
	public static void main(String[] args) {
		
		SpringApplication.run(ProductServiceApplication.class, args);
	}

}