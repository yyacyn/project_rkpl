package com.tokoteratai.project.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tokoteratai.project.models.Product;

public interface ProductRepository extends JpaRepository<Product, String> {

}
