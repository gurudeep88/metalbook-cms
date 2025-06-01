package com.metalbook.cms.service.Impl;

import com.metalbook.cms.dto.request.LoginRequest;
import com.metalbook.cms.dto.request.RegisterRequest;
import com.metalbook.cms.dto.response.JwtResponse;
import com.metalbook.cms.exception.UserAlreadyExistsException;
import com.metalbook.cms.model.User;
import com.metalbook.cms.repository.UserRepository;
import com.metalbook.cms.security.jwt.JwtService;
import com.metalbook.cms.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository,
                           AuthenticationManager authenticationManager,
                           JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void register(RegisterRequest request){
        if(userRepository.existsByUsername(request.getUsername())){
            throw new UserAlreadyExistsException("Username is already taken");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
    }

    @Override
    public JwtResponse login(LoginRequest request){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        String jwt = jwtService
                .generateTokenFromUserDetails((UserDetails) authentication.getPrincipal());
        return new JwtResponse(jwt);
    }
}
