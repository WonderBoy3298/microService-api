package org.wonderboy.productapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.wonderboy.productapp.configuration.JacksonConfig;
import org.wonderboy.productapp.dtos.ProductRequest;
import org.wonderboy.productapp.repository.ProductRepository;

import java.math.BigDecimal;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.http.ResponseEntity.status;


@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductAppApplicationTests {


	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");


	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry){
		dynamicPropertyRegistry.add("spring.data.mongodb.uri",mongoDBContainer::getReplicaSetUrl);
	}

	@Autowired
	private ProductRepository productRepository ;

	@Autowired
	private JacksonConfig jacksonConfig;
	@Autowired
	private MockMvc mock ;

	@Test
	void itShouldCreateProduct() throws Exception {
		// Given
		ProductRequest productRequest = createProd();
		String productRequestString = jacksonConfig.objectMapper().writeValueAsString(productRequest);
		//when
		mock.perform(MockMvcRequestBuilders.post("/api/product")
				.contentType(MediaType.APPLICATION_JSON)
				.content(productRequestString)).andExpect(status().isCreated());

		Assertions.assertEquals(1,productRepository.findAll().size());

	}

	ProductRequest createProd(){
		return ProductRequest.builder().name("iphone 13 mini")
				.description("iphone 13 mini")
				.price(BigDecimal.valueOf(1200))
				.build();
	}

	@Test
	void itShouldPass() {
		// Given
		// when
		// then
	}

	@Test
	void contextLoads() {
		// Given
		// when
		// then
	}




}