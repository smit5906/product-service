package com.myretail.productservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.myretail.productservice.ProductService;
import com.myretail.productservice.dao.ProductDAO;
import com.myretail.productservice.data.Product;
import com.myretail.productservice.dto.ProductDTO;

@RestController
@RequestMapping("/products")
public class ProductServiceController {
	private static final Logger LOGGER= LoggerFactory.getLogger(ProductServiceController.class);
	
	@Autowired
	private ProductService productService;

	@Autowired
	private ProductDAO productDAO;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Product> getProduct(@PathVariable int id) throws Exception {
		
		LOGGER.debug("Entering getProduct for productId {}", id);
		
		Product product = null;

		try {
			product = productService.getProductById(id);
			if (product == null) {
				return new ResponseEntity<>(product, HttpStatus.NO_CONTENT);
			}
		} catch (HttpClientErrorException ex) {
			System.out.println(ex.getLocalizedMessage());
			return new ResponseEntity<>(ex.getStatusCode());
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		LOGGER.debug("Exiting getProduct for productId {}", id);
		return new ResponseEntity<>(product, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Product> updateProductPrice(@RequestBody Product productRequest, @PathVariable int id)
			throws Exception {
		
		LOGGER.debug("Entering updateProductPrice for productId {}", id);
		Product product = null;

		try {
			ProductDTO productDTO = productDAO.resourceObject(productRequest);
			product = productService.updateProductPrice(productDTO);
			if (product == null) {
				return new ResponseEntity<>(product, HttpStatus.NO_CONTENT);
			}
		} catch (HttpClientErrorException ex) {
			System.out.println(ex.getLocalizedMessage());
			return new ResponseEntity<>(ex.getStatusCode());
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		LOGGER.debug("Exiting updateProductPrice for productId {}", id);
		return new ResponseEntity<>(product, HttpStatus.OK);
	}
}