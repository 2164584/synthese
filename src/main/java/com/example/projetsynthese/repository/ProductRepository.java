package com.example.projetsynthese.repository;

import com.example.projetsynthese.dto.ProductDTO;
import com.example.projetsynthese.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.manufacturer = 'Super C'")
    List<ProductDTO> findAllWithManufacturerSuperC();
}
