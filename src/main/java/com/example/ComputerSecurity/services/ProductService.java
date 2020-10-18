package com.example.ComputerSecurity.services;

import com.example.ComputerSecurity.entities.Product;
import com.example.ComputerSecurity.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void getProductById(String id) {

        Object customById = productRepository.findCustomById(id);
        System.out.println("output is" + customById);
        Optional<Product> byId = productRepository.findById(id);
        if (byId.isPresent()) {
            Product product = byId.get();
            System.out.println("prod = " + product);
        }
        Iterable<Product> all = productRepository.findAll();
        Iterator<Product> iterator = all.iterator();
        while (iterator.hasNext()) {
            Product next = iterator.next();
            System.out.println(next);
            System.out.println("--------");
        }
    }


    public void getProductsByCategory(String category) {

        List<Product> productsByCategory = productRepository.findProductsByCategory(category);

        for (Product product : productsByCategory) {
            System.out.println(product.getName());
        }
    }
}
