package com.example.productservice;

import com.example.productservice.repository.ProductRepo;
import com.example.productservice.request.ProductRequest;
import com.example.productservice.response.ProductResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {


    // this is an integration test

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductRepo productRepo;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    void shouldCreateProduct() throws Exception {

        ProductRequest productRequest = getProductRequest();
        String productRequestString = objectMapper.writeValueAsString(productRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productRequestString))
                .andExpect(status().isCreated()
                );
        Assertions.assertEquals(1, productRepo.findAll().size());
    }

//	@Test
//	void shouldListProducts(HttpServletResponse response) throws Exception {
//		ProductResponse productResponse = getProductResponse(response);
//		String productResponseString = objectMapper.writeValueAsString(productResponse);
//		mockMvc.perform(MockMvcRequestBuilders.get("/api/product")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(productResponseString))
//				.andExpect(status().isOk());
//		Assertions.assertEquals(1,productRepo.findAll());

//	}

    private ProductRequest getProductRequest() {
        return ProductRequest.builder()
                .name("Nokia A-15")
                .description("Nokia A-15")
                .price(BigDecimal.valueOf(30000))
                .build();

    }

    private ProductResponse getProductResponse(HttpServletResponse response) {
        return (ProductResponse) response;
    }

//    @ExtendWith(HttpServletResponseResolver.class)
//    @DisplayName("Configuration Tests")
//    static class Config {
//    }

}
