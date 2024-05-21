package com.tokoteratai.project111.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.tokoteratai.project111.model.Account;
import com.tokoteratai.project111.repository.AccountRepository;

import jakarta.annotation.PostConstruct;

// @Slf4j
// @Service
// public class AccountService {

//     private Account account;

//     @Autowired
//     private AccountRepository acrepo;

//     public AccountService(AccountRepository accountRepository) {
//         this.acrepo = accountRepository;
//     }

//     @Transactional
//     public Account authenticate(Account account ) {
//         Optional<Account> optionalAccount = acrepo.findByUsername(account.getUsername());

//         if (optionalAccount.isPresent() && BCrypt.checkpw(account.getPassword(), optionalAccount.get().getPassword())) {
//             return optionalAccount.get();
//         } else {
//             return null;
//         }
//     }

//     // Get all accounts
//     public List<Account> getAllAccounts() {
//         return acrepo.findAll();
//     }

//     // Get account by ID
//     public Optional<Account> findByUsername(String username) {
//         return acrepo.findByUsername(username);
//     }
// }

@Service
public class AccountService {

    @Autowired
    private AccountRepository acrepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AccountService(AccountRepository accountRepository) {
        this.acrepo = accountRepository;
    }

    @PostConstruct
    public void init() {
        createAdminUser();
    }

    public void createAdminUser() {
        String username = "admin";
        String password = "admin";
        String encodedPassword = passwordEncoder.encode(password);

        Account account = new Account(username, encodedPassword);
        acrepo.save(account);
    }

    // ... rest of your methods
}