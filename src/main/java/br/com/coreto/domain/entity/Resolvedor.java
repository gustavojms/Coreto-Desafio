package br.com.coreto.domain.entity;

import br.com.coreto.domain.enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "resolvedores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Resolvedor extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_iniciativa", nullable = false, length = 50)
    private TipoIniciativa tipoIniciativa;

    @Column(nullable = false, length = 255)
    private String nome;

    @Column(name = "possui_vinculo", nullable = false)
    private boolean possuiVinculo;

    @Column(name = "vinculo_detalhes", length = 500)
    private String vinculoDetalhes;

    // Responsavel
    @Column(name = "nome_responsavel", nullable = false, length = 255)
    private String nomeResponsavel;

    @Column(nullable = false, length = 255)
    private String papel;

    @Column(nullable = false, length = 255)
    private String email;

    @Column(name = "telefone_whatsapp", nullable = false, length = 20)
    private String telefoneWhatsapp;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Dedicacao dedicacao;

    // Sobre
    @Column(name = "descricao_curta", nullable = false)
    private String descricaoCurta;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String problema;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String solucao;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "resolvedor_publico_alvo",
            joinColumns = @JoinColumn(name = "resolvedor_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "publico_alvo", length = 50)
    private Set<PublicoAlvo> publicoAlvo = new HashSet<>();

    // Classificacao
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "resolvedor_areas_tematicas",
            joinColumns = @JoinColumn(name = "resolvedor_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "area_tematica", length = 50)
    private Set<AreaTematica> areaTematica = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "trl_grupo", nullable = false, length = 10)
    private TRL trlGrupo;

    @Enumerated(EnumType.STRING)
    @Column(name = "estagio_negocio", nullable = false, length = 30)
    private EstagioNegocio estagioNegocio;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "resolvedor_apoio_buscado",
            joinColumns = @JoinColumn(name = "resolvedor_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "apoio_buscado", length = 50)
    private Set<ApoioBuscado> apoioBuscado = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "parceiro_desejado", nullable = false, length = 50)
    private ParceiroDesejado parceiroDesejado;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "resolvedor_links_oficiais",
            joinColumns = @JoinColumn(name = "resolvedor_id"))
    @Column(name = "link")
    private List<String> linksOficiais = new ArrayList<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "resolvedor_tags",
            joinColumns = @JoinColumn(name = "resolvedor_id"))
    @Column(name = "tag", length = 100)
    private Set<String> tags = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
