package br.com.coreto.domain.entity;

import br.com.coreto.domain.enums.*;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "oportunidades")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Oportunidade extends BaseEntity {

    @Column(nullable = false, length = 150)
    private String titulo;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_oportunidade", nullable = false, length = 30)
    private TipoOportunidade tipoOportunidade;

    @Column(nullable = false, length = 600)
    private String resumo;

    @Column(name = "descricao_detalhada", columnDefinition = "TEXT")
    private String descricaoDetalhada;

    @Column(name = "link_externo")
    private String linkExterno;

    @Column(name = "imagem_url")
    private String imagemUrl;

    @Type(JsonType.class)
    @Column(name = "anexos", columnDefinition = "jsonb")
    private List<Map<String, String>> anexos = new ArrayList<>();

    @Column(name = "data_abertura")
    private LocalDate dataAbertura;

    @Column(name = "data_limite_inscricao", nullable = false)
    private LocalDate dataLimiteInscricao;

    @Column(name = "data_inicio_prevista")
    private LocalDate dataInicioPrevista;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizador_id", nullable = false)
    private Organizador organizador;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_organizador", nullable = false, length = 50)
    private TipoOrganizacao tipoOrganizador;

    @Column(name = "possui_parceria", nullable = false)
    private boolean possuiParceria;

    @Type(JsonType.class)
    @Column(name = "parceiros", columnDefinition = "jsonb")
    private List<Map<String, String>> parceiros = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "oportunidade_areas_tematicas",
            joinColumns = @JoinColumn(name = "oportunidade_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "area_tematica", length = 50)
    private Set<AreaTematica> areasTematicas = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "oportunidade_apoio_oferecido",
            joinColumns = @JoinColumn(name = "oportunidade_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "apoio_oferecido", length = 50)
    private Set<ApoioOferecido> apoioOferecido = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "oportunidade_tipo_iniciativa_elegivel",
            joinColumns = @JoinColumn(name = "oportunidade_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_iniciativa", length = 50)
    private Set<TipoIniciativa> tipoIniciativaElegivel = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "estagio_elegivel", length = 20)
    private EstagioInovacao estagioElegivel;

    @Enumerated(EnumType.STRING)
    @Column(name = "trl_elegivel", length = 10)
    private TRL trlElegivel;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusOportunidade status = StatusOportunidade.RASCUNHO;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "oportunidade_tags",
            joinColumns = @JoinColumn(name = "oportunidade_id"))
    @Column(name = "tag", length = 100)
    private Set<String> tags = new HashSet<>();
}
