package com.tokoteratai.project111.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tokoteratai.project111.model.Account;
import com.tokoteratai.project111.repository.AccountRepository;
import com.tokoteratai.project111.service.UserDetailsServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;

// @Configuration
// @EnableWebSecurity
// public class SecurityConfig extends WebSecurityConfiguration {

//     @Autowired
//     public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//         auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//     }


//     @Autowired
//     private UserDetailsServiceImpl userDetailsService;

//     @Bean
//     public UserDetailsService userDetailsService() {
//         return userDetailsService;
//     }

//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new PasswordEncoder() {
//             @Override
//             public String encode(CharSequence rawPassword) {
//                 return rawPassword.toString();
//             }

//             @Override
//             public boolean matches(CharSequence rawPassword, String encodedPassword) {
//                 return rawPassword.toString().equals(encodedPassword);
//             }
//         };
//     }
// }

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