package com.example.projetsynthese.controller;

import com.example.projetsynthese.model.Product;
import com.example.projetsynthese.service.UserService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/products")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/superc")
    public List<Product> getAllSupercProducts() {
        return userService.getSuperCProduct();
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
