package com.tag.backend.controllers;

import com.tag.backend.model.TokenRequest;
import com.tag.backend.services.TokenBlacklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody TokenRequest tokenRequest) {
        String token = tokenRequest.getToken();
        tokenBlacklistService.blacklistToken(token);
        return ResponseEntity.ok().build();
    }
}
