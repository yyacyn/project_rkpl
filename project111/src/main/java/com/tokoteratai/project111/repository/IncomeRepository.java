package com.tokoteratai.project111.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tokoteratai.project111.model.Income;

import jakarta.transaction.Transactional;

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

    @Modifying
    @Transactional
    @Query("DELETE FROM Income i WHERE i.oid IS NULL")
    void deleteByOidIsNull();
    
    @Query(value = "SELECT * FROM Income WHERE DATE(date) = CURDATE()", nativeQuery = true)
    List<Income> findAllByDate();

    Optional<Income> findById(Integer id);

    @Query(value = "SELECT * FROM Income WHERE invo_id = :invo_id", nativeQuery = true)
    List<Income> findByInvoId(@Param("invo_id") Integer invo_id);

}
