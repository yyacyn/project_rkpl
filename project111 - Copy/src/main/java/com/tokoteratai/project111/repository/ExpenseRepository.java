package com.tokoteratai.project111.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tokoteratai.project111.model.Expense;

import jakarta.transaction.Transactional;

@Repository
public interface ExpenseRepository extends JpaRepository <Expense, Integer>{
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Expense", nativeQuery = true)
    void deleteAllExpenses();
}
