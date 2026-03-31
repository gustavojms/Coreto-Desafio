package br.com.coreto.web.controller;

import br.com.coreto.application.dto.response.ResolvedorResponse;
import br.com.coreto.application.service.ResolvedorService;
import br.com.coreto.infrastructure.persistence.repository.OrganizadorRepository;
import br.com.coreto.infrastructure.persistence.repository.OportunidadeRepository;
import br.com.coreto.infrastructure.persistence.repository.ResolvedorRepository;
import br.com.coreto.infrastructure.persistence.repository.TalentoRepository;
import br.com.coreto.infrastructure.security.OwnershipChecker;
import br.com.coreto.infrastructure.security.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ResolvedorController.class)
@Import(SecurityConfig.class)
class ResolvedorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ResolvedorService service;

    @MockitoBean
    private JwtDecoder jwtDecoder;

    @MockitoBean
    private OwnershipChecker ownershipChecker;

    @MockitoBean
    private OrganizadorRepository organizadorRepository;

    @MockitoBean
    private OportunidadeRepository oportunidadeRepository;

    @MockitoBean
    private ResolvedorRepository resolvedorRepository;

    @MockitoBean
    private TalentoRepository talentoRepository;

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldGetResolvedorById() throws Exception {
        UUID id = UUID.randomUUID();
        ResolvedorResponse response = new ResolvedorResponse();
        response.setId(id);
        response.setNome("HealthTech Recife");

        when(service.findById(id)).thenReturn(response);

        mockMvc.perform(get("/api/v1/resolvedores/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("HealthTech Recife"));
    }

    @Test
    void shouldReturn401WhenNotAuthenticated() throws Exception {
        mockMvc.perform(get("/api/v1/resolvedores"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldDeleteResolvedor() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/api/v1/resolvedores/{id}", id))
                .andExpect(status().isNoContent());
    }
}
