package br.com.coreto.application.dto.request;

import br.com.coreto.application.validation.CNPJ;
import br.com.coreto.application.validation.MaxCollectionSize;
import br.com.coreto.domain.enums.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
public class OrganizadorRequest {

    @NotNull(message = "Tipo de organizacao e obrigatorio")
    private TipoOrganizacao tipoOrganizacao;

    @NotBlank(message = "Nome e obrigatorio")
    private String nome;

    @NotBlank(message = "Descricao e obrigatoria")
    private String descricao;

    private String descricaoCurta;

    @NotBlank(message = "CNPJ e obrigatorio")
    @CNPJ
    private String cnpj;

    @NotEmpty(message = "Pelo menos um usuario responsavel e obrigatorio")
    private Set<UUID> usuariosResponsaveis;

    @NotBlank(message = "Logo URL e obrigatoria")
    private String logoUrl;

    @NotBlank(message = "Banner URL e obrigatorio")
    private String bannerUrl;

    private String problemaAtuacao;
    private String produtosServicos;

    @MaxCollectionSize(value = 3, message = "Maximo de 3 publicos alvo")
    private Set<PublicoAlvo> publicoAlvo;

    private String territorioPais;
    private String territorioEstado;
    private String territorioMunicipio;
    private String resultadosRelevantes;
    private List<String> linksOficiais;

    @NotEmpty(message = "Area tematica e obrigatoria")
    @MaxCollectionSize(value = 3, message = "Maximo de 3 areas tematicas")
    private Set<AreaTematica> areaTematica;

    @NotNull(message = "Estagio de inovacao e obrigatorio")
    private EstagioInovacao estagioInovacao;

    @NotEmpty(message = "O que busca e obrigatorio")
    @MaxCollectionSize(value = 3, message = "Maximo de 3 apoios buscados")
    @JsonProperty("oQueBusca")
    private Set<ApoioBuscado> oQueBusca;

    @NotNull(message = "Parceiro desejado e obrigatorio")
    private ParceiroDesejado parceiroDesejado;
}
