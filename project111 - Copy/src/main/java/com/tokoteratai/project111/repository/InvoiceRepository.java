package com.tokoteratai.project111.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tokoteratai.project111.model.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository <Invoice, Integer>{

    // @Modifying
    // @Transactional
    // @Query(value = "DELETE FROM Invoice WHERE id = :id", nativeQuery = true)
    // void deleteById(@Param("id") Integer id);

}
