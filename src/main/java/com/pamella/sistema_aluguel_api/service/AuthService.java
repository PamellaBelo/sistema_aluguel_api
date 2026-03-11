package com.pamella.sistema_aluguel_api.service;

import com.pamella.sistema_aluguel_api.dto.AuthResponse;
import com.pamella.sistema_aluguel_api.dto.LoginRequest;
import com.pamella.sistema_aluguel_api.dto.RegisterRequest;
import com.pamella.sistema_aluguel_api.model.Usuario;
import com.pamella.sistema_aluguel_api.repository.UsuarioRepository;
import com.pamella.sistema_aluguel_api.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest request) {
        if (repository.findByEmail(request.email()).isPresent()) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        Usuario usuario = Usuario.builder()
                .nome(request.nome())
                .email(request.email())
                .senha(passwordEncoder.encode(request.senha()))
                .build();

        repository.save(usuario);

        String token = jwtService.gerarToken(usuario.getEmail());
        return new AuthResponse(usuario.getId(), usuario.getNome(), usuario.getEmail(), token);
    }

    public AuthResponse login(LoginRequest request) {
        Usuario usuario = repository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("Email ou senha inválidos"));

        if (!passwordEncoder.matches(request.senha(), usuario.getSenha())) {
            throw new IllegalArgumentException("Email ou senha inválidos");
        }

        String token = jwtService.gerarToken(usuario.getEmail());
        return new AuthResponse(usuario.getId(), usuario.getNome(), usuario.getEmail(), token);
    }
}