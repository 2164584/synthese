package com.example.projetsynthese.controller;

import com.example.projetsynthese.dto.ProductDTO;
import com.example.projetsynthese.model.Product;
import com.example.projetsynthese.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/products")
public class UserController {

    @Autowired
    private UserService userService;
    public UserController() {}


    @GetMapping("/superc")
    public List<Product> getAllSupercProducts() {
        return userService.getSuperCProduct();
    }

    @PostMapping("/update-superc")
    public ResponseEntity<String> updateSupercProducts() {
        userService.updateSuperCProduct();
        return ResponseEntity.ok("SuperC products updated successfully");
    }

    @PostMapping("/update-metro")
    public ResponseEntity<String> updateMetroProducts() {
        userService.updateMetroProduct();
        return ResponseEntity.ok("Metro products updated successfully");
    }

    @GetMapping("/metro")
    public List<Product> getAllMetroProducts() {
        return userService.getMetroProduct();
    }

    @GetMapping("/iga")
    public List<Product> getAllIGAProducts() {
        return userService.getIGAProduct();
    }
}
