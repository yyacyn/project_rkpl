package com.tokoteratai.project111.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tokoteratai.project111.model.Income;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Integer> {

    @Query("SELECT SUM(total) FROM Income WHERE paymethod = ?1")
    Integer sumTotalByPaymethod(String paymethod);

    @Query("SELECT SUM(total) FROM Income WHERE status = ?1")
    Integer sumTotalByStatus(String status);

    // @Query("SELECT SUM(total) FROM Income WHERE staus = ?1")
    // Integer sumTotal();

    void deleteAll();
    

    @Query(value = "SELECT i.p_code as product, SUM(i.qty) as totalQuantity "
            + "FROM Income i "
            + "GROUP BY i.p_code "
            + "ORDER BY totalQuantity DESC "
            + "LIMIT 1")
    Object[] getTopSellingProduct();
}
