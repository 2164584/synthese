package com.example.projetsynthese.repository;

import com.example.projetsynthese.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.manufacturer = 'SuperC'")
    List<Product> findAllWithManufacturerSuperC();

    @Query("SELECT p FROM Product p WHERE p.manufacturer = 'Metro'")
    List<Product> findAllWithManufacturerMetro();

    @Query("SELECT p FROM Product p WHERE p.manufacturer = 'IGA'")
    List<Product> findAllWithManufacturerIGA();

    @Query("SELECT p FROM Product p WHERE p.manufacturer = 'Maxi'")
    List<Product> findAllWithManufacturerMaxi();

}
