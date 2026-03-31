package br.com.coreto.web.controller;

import br.com.coreto.application.dto.request.LoginRequest;
import br.com.coreto.application.dto.request.RegisterRequest;
import br.com.coreto.application.dto.response.TokenResponse;
import br.com.coreto.application.service.AuthService;
import br.com.coreto.infrastructure.security.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@Import(SecurityConfig.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;

    @MockitoBean
    private JwtDecoder jwtDecoder;

    @Test
    void shouldRegisterUser() throws Exception {
        TokenResponse token = new TokenResponse("jwt-token", "ORGANIZADOR", "user-id");
        when(authService.register(any(RegisterRequest.class))).thenReturn(token);

        String body = """
                {"nome":"Teste","email":"teste@teste.com","senha":"123456","role":"ORGANIZADOR"}
                """;

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token").value("jwt-token"));
    }

    @Test
    void shouldReturnBadRequestForInvalidRegistration() throws Exception {
        String body = """
                {"nome":"","email":"invalid","senha":"12","role":"ORGANIZADOR"}
                """;

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldLogin() throws Exception {
        TokenResponse token = new TokenResponse("jwt-token", "ADMIN", "admin-id");
        when(authService.login(any(LoginRequest.class))).thenReturn(token);

        String body = """
                {"email":"admin@coreto.com.br","senha":"admin123"}
                """;

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("jwt-token"));
    }
}
