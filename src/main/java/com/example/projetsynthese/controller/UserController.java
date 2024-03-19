package com.example.projetsynthese.controller;

import com.example.projetsynthese.model.Product;
import com.example.projetsynthese.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/superc")
    public List<Product> getAllSupercProducts() {
        return userService.getSupercDatas();
    }

    @GetMapping("/metro")
    public List<Product> getAllMetroProducts() {
        return userService.getMetroDatas();
    }
}
