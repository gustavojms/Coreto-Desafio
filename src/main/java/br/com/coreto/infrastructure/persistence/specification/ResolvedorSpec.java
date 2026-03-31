package br.com.coreto.infrastructure.persistence.specification;

import br.com.coreto.application.dto.filter.ResolvedorFilter;
import br.com.coreto.domain.entity.Resolvedor;
import br.com.coreto.domain.enums.AreaTematica;
import br.com.coreto.domain.enums.EstagioNegocio;
import br.com.coreto.domain.enums.TipoIniciativa;
import org.springframework.data.jpa.domain.Specification;

public final class ResolvedorSpec {

    private ResolvedorSpec() {}

    public static Specification<Resolvedor> withFilter(ResolvedorFilter f) {
        return Specification.where(notDeleted())
                .and(hasType(f.getTipoIniciativa()))
                .and(hasEstagio(f.getEstagioNegocio()))
                .and(hasTag(f.getTag()))
                .and(hasAreaTematica(f.getAreaTematica()))
                .and(searchText(f.getSearch()));
    }

    private static Specification<Resolvedor> notDeleted() {
        return (root, query, cb) -> cb.equal(root.get("deleted"), false);
    }

    private static Specification<Resolvedor> hasType(TipoIniciativa tipo) {
        if (tipo == null) return null;
        return (root, query, cb) -> cb.equal(root.get("tipoIniciativa"), tipo);
    }

    private static Specification<Resolvedor> hasEstagio(EstagioNegocio estagio) {
        if (estagio == null) return null;
        return (root, query, cb) -> cb.equal(root.get("estagioNegocio"), estagio);
    }

    private static Specification<Resolvedor> hasTag(String tag) {
        if (tag == null || tag.isBlank()) return null;
        return (root, query, cb) -> cb.isMember(tag.toLowerCase(), root.get("tags"));
    }

    private static Specification<Resolvedor> hasAreaTematica(AreaTematica area) {
        if (area == null) return null;
        return (root, query, cb) -> cb.isMember(area, root.get("areaTematica"));
    }

    private static Specification<Resolvedor> searchText(String search) {
        if (search == null || search.isBlank()) return null;
        return (root, query, cb) -> {
            String like = "%" + search.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("nome")), like),
                    cb.like(cb.lower(root.get("descricaoCurta")), like)
            );
        };
    }
}
