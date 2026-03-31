package br.com.coreto.infrastructure.persistence.specification;

import br.com.coreto.application.dto.filter.OrganizadorFilter;
import br.com.coreto.domain.entity.Organizador;
import br.com.coreto.domain.enums.AreaTematica;
import br.com.coreto.domain.enums.TipoOrganizacao;
import org.springframework.data.jpa.domain.Specification;

public final class OrganizadorSpec {

    private OrganizadorSpec() {}

    public static Specification<Organizador> withFilter(OrganizadorFilter f) {
        return Specification.where(notDeleted())
                .and(hasType(f.getTipoOrganizacao()))
                .and(inEstado(f.getEstado()))
                .and(hasTag(f.getTag()))
                .and(hasAreaTematica(f.getAreaTematica()))
                .and(searchText(f.getSearch()));
    }

    private static Specification<Organizador> notDeleted() {
        return (root, query, cb) -> cb.equal(root.get("deleted"), false);
    }

    private static Specification<Organizador> hasType(TipoOrganizacao tipo) {
        if (tipo == null) return null;
        return (root, query, cb) -> cb.equal(root.get("tipoOrganizacao"), tipo);
    }

    private static Specification<Organizador> inEstado(String estado) {
        if (estado == null || estado.isBlank()) return null;
        return (root, query, cb) -> cb.equal(cb.lower(root.get("territorioEstado")), estado.toLowerCase());
    }

    private static Specification<Organizador> hasTag(String tag) {
        if (tag == null || tag.isBlank()) return null;
        return (root, query, cb) -> cb.isMember(tag.toLowerCase(), root.get("tags"));
    }

    private static Specification<Organizador> hasAreaTematica(AreaTematica area) {
        if (area == null) return null;
        return (root, query, cb) -> cb.isMember(area, root.get("areaTematica"));
    }

    private static Specification<Organizador> searchText(String search) {
        if (search == null || search.isBlank()) return null;
        return (root, query, cb) -> {
            String like = "%" + search.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("nome")), like),
                    cb.like(cb.lower(root.get("descricao")), like)
            );
        };
    }
}
