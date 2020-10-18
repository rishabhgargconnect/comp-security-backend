package com.example.ComputerSecurity.controllers;

import com.example.ComputerSecurity.dto.responseDto.SignInResponse;
import com.example.ComputerSecurity.services.AuthenticationService;
import com.example.ComputerSecurity.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductController {

    private final ProductService productService;

    @PostMapping(value = "product-by-id")
    @CrossOrigin(origins = "http://localhost:3000")
    public void productById(@RequestParam String productId) {
        productService.getProductById(productId);
    }


    @PostMapping(value = "product-by-category")
    @CrossOrigin(origins = "http://localhost:3000")
    public void productByCategory(@RequestParam String category) {

        System.out.println("category: " + category);
        productService.getProductsByCategory(category);
    }
}
