package com.tokoteratai.project111.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tokoteratai.project111.model.Account;
import com.tokoteratai.project111.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfiguration {

    // Other methods...

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private AccountRepository acrepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void createAdminUser() {
        String username = "admin";
        String password = "admin";
        String encodedPassword = passwordEncoder.encode(password);

        Account account = new Account(username, encodedPassword);
        acrepo.save(account);
    }
}