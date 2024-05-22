package com.tokoteratai.project111.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tokoteratai.project111.model.Account;

@Repository
public interface AccountRepository extends JpaRepository <Account, String>{

    
    Optional<Account> findByUsername(String username);

}
