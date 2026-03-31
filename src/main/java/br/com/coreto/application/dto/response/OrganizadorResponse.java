package br.com.coreto.application.dto.response;

import br.com.coreto.domain.enums.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
public class OrganizadorResponse {
    private UUID id;
    private TipoOrganizacao tipoOrganizacao;
    private String nome;
    private String descricao;
    private String descricaoCurta;
    private String cnpj;
    private Set<UUID> usuariosResponsaveis;
    private String logoUrl;
    private String bannerUrl;
    private String problemaAtuacao;
    private String produtosServicos;
    private Set<PublicoAlvo> publicoAlvo;
    private String territorioPais;
    private String territorioEstado;
    private String territorioMunicipio;
    private String resultadosRelevantes;
    private List<String> linksOficiais;
    private Set<AreaTematica> areaTematica;
    private EstagioInovacao estagioInovacao;
    @JsonProperty("oQueBusca")
    private Set<ApoioBuscado> oQueBusca;
    private ParceiroDesejado parceiroDesejado;
    private Set<String> tags;
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
}
