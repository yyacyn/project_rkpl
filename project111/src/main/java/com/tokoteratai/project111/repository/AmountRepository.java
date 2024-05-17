package com.tokoteratai.project111.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tokoteratai.project111.model.Amount;

import jakarta.transaction.Transactional;

@Repository
public interface AmountRepository extends JpaRepository <Amount, Integer>{

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Amount WHERE id = :id", nativeQuery = true)
    void deleteByCode(@Param("id") Integer id);

}
