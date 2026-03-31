package br.com.coreto.application.service;

import br.com.coreto.application.dto.request.LoginRequest;
import br.com.coreto.application.dto.request.RegisterRequest;
import br.com.coreto.application.dto.response.TokenResponse;
import br.com.coreto.domain.entity.Usuario;
import br.com.coreto.infrastructure.persistence.repository.UsuarioRepository;
import br.com.coreto.infrastructure.security.JwtTokenService;
import br.com.coreto.web.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;

    @Transactional
    public TokenResponse register(RegisterRequest request) {
        log.info("action=register_attempt email={} role={}", request.getEmail(), request.getRole());

        if (usuarioRepository.existsByEmail(request.getEmail())) {
            log.warn("action=register_failed reason=email_already_exists email={}", request.getEmail());
            throw new BusinessException("Email ja cadastrado");
        }

        Usuario usuario = Usuario.builder()
                .nome(request.getNome())
                .email(request.getEmail())
                .senhaHash(passwordEncoder.encode(request.getSenha()))
                .role(request.getRole())
                .build();

        usuario = usuarioRepository.save(usuario);
        log.info("action=register_success userId={} email={} role={}", usuario.getId(), usuario.getEmail(), usuario.getRole());

        String token = jwtTokenService.generateToken(usuario);
        return new TokenResponse(token, usuario.getRole().name(), usuario.getId().toString());
    }

    public TokenResponse login(LoginRequest request) {
        log.info("action=login_attempt email={}", request.getEmail());

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    log.warn("action=login_failed reason=user_not_found email={}", request.getEmail());
                    return new BusinessException("Credenciais invalidas");
                });

        if (!passwordEncoder.matches(request.getSenha(), usuario.getSenhaHash())) {
            log.warn("action=login_failed reason=invalid_password email={} userId={}", request.getEmail(), usuario.getId());
            throw new BusinessException("Credenciais invalidas");
        }

        if (!usuario.isAtivo()) {
            log.warn("action=login_failed reason=user_inactive email={} userId={}", request.getEmail(), usuario.getId());
            throw new BusinessException("Usuario desativado");
        }

        log.info("action=login_success userId={} email={} role={}", usuario.getId(), usuario.getEmail(), usuario.getRole());
        String token = jwtTokenService.generateToken(usuario);
        return new TokenResponse(token, usuario.getRole().name(), usuario.getId().toString());
    }
}
