package br.com.coreto.application.service;

import br.com.coreto.domain.entity.Oportunidade;
import br.com.coreto.domain.entity.Organizador;
import br.com.coreto.domain.entity.Resolvedor;
import br.com.coreto.domain.entity.Talento;
import br.com.coreto.domain.enums.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class TagServiceTest {

    private TagService tagService;

    @BeforeEach
    void setUp() {
        tagService = new TagService();
    }

    @Test
    void shouldGenerateOportunidadeTags() {
        Oportunidade oportunidade = new Oportunidade();
        oportunidade.setTipoOportunidade(TipoOportunidade.EDITAL);
        oportunidade.setAreasTematicas(Set.of(AreaTematica.SAUDE, AreaTematica.EDUCACAO));
        oportunidade.setApoioOferecido(Set.of(ApoioOferecido.FINANCEIRO));

        Set<String> tags = tagService.generateTags(oportunidade);

        assertThat(tags).contains("edital", "saude", "educacao", "financeiro");
    }

    @Test
    void shouldGenerateOrganizadorTags() {
        Organizador organizador = new Organizador();
        organizador.setTipoOrganizacao(TipoOrganizacao.UNIVERSIDADE);
        organizador.setAreaTematica(Set.of(AreaTematica.TECNOLOGIA));
        organizador.setEstagioInovacao(EstagioInovacao.ESTAGIO_3);
        organizador.setOQueBusca(Set.of(ApoioBuscado.MENTORIA));

        Set<String> tags = tagService.generateTags(organizador);

        assertThat(tags).contains("universidade", "tecnologia", "estagio_3", "mentoria");
    }

    @Test
    void shouldGenerateResolvedorTags() {
        Resolvedor resolvedor = new Resolvedor();
        resolvedor.setTipoIniciativa(TipoIniciativa.STARTUP);
        resolvedor.setAreaTematica(Set.of(AreaTematica.SAUDE));
        resolvedor.setTrlGrupo(TRL.TRL_5);
        resolvedor.setEstagioNegocio(EstagioNegocio.VALIDACAO);
        resolvedor.setApoioBuscado(Set.of(ApoioBuscado.FINANCEIRO));

        Set<String> tags = tagService.generateTags(resolvedor);

        assertThat(tags).contains("startup", "saude", "trl_5", "validacao", "financeiro");
    }

    @Test
    void shouldGenerateTalentoTags() {
        Talento talento = new Talento();
        talento.setSkills(Set.of("Java", "Spring"));
        talento.setSenioridade(Senioridade.SENIOR);
        talento.setAreasTematicas(Set.of(AreaTematica.TECNOLOGIA));
        talento.setTipoAtuacao(TipoAtuacao.PJ);

        Set<String> tags = tagService.generateTags(talento);

        assertThat(tags).contains("java", "spring", "senior", "tecnologia", "pj");
    }

    @Test
    void shouldHandleNullCollections() {
        Oportunidade oportunidade = new Oportunidade();
        oportunidade.setTipoOportunidade(TipoOportunidade.DESAFIO);

        Set<String> tags = tagService.generateTags(oportunidade);

        assertThat(tags).contains("desafio");
    }
}
