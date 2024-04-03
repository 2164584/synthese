package com.example.projetsynthese.repository;

import com.example.projetsynthese.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuperCRepository extends JpaRepository<Product, Long> {
}
