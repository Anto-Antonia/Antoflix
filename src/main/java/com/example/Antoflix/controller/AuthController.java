package com.example.Antoflix.controller;

import com.example.Antoflix.dto.request.user.AddUserRequest;
import com.example.Antoflix.dto.request.user.RegisterRequest;
import com.example.Antoflix.dto.request.user.SignInRequest;
import com.example.Antoflix.dto.response.user.RegisterResponse;
import com.example.Antoflix.dto.response.user.SignInResponse;
import com.example.Antoflix.service.security.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/api/signIn")
    public ResponseEntity<SignInResponse> signIn(@RequestBody @Valid SignInRequest signInRequest){
        SignInResponse signInResponse = authService.signIn(signInRequest);
        return ResponseEntity.ok().body(signInResponse);
    }

    @PostMapping("/api/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody @Valid RegisterRequest registerRequest){
        RegisterResponse registerResponse = authService.registerUser(registerRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}