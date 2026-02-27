package com.pamella.sistema_aluguel_api.controller;

import com.pamella.sistema_aluguel_api.dto.LoginRequest;
import com.pamella.sistema_aluguel_api.dto.RegisterRequest;
import com.pamella.sistema_aluguel_api.model.Usuario;
import com.pamella.sistema_aluguel_api.service.AuthService;
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
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            Usuario usuario = service.registerAndReturnUser(request);
            String token = service.gerarToken(usuario); // gerar token para o usuário recém-criado
            return ResponseEntity.ok(Map.of(
                    "usuario", usuario,
                    "token", token
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request){
        try {
            String token = service.login(request);
            return ResponseEntity.ok(token);
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}