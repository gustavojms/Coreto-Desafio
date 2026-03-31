package br.com.coreto.application.dto.response;

import br.com.coreto.domain.enums.*;
import lombok.Data;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Data
public class TalentoResponse {
    private UUID id;
    private String nomeCompleto;
    private String nomeSocial;
    private String email;
    private String telefoneWhatsapp;
    private String miniBio;
    private String pais;
    private String estado;
    private String cidade;
    private String linkedin;
    private String github;
    private String portfolio;
    private Set<String> skills;
    private Senioridade senioridade;
    private Set<AreaTematica> areasTematicas;
    private Integer horasSemana;
    private TipoAtuacao tipoAtuacao;
    private Formato formato;
    private Set<String> tags;
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
}
