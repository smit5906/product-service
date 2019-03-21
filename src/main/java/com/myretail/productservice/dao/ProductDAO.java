package com.myretail.productservice.dao;

import org.springframework.stereotype.Component;

import com.myretail.productservice.data.PriceData;
import com.myretail.productservice.data.Product;
import com.myretail.productservice.dto.ProductDTO;

@Component
public class ProductDAO {

	public Product resourceObject(ProductDTO productDTO) {

		Product product = null;
		if (productDTO != null) {
			product = new Product();

			product.setId(productDTO.getProductId());
			product.setTitle(productDTO.getTitle());

			PriceData priceData = new PriceData();
			priceData.setCurrencyCode(productDTO.getCurrency_code());
			priceData.setPrice(productDTO.getCurrentPrice());
			product.setPriceData(priceData);
		}
		return product;
	}

	public ProductDTO resourceObject(Product productRequest) {
		ProductDTO productDTO = new ProductDTO(productRequest.getId());
		productDTO.setCurrentPrice(productRequest.getPriceData().getPrice());
		return productDTO;
	}

}
