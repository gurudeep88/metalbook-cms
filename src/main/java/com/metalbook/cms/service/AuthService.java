package com.metalbook.cms.service;

import com.metalbook.cms.dto.request.LoginRequest;
import com.metalbook.cms.dto.request.RegisterRequest;
import com.metalbook.cms.dto.response.JwtResponse;

public interface AuthService {
    void register(RegisterRequest request);
    JwtResponse login(LoginRequest request);
}
