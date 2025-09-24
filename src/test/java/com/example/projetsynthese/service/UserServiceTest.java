package com.example.projetsynthese.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

import com.example.projetsynthese.callAPI.IGA;
import com.example.projetsynthese.callAPI.Maxi;
import com.example.projetsynthese.callAPI.Metro;
import com.example.projetsynthese.callAPI.SuperC;
import com.example.projetsynthese.model.Product;
import com.example.projetsynthese.repository.ProductRepository;
import com.jayway.jsonpath.internal.function.numeric.Max;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
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

    @Test
    public void testGetMaxiProduct() {
        // Arrange: Create a list of products to be returned by the repository
        List<Product> expectedProducts = Arrays.asList(
                new Product(),
                new Product()
        );

        // Stub the findAllWithManufacturerIGA method to return the list of products
        when(productRepository.findAllWithManufacturerMaxi()).thenReturn(expectedProducts);

        // Act: Call the getIGAProduct method
        List<Product> actualProducts = userService.getMaxiProduct();

        // Assert: Verify that the method returns the expected list of products
        assertEquals(expectedProducts, actualProducts);

        // Verify that the method findAllWithManufacturerIGA was called once
        verify(productRepository, times(1)).findAllWithManufacturerMaxi();
    }

    @Test
    public void testUpdateSuperCProduct() {
        try (MockedStatic<SuperC> mockedSuperC = Mockito.mockStatic(SuperC.class)) {
            userService.updateSuperCProduct();
            mockedSuperC.verify(SuperC::getSupercDatas);
        }
    }

    @Test
    public void testUpdateMetroProduct() {
        try (MockedStatic<Metro> mockedMetro = Mockito.mockStatic(Metro.class)) {
            userService.updateMetroProduct();
            mockedMetro.verify(Metro::getMetroDatas);
        }
    }

    @Test
    public void testUpdateIGAProduct() {
        try (MockedStatic<IGA> mockedIGA = Mockito.mockStatic(IGA.class)) {
            userService.updateIGAProduct();
            mockedIGA.verify(IGA::getIGADatas);
        }
    }

    @Test
    public void testUpdateMaxiProduct() {
        try (MockedStatic<Maxi> mockedMaxi = Mockito.mockStatic(Maxi.class)) {
            userService.updateMaxiProduct();
            mockedMaxi.verify(Maxi::getMaxiDatas);
        }
    }
}
