package com.pamella.sistema_aluguel_api.controller;

import com.pamella.sistema_aluguel_api.dto.AuthResponse;
import com.pamella.sistema_aluguel_api.dto.LoginRequest;
import com.pamella.sistema_aluguel_api.dto.RegisterRequest;
import com.pamella.sistema_aluguel_api.model.Usuario;
import com.pamella.sistema_aluguel_api.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(service.login(request));
    }
}