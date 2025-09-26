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
        System.out.println("SuperC "+userService.getSuperCProduct().size());
        return userService.getSuperCProduct();
    }

    @PostMapping("/update-superc")
    public ResponseEntity<String> updateSupercProducts() {
        userService.updateSuperCProduct();
        return ResponseEntity.ok("SuperC products updated successfully");
    }

    @GetMapping("/superc-progress")
    public ResponseEntity<Float> getSupercProgress() {
        return ResponseEntity.ok(userService.getSuperCPercent());
    }

    @GetMapping("/metro")
    public List<Product> getAllMetroProducts() {
        System.out.println("Metro "+userService.getMetroProduct().size());
        return userService.getMetroProduct();
    }

    @PostMapping("/update-metro")
    public ResponseEntity<String> updateMetroProducts() {
        userService.updateMetroProduct();
        return ResponseEntity.ok("Metro products updated successfully");
    }

    @GetMapping("/iga")
    public List<Product> getAllIGAProducts() {

        System.out.println("IGA "+userService.getIGAProduct().size());
        return userService.getIGAProduct();
    }

    @PostMapping("/update-iga")
    public ResponseEntity<String> updateIGAProducts() {
        userService.updateIGAProduct();
        return ResponseEntity.ok("IGA products updated successfully");
    }

    @GetMapping("/iga-progress")
    public ResponseEntity<Float> getIGAProgress() {
        return ResponseEntity.ok(userService.getIGAPercent());
    }

    @GetMapping("/maxi")
    public List<Product> getAllMaxiProducts() {
        System.out.println("Maxi "+userService.getMaxiProduct().size());
        return userService.getMaxiProduct();
    }

    @PostMapping("/update-maxi")
    public ResponseEntity<String> updateMaxiProducts() {
        userService.updateMaxiProduct();
        return ResponseEntity.ok("Maxi products updated successfully");
    }
}
