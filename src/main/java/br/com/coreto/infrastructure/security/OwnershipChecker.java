package br.com.coreto.infrastructure.security;

import br.com.coreto.domain.entity.*;
import br.com.coreto.infrastructure.persistence.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("ownershipChecker")
@RequiredArgsConstructor
public class OwnershipChecker {

    private final OrganizadorRepository organizadorRepository;
    private final ResolvedorRepository resolvedorRepository;
    private final TalentoRepository talentoRepository;
    private final OportunidadeRepository oportunidadeRepository;

    public boolean ownsOrganizador(UUID organizadorId, Authentication authentication) {
        String userId = extractUserId(authentication);
        return organizadorRepository.findByIdAndDeletedFalse(organizadorId)
                .map(o -> o.getUsuario() != null && o.getUsuario().getId().toString().equals(userId))
                .orElse(false);
    }

    public boolean ownsResolvedor(UUID resolvedorId, Authentication authentication) {
        String userId = extractUserId(authentication);
        return resolvedorRepository.findByIdAndDeletedFalse(resolvedorId)
                .map(r -> r.getUsuario() != null && r.getUsuario().getId().toString().equals(userId))
                .orElse(false);
    }

    public boolean ownsTalento(UUID talentoId, Authentication authentication) {
        String userId = extractUserId(authentication);
        return talentoRepository.findByIdAndDeletedFalse(talentoId)
                .map(t -> t.getUsuario() != null && t.getUsuario().getId().toString().equals(userId))
                .orElse(false);
    }

    public boolean ownsOportunidade(UUID oportunidadeId, Authentication authentication) {
        String userId = extractUserId(authentication);
        return oportunidadeRepository.findByIdAndDeletedFalse(oportunidadeId)
                .map(o -> o.getOrganizador() != null
                        && o.getOrganizador().getUsuario() != null
                        && o.getOrganizador().getUsuario().getId().toString().equals(userId))
                .orElse(false);
    }

    private String extractUserId(Authentication authentication) {
        if (authentication.getPrincipal() instanceof Jwt jwt) {
            return jwt.getSubject();
        }
        return "";
    }
}
