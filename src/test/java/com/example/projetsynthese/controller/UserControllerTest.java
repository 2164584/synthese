package com.example.projetsynthese.controller;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.projetsynthese.model.Product;
import com.example.projetsynthese.repository.ProductRepository;
import com.example.projetsynthese.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private UserController userController;

    @Test
    public void testGetAllSupercProducts() throws Exception {
        mockMvc.perform(get("/products/superc"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Arrange

        List<Product> expectedProducts = Arrays.asList(
                new Product(),
                new Product()
        );

        when(userService.getSuperCProduct()).thenReturn(expectedProducts);

        // Act

        List<Product> actualProducts = userController.getAllSupercProducts();

        // Assert

        assertNotNull(actualProducts);
        assertFalse(actualProducts.isEmpty());
        verify(userService, times(4)).getSuperCProduct();

    }

    @Test
    public void testUpdateSupercProducts() throws Exception {
        // Act and Assert: Perform POST request and verify the response
        mockMvc.perform(post("/products/update-superc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString("SuperC products updated successfully")));

        // Verify that the service method was called once
        verify(userService, times(1)).updateSuperCProduct();
    }

    @Test
    public void testGetAllMetroProducts() throws Exception {
        mockMvc.perform(get("/products/metro"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Arrange

        List<Product> expectedProducts = Arrays.asList(
                new Product(),
                new Product()
        );

        when(userService.getMetroProduct()).thenReturn(expectedProducts);

        // Act

        List<Product> actualProducts = userController.getAllMetroProducts();

        // Assert

        assertNotNull(actualProducts);
        assertFalse(actualProducts.isEmpty());
        verify(userService, times(4)).getMetroProduct();
    }

    @Test
    public void testUpdateMetroProducts() throws Exception {
        // Act and Assert: Perform POST request and verify the response
        mockMvc.perform(post("/products/update-metro")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString("Metro products updated successfully")));

        // Verify that the service method was called once
        verify(userService, times(1)).updateMetroProduct();
    }

    @Test
    public void testGetAllIGAProducts() throws Exception {
        mockMvc.perform(get("/products/iga"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Arrange

        List<Product> expectedProducts = Arrays.asList(
                new Product(),
                new Product()
        );

        when(userService.getIGAProduct()).thenReturn(expectedProducts);

        // Act

        List<Product> actualProducts = userController.getAllIGAProducts();

        // Assert

        assertNotNull(actualProducts);
        assertFalse(actualProducts.isEmpty());
        verify(userService, times(4)).getIGAProduct();
    }

    @Test
    public void testUpdateIGAProducts() throws Exception {
        // Act and Assert: Perform POST request and verify the response
        mockMvc.perform(post("/products/update-iga")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString("IGA products updated successfully")));

        // Verify that the service method was called once
        verify(userService, times(1)).updateIGAProduct();
    }

    @Test
    public void testGetAllMaxiProducts() throws Exception {
        mockMvc.perform(get("/products/maxi"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Arrange

        List<Product> expectedProducts = Arrays.asList(
                new Product(),
                new Product()
        );

        when(userService.getMaxiProduct()).thenReturn(expectedProducts);

        // Act

        List<Product> actualProducts = userController.getAllMaxiProducts();

        // Assert

        assertNotNull(actualProducts);
        assertFalse(actualProducts.isEmpty());
        verify(userService, times(4)).getMaxiProduct();
    }

    @Test
    public void testUpdateMaxiProducts() throws Exception {
        // Act and Assert: Perform POST request and verify the response
        mockMvc.perform(post("/products/update-maxi")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString("Maxi products updated successfully")));

        // Verify that the service method was called once
        verify(userService, times(1)).updateMaxiProduct();
    }
}
