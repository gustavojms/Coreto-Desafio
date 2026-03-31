package br.com.coreto.application.service;

import br.com.coreto.domain.entity.*;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TagService {

    public Set<String> generateTags(Oportunidade entity) {
        Set<String> tags = new LinkedHashSet<>();
        addEnum(tags, entity.getTipoOportunidade());
        addEnums(tags, entity.getAreasTematicas());
        addEnums(tags, entity.getApoioOferecido());
        return normalize(tags);
    }

    // Spec tags: tipo_iniciativa (mapped to tipoOrganizacao), area_tematica, estagio_inovacao, apoio_buscado
    public Set<String> generateTags(Organizador entity) {
        Set<String> tags = new LinkedHashSet<>();
        addEnum(tags, entity.getTipoOrganizacao());
        addEnums(tags, entity.getAreaTematica());
        addEnum(tags, entity.getEstagioInovacao());
        addEnums(tags, entity.getOQueBusca());
        return normalize(tags);
    }

    // Spec tags: tipo_iniciativa, area_tematica, trl_grupo, estagio_inovacao (mapped to estagioNegocio), apoio_buscado
    public Set<String> generateTags(Resolvedor entity) {
        Set<String> tags = new LinkedHashSet<>();
        addEnum(tags, entity.getTipoIniciativa());
        addEnums(tags, entity.getAreaTematica());
        addEnum(tags, entity.getTrlGrupo());
        addEnum(tags, entity.getEstagioNegocio());
        addEnums(tags, entity.getApoioBuscado());
        return normalize(tags);
    }

    public Set<String> generateTags(Talento entity) {
        Set<String> tags = new LinkedHashSet<>();
        if (entity.getSkills() != null) {
            tags.addAll(entity.getSkills());
        }
        addEnum(tags, entity.getSenioridade());
        addEnums(tags, entity.getAreasTematicas());
        addEnum(tags, entity.getTipoAtuacao());
        return normalize(tags);
    }

    private void addEnum(Set<String> tags, Enum<?> value) {
        if (value != null) {
            tags.add(value.name());
        }
    }

    private <E extends Enum<E>> void addEnums(Set<String> tags, Collection<E> values) {
        if (values != null) {
            values.forEach(v -> tags.add(v.name()));
        }
    }

    private Set<String> normalize(Set<String> tags) {
        return tags.stream()
                .map(tag -> tag.toLowerCase().replaceAll("[^a-z0-9_\\-]", "").trim())
                .filter(tag -> !tag.isEmpty())
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
