package com.khai.admin;

import com.khai.admin.controller.ProductController;
import com.khai.admin.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes=AdminApplication.class)
@AutoConfigureMockMvc
public class ProductTests {
    @Autowired
    private ProductRepository productRepository;
    private MockMvc mockMvc;

//    @Before
//    public void setUp() throws Exception {
//        mockMvc = MockMvcBuilders.standaloneSetup( new ProductController(productRepository))
//                .build();
//    }

    @Test
    public void whenInsertingProducts_thenProductsAreCreated() throws Exception {
        this.mockMvc.perform(post("/products/insert/batch"))
                .andExpect(status().isCreated());
    }
}
