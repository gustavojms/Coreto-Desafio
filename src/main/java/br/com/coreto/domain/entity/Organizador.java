package br.com.coreto.domain.entity;

import br.com.coreto.domain.enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "organizadores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Organizador extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_organizacao", nullable = false, length = 50)
    private TipoOrganizacao tipoOrganizacao;

    @Column(nullable = false, length = 255)
    private String nome;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "descricao_curta")
    private String descricaoCurta;

    @Column(nullable = false, length = 18, unique = true)
    private String cnpj;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "organizador_usuarios_responsaveis",
            joinColumns = @JoinColumn(name = "organizador_id"))
    @Column(name = "usuario_id")
    private Set<UUID> usuariosResponsaveis = new HashSet<>();

    @Column(name = "logo_url", nullable = false)
    private String logoUrl;

    @Column(name = "banner_url", nullable = false)
    private String bannerUrl;

    @Column(name = "problema_atuacao", columnDefinition = "TEXT")
    private String problemaAtuacao;

    @Column(name = "produtos_servicos", columnDefinition = "TEXT")
    private String produtosServicos;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "organizador_publico_alvo",
            joinColumns = @JoinColumn(name = "organizador_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "publico_alvo", length = 50)
    private Set<PublicoAlvo> publicoAlvo = new HashSet<>();

    @Column(name = "territorio_pais")
    private String territorioPais;

    @Column(name = "territorio_estado")
    private String territorioEstado;

    @Column(name = "territorio_municipio")
    private String territorioMunicipio;

    @Column(name = "resultados_relevantes", columnDefinition = "TEXT")
    private String resultadosRelevantes;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "organizador_links_oficiais",
            joinColumns = @JoinColumn(name = "organizador_id"))
    @Column(name = "link")
    private List<String> linksOficiais = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "organizador_areas_tematicas",
            joinColumns = @JoinColumn(name = "organizador_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "area_tematica", length = 50)
    private Set<AreaTematica> areaTematica = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "estagio_inovacao", nullable = false, length = 20)
    private EstagioInovacao estagioInovacao;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "organizador_o_que_busca",
            joinColumns = @JoinColumn(name = "organizador_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "apoio_buscado", length = 50)
    private Set<ApoioBuscado> oQueBusca = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "parceiro_desejado", nullable = false, length = 50)
    private ParceiroDesejado parceiroDesejado;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "organizador_tags",
            joinColumns = @JoinColumn(name = "organizador_id"))
    @Column(name = "tag", length = 100)
    private Set<String> tags = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
