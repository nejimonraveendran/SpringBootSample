package com.neji.hellospring.models.services.implementations;

import com.neji.hellospring.models.services.interfaces.AuthServiceInterface;

import org.springframework.stereotype.Component;

@Component
public class AuthService implements AuthServiceInterface {

    @Override
    public boolean validateUser(String username, String password) {
        return false;
    }
    
}
