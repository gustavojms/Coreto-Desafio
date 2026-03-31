package br.com.coreto.web.controller;

import br.com.coreto.application.dto.response.OrganizadorResponse;
import br.com.coreto.application.service.OrganizadorService;
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

@WebMvcTest(OrganizadorController.class)
@Import(SecurityConfig.class)
class OrganizadorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrganizadorService service;

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
    void shouldGetOrganizadorById() throws Exception {
        UUID id = UUID.randomUUID();
        OrganizadorResponse response = new OrganizadorResponse();
        response.setId(id);
        response.setNome("Test Org");

        when(service.findById(id)).thenReturn(response);

        mockMvc.perform(get("/api/v1/organizadores/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Test Org"));
    }

    @Test
    void shouldReturn401WhenNotAuthenticated() throws Exception {
        mockMvc.perform(get("/api/v1/organizadores"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldDeleteOrganizador() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/api/v1/organizadores/{id}", id))
                .andExpect(status().isNoContent());
    }
}
