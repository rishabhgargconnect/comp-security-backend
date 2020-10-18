package com.example.ComputerSecurity.repositories;

import com.example.ComputerSecurity.entities.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, String> {

    @Override
    Optional<Product> findById(String id);

    List<Optional<Product>> findByCategoryAndName(String category, String name);

    @Override
    Iterable<Product> findAll();

    @Query(value = "select * from products where id= :abc", nativeQuery = true)
    Object findCustomById(String abc);

    @Query(value = "select * from products where category= :category", nativeQuery = true)
    List<Product> findProductsByCategory(String category);
}