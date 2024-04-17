package com.example.projetsynthese.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

import com.example.projetsynthese.callAPI.IGA;
import com.example.projetsynthese.callAPI.Metro;
import com.example.projetsynthese.callAPI.SuperC;
import com.example.projetsynthese.model.Product;
import com.example.projetsynthese.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.util.Arrays;
import java.util.List;

@PrepareForTest({SuperC.class, Metro.class, IGA.class})
public class UserServiceTest {

    @InjectMocks
    private UserService userService;
    private ProductRepository productRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        productRepository = mock(ProductRepository.class);

        userService.productRepository = productRepository;
    }

    @Test
    public void testGetIGAProduct() {
        // Arrange: Create a list of products to be returned by the repository
        List<Product> expectedProducts = Arrays.asList(
                new Product(),
                new Product()
        );

        // Stub the findAllWithManufacturerIGA method to return the list of products
        when(productRepository.findAllWithManufacturerIGA()).thenReturn(expectedProducts);

        // Act: Call the getIGAProduct method
        List<Product> actualProducts = userService.getIGAProduct();

        // Assert: Verify that the method returns the expected list of products
        assertEquals(expectedProducts, actualProducts);

        // Verify that the method findAllWithManufacturerIGA was called once
        verify(productRepository, times(1)).findAllWithManufacturerIGA();
    }

    @Test
    public void testGetMetroProduct() {
        // Arrange: Create a list of products to be returned by the repository
        List<Product> expectedProducts = Arrays.asList(
                new Product(),
                new Product()
        );

        // Stub the findAllWithManufacturerIGA method to return the list of products
        when(productRepository.findAllWithManufacturerMetro()).thenReturn(expectedProducts);

        // Act: Call the getIGAProduct method
        List<Product> actualProducts = userService.getMetroProduct();

        // Assert: Verify that the method returns the expected list of products
        assertEquals(expectedProducts, actualProducts);

        // Verify that the method findAllWithManufacturerIGA was called once
        verify(productRepository, times(1)).findAllWithManufacturerMetro();
    }

    @Test
    public void testGetSuperCProduct() {
        // Arrange: Create a list of products to be returned by the repository
        List<Product> expectedProducts = Arrays.asList(
                new Product(),
                new Product()
        );

        // Stub the findAllWithManufacturerIGA method to return the list of products
        when(productRepository.findAllWithManufacturerSuperC()).thenReturn(expectedProducts);

        // Act: Call the getIGAProduct method
        List<Product> actualProducts = userService.getSuperCProduct();

        // Assert: Verify that the method returns the expected list of products
        assertEquals(expectedProducts, actualProducts);

        // Verify that the method findAllWithManufacturerIGA was called once
        verify(productRepository, times(1)).findAllWithManufacturerSuperC();
    }
//
//    @Test
//    public void testUpdateSuperCProduct() {
//        // Mock the static variable and method in the SuperC class
//        PowerMockito.mockStatic(SuperC.class);
//        when(SuperC.isFecthing).thenReturn(false);
//
//        // Act
//        userService.updateSuperCProduct();
//
//        // Verify that the static method getSupercDatas() is called
//        verifyStatic(SuperC.class);
//        SuperC.getSupercDatas();
//    }
//
//    @Test
//    public void testUpdateMetroProduct() {
//        // Mock the static variable and method in the Metro class
//        PowerMockito.mockStatic(Metro.class);
//        when(Metro.isFecthing).thenReturn(false);
//
//        // Act
//        userService.updateMetroProduct();
//
//        // Verify that the static method getMetroDatas() is called
//        verifyStatic(Metro.class);
//        Metro.getMetroDatas();
//    }
//
//    @Test
//    public void testUpdateIGAProduct() {
//        // Mock the static variable and method in the IGA class
//        PowerMockito.mockStatic(IGA.class);
//        when(IGA.isFetching).thenReturn(false);
//
//        // Act
//        userService.updateIGAProduct();
//
//        // Verify that the static method getIGADatas() is called
//        verifyStatic(IGA.class);
//        IGA.getIGADatas();
//    }
}
