package com.pamella.sistema_aluguel_api.service;

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

    public Usuario registerAndReturnUser(RegisterRequest request) {
        if (repository.findByEmail(request.email()).isPresent()) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        Usuario usuario = Usuario.builder()
                .nome(request.nome())
                .email(request.email())
                .senha(passwordEncoder.encode(request.senha()))
                .build();

        repository.save(usuario);
        return usuario;
    }

    public String login(LoginRequest request) {
        Usuario usuario = repository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        if (passwordEncoder.matches(request.senha(), usuario.getSenha())) {
            return jwtService.gerarToken(usuario.getEmail());
        }

        throw new RuntimeException("Senha inválida");
    }

    public String gerarToken(Usuario usuario) {
        return jwtService.gerarToken(usuario.getEmail());
    }
}