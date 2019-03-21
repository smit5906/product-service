package com.myretail.productservice.test;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.myretail.productservice.ProductServiceApplication;
import com.myretail.productservice.data.PriceData;
import com.myretail.productservice.data.Product;
import com.myretail.productservice.dto.ProductDTO;
import com.myretail.productservice.repository.ProductRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ProductServiceApplication.class)
@WebAppConfiguration
public class ProductServiceTests {

	@Autowired
	ProductRepository repository;

	MockMvc mockMvc;

	@Autowired
	WebApplicationContext webApplicationContext;

	final static private int successfulID = 13860428;

	private ProductDTO productDTO;

	@Before
	public void setUp() {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();

		this.repository.deleteAll();

		this.productDTO = new ProductDTO(successfulID);
		this.productDTO.setTitle("The Big Lebowski (Blu-ray)");
		this.productDTO.setCurrency_code("USD");
		this.productDTO.setCurrentPrice(new BigDecimal(14.95));

		repository.save(productDTO);
		repository.findAll().forEach(System.out::println);
	}

	@Test
	public void testGetProductById() throws Exception {
		mockMvc.perform(get("/products/" + successfulID)).andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(successfulID)));
	}

	@Test
	public void testUpdateProductPrice() throws Exception {
		Product requestBody = new Product();
		PriceData priceData = new PriceData();
		priceData.setPrice(new BigDecimal(14.95));
		requestBody.setId(successfulID);
		requestBody.setPriceData(priceData);

		mockMvc.perform(put("/products/" + successfulID).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(requestBody))).andExpect(status().isOk())
				.andExpect(jsonPath("$.current_price.value", is(requestBody.getPriceData().getPrice())));
	}

	@Test
	public void testGetProductById_NotFound() throws Exception {
		mockMvc.perform(get("/products/" + 123456789)).andExpect(status().isNotFound());
	}

	@Test
	public void testUpdateProductPrice_NotFound() throws Exception {
		Product requestBody = new Product();
		PriceData priceData = new PriceData();
		priceData.setPrice(new BigDecimal(14.95));
		requestBody.setId(12345678);
		requestBody.setPriceData(priceData);

		mockMvc.perform(put("/products/" + requestBody.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(requestBody))).andExpect(status().isNotFound());
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}