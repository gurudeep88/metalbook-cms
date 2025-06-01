package com.metalbook.cms.controller;

import com.metalbook.cms.dto.request.LoginRequest;
import com.metalbook.cms.dto.request.RegisterRequest;
import com.metalbook.cms.dto.response.JwtResponse;
import com.metalbook.cms.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request){
        System.out.println("register req "+request.toString());
        authService.register(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest request){
        JwtResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

}
