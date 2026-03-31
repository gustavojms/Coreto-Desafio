package br.com.coreto.application.dto.response;

import br.com.coreto.domain.enums.*;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
public class ResolvedorResponse {
    private UUID id;
    private TipoIniciativa tipoIniciativa;
    private String nome;
    private boolean possuiVinculo;
    private String vinculoDetalhes;
    private String nomeResponsavel;
    private String papel;
    private String email;
    private String telefoneWhatsapp;
    private Dedicacao dedicacao;
    private String descricaoCurta;
    private String problema;
    private String solucao;
    private Set<PublicoAlvo> publicoAlvo;
    private Set<AreaTematica> areaTematica;
    private TRL trlGrupo;
    private EstagioNegocio estagioNegocio;
    private Set<ApoioBuscado> apoioBuscado;
    private ParceiroDesejado parceiroDesejado;
    private List<String> linksOficiais;
    private Set<String> tags;
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
}
