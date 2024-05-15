package com.tokoteratai.project111.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tokoteratai.project111.model.Product;

import jakarta.transaction.Transactional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Product WHERE code = :code", nativeQuery = true)
    void deleteByCode(@Param("code") Integer code);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Product SET name = :name, category = :category, s_price = :s_price, p_price = :p_price, stock = :stock WHERE code = :code", nativeQuery = true)
    void updateByCode(@Param("name") String name, @Param("category") String category, @Param("s_price") Integer s_price, @Param("p_price") Integer p_price, @Param("stock") Integer stock, @Param("code") Integer code);
}
