package com.tokoteratai.adminweb;

import org.springframework.stereotype.Component;

@Component
public class WelcomeMessage {

    public String getWelcomeMessage () {
        return "Welcome to the Toko Teratai Website";
    }
}
