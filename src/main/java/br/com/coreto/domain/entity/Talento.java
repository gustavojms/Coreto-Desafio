package br.com.coreto.domain.entity;

import br.com.coreto.domain.enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "talentos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Talento extends BaseEntity {

    @Column(name = "nome_completo", nullable = false, length = 255)
    private String nomeCompleto;

    @Column(name = "nome_social", length = 255)
    private String nomeSocial;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "telefone_whatsapp", nullable = false, length = 20)
    private String telefoneWhatsapp;

    // Perfil
    @Column(name = "mini_bio", nullable = false, length = 500)
    private String miniBio;

    @Column(nullable = false, length = 100)
    private String pais;

    @Column(nullable = false, length = 100)
    private String estado;

    @Column(nullable = false, length = 255)
    private String cidade;

    @Column(length = 500)
    private String linkedin;

    @Column(length = 500)
    private String github;

    @Column(length = 500)
    private String portfolio;

    // Competencias
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "talento_skills",
            joinColumns = @JoinColumn(name = "talento_id"))
    @Column(name = "skill")
    private Set<String> skills = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Senioridade senioridade;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "talento_areas_tematicas",
            joinColumns = @JoinColumn(name = "talento_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "area_tematica", length = 50)
    private Set<AreaTematica> areasTematicas = new HashSet<>();

    // Disponibilidade
    @Column(name = "horas_semana", nullable = false)
    private Integer horasSemana;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_atuacao", nullable = false, length = 20)
    private TipoAtuacao tipoAtuacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Formato formato;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "talento_tags",
            joinColumns = @JoinColumn(name = "talento_id"))
    @Column(name = "tag", length = 100)
    private Set<String> tags = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
