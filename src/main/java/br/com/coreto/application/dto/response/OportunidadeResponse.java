package br.com.coreto.application.dto.response;

import br.com.coreto.domain.enums.*;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Data
public class OportunidadeResponse {
    private UUID id;
    private String titulo;
    private TipoOportunidade tipoOportunidade;
    private String resumo;
    private String descricaoDetalhada;
    private String linkExterno;
    private String imagemUrl;
    private List<Map<String, String>> anexos;
    private LocalDate dataAbertura;
    private LocalDate dataLimiteInscricao;
    private LocalDate dataInicioPrevista;
    private UUID organizadorId;
    private TipoOrganizacao tipoOrganizador;
    private boolean possuiParceria;
    private List<Map<String, String>> parceiros;
    private Set<AreaTematica> areasTematicas;
    private Set<ApoioOferecido> apoioOferecido;
    private Set<TipoIniciativa> tipoIniciativaElegivel;
    private EstagioInovacao estagioElegivel;
    private TRL trlElegivel;
    private StatusOportunidade status;
    private Set<String> tags;
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
}
