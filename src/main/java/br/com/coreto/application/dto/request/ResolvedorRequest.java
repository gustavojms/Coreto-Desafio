package br.com.coreto.application.dto.request;

import br.com.coreto.application.validation.MaxCollectionSize;
import br.com.coreto.application.validation.ValidVinculo;
import br.com.coreto.domain.enums.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@ValidVinculo
public class ResolvedorRequest {

    @NotNull(message = "Tipo de iniciativa e obrigatorio")
    private TipoIniciativa tipoIniciativa;

    @NotBlank(message = "Nome e obrigatorio")
    private String nome;

    @NotNull(message = "Possui vinculo e obrigatorio")
    private Boolean possuiVinculo;

    private String vinculoDetalhes;

    @NotBlank(message = "Nome do responsavel e obrigatorio")
    private String nomeResponsavel;

    @NotBlank(message = "Papel e obrigatorio")
    private String papel;

    @NotBlank(message = "Email e obrigatorio")
    @Email(message = "Email invalido")
    private String email;

    @NotBlank(message = "Telefone/WhatsApp e obrigatorio")
    private String telefoneWhatsapp;

    @NotNull(message = "Dedicacao e obrigatoria")
    private Dedicacao dedicacao;

    @NotBlank(message = "Descricao curta e obrigatoria")
    private String descricaoCurta;

    @NotBlank(message = "Problema e obrigatorio")
    private String problema;

    @NotBlank(message = "Solucao e obrigatoria")
    private String solucao;

    @MaxCollectionSize(value = 3, message = "Maximo de 3 publicos alvo")
    private Set<PublicoAlvo> publicoAlvo;

    @NotEmpty(message = "Area tematica e obrigatoria")
    @MaxCollectionSize(value = 3, message = "Maximo de 3 areas tematicas")
    private Set<AreaTematica> areaTematica;

    @NotNull(message = "TRL e obrigatorio")
    private TRL trlGrupo;

    @NotNull(message = "Estagio de negocio e obrigatorio")
    private EstagioNegocio estagioNegocio;

    @MaxCollectionSize(value = 3, message = "Maximo de 3 apoios buscados")
    private Set<ApoioBuscado> apoioBuscado;

    @NotNull(message = "Parceiro desejado e obrigatorio")
    private ParceiroDesejado parceiroDesejado;

    private List<String> linksOficiais;
}
