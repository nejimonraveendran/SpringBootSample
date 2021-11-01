package com.neji.hellospring.controllers;

import com.neji.hellospring.models.dto.request.TokenRequest;
import com.neji.hellospring.models.dto.response.TokenResponse;
import com.neji.hellospring.models.services.interfaces.AuthServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    
    private @Autowired AuthServiceInterface authService;

    @PostMapping("/token")
    public TokenResponse getToken(@RequestBody TokenRequest request){
        var response = new TokenResponse();
        var success = authService.validateUser(request.username, request.password);

        if(success){
            response.message = "authenticated";
        }else{
            response.message = "not authenticated";
        }

        return response;
    }


}
