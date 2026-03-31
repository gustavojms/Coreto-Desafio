package br.com.coreto.application.dto.request;

import br.com.coreto.application.validation.MaxCollectionSize;
import br.com.coreto.application.validation.ValidDateOrder;
import br.com.coreto.application.validation.ValidParceria;
import br.com.coreto.domain.enums.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Data
@ValidDateOrder
@ValidParceria
public class OportunidadeRequest {

    @NotBlank(message = "Titulo e obrigatorio")
    @Size(max = 150, message = "Titulo deve ter no maximo 150 caracteres")
    private String titulo;

    @NotNull(message = "Tipo de oportunidade e obrigatorio")
    private TipoOportunidade tipoOportunidade;

    @NotBlank(message = "Resumo e obrigatorio")
    @Size(max = 600, message = "Resumo deve ter no maximo 600 caracteres")
    private String resumo;

    private String descricaoDetalhada;
    private String linkExterno;
    private String imagemUrl;
    private List<Map<String, String>> anexos;

    private LocalDate dataAbertura;

    @NotNull(message = "Data limite de inscricao e obrigatoria")
    private LocalDate dataLimiteInscricao;

    private LocalDate dataInicioPrevista;

    @NotNull(message = "Organizador e obrigatorio")
    private UUID organizadorId;

    @NotNull(message = "Tipo de organizador e obrigatorio")
    private TipoOrganizacao tipoOrganizador;

    @NotNull(message = "Possui parceria e obrigatorio")
    private Boolean possuiParceria;

    private List<Map<String, String>> parceiros;

    @NotEmpty(message = "Areas tematicas sao obrigatorias")
    @MaxCollectionSize(value = 3, message = "Maximo de 3 areas tematicas")
    private Set<AreaTematica> areasTematicas;

    @NotEmpty(message = "Apoio oferecido e obrigatorio")
    @MaxCollectionSize(value = 3, message = "Maximo de 3 apoios oferecidos")
    private Set<ApoioOferecido> apoioOferecido;

    private Set<TipoIniciativa> tipoIniciativaElegivel;
    private EstagioInovacao estagioElegivel;
    private TRL trlElegivel;
}
