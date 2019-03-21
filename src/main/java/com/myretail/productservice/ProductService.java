package com.myretail.productservice;

import com.myretail.productservice.data.Product;
import com.myretail.productservice.dto.ProductDTO;

public interface ProductService {
	
	Product getProductById(int id) throws Exception;

	Product updateProductPrice(ProductDTO product);
}
