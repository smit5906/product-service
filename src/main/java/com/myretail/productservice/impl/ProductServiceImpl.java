package com.myretail.productservice.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myretail.productservice.ProductService;
import com.myretail.productservice.dao.ProductDAO;
import com.myretail.productservice.data.Product;
import com.myretail.productservice.dto.ProductDTO;
import com.myretail.productservice.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
	
	private static final Logger LOGGER= LoggerFactory.getLogger(ProductServiceImpl.class);
	
	private static final String HOST = "https://redsky.target.com/v2/pdp/tcin/";
	private static final String EXCLUDES_PATH = "?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics";

	@Autowired
	private ProductDAO productDAO;

	@Autowired
	private ProductRepository productRepository;

	RestTemplate restfulTemplate = new RestTemplate();

	
	public Product getProductById(int id) throws JsonProcessingException, IOException {
		LOGGER.debug("Entering getProductById for product id {}", id);
		ProductDTO productDTO = productRepository.findByProductId(id);

		String productTitle = getProductTitle(id);

		if (productDTO == null) {
			productDTO = new ProductDTO(id);
		}

		productDTO.setTitle(productTitle);
		
		LOGGER.debug("Exiting getProductById for product id {}", id);
		return productDAO.resourceObject(productDTO);
	}

	private String getProductTitle(int productId) throws JsonProcessingException, IOException {
		LOGGER.debug("Entering getProductTitle for product id {}", productId);
		
		String responseBody = null;
		ResponseEntity<String> response = null;
		String productTitle = null;

		UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(getURIPath(productId)).build();

		response = restfulTemplate.getForEntity(uriComponents.encode().toUri(), String.class);
		responseBody = response.getBody();

		ObjectMapper mapper = new ObjectMapper();

		JsonNode productRootNode = mapper.readTree(responseBody);

		if (productRootNode != null)
			productTitle = productRootNode.get("product").get("item").get("product_description").get("title").asText();
		
		LOGGER.debug("Exiting getProductTitle for product id {}", productId);
		return productTitle;
	}

	private String getURIPath(int productId) {
		return HOST + productId + EXCLUDES_PATH;
	}
	
	public Product updateProductPrice(ProductDTO product) {
		
		int productId = product.getProductId();
		LOGGER.debug("Entering updateProductPrice for product id {}", productId);

		// get product from database
		ProductDTO existingProductDTO = productRepository.findByProductId(productId);

		if (existingProductDTO == null) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "404");
		}

		existingProductDTO.setCurrentPrice(product.getCurrentPrice());

		// updating the price in database
		ProductDTO updatedProductDTO = productRepository.save(existingProductDTO);
		
		LOGGER.debug("Exiting updateProductPrice for product id {}", productId);
		return productDAO.resourceObject(updatedProductDTO);
	}
	
}
