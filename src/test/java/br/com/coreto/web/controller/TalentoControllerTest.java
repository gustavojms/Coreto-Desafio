package br.com.coreto.web.controller;

import br.com.coreto.application.dto.response.TalentoResponse;
import br.com.coreto.application.service.TalentoService;
import br.com.coreto.infrastructure.persistence.repository.OrganizadorRepository;
import br.com.coreto.infrastructure.persistence.repository.OportunidadeRepository;
import br.com.coreto.infrastructure.persistence.repository.ResolvedorRepository;
import br.com.coreto.infrastructure.persistence.repository.TalentoRepository;
import br.com.coreto.infrastructure.security.OwnershipChecker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import br.com.coreto.infrastructure.security.SecurityConfig;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TalentoController.class)
@Import(SecurityConfig.class)
class TalentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TalentoService service;

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
    void shouldGetTalentoById() throws Exception {
        UUID id = UUID.randomUUID();
        TalentoResponse response = new TalentoResponse();
        response.setId(id);
        response.setNomeCompleto("Joao Silva");

        when(service.findById(id)).thenReturn(response);

        mockMvc.perform(get("/api/v1/talentos/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomeCompleto").value("Joao Silva"));
    }

    @Test
    void shouldReturn401WhenNotAuthenticated() throws Exception {
        mockMvc.perform(get("/api/v1/talentos"))
                .andExpect(status().isUnauthorized());
    }
}
