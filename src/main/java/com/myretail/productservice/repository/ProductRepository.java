package com.myretail.productservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.myretail.productservice.dto.ProductDTO;

@Repository("ProductRepository")
public interface ProductRepository extends MongoRepository<ProductDTO, String> {

	@Query("{ 'pid' : ?0 }")
	public ProductDTO findByProductId(int pid);

	@SuppressWarnings("unchecked")
	public ProductDTO save(ProductDTO productDao);

}