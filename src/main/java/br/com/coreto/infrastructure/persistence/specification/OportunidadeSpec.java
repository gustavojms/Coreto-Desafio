package br.com.coreto.infrastructure.persistence.specification;

import br.com.coreto.application.dto.filter.OportunidadeFilter;
import br.com.coreto.domain.entity.Oportunidade;
import br.com.coreto.domain.enums.AreaTematica;
import br.com.coreto.domain.enums.StatusOportunidade;
import br.com.coreto.domain.enums.TipoOportunidade;
import org.springframework.data.jpa.domain.Specification;

public final class OportunidadeSpec {

    private OportunidadeSpec() {}

    public static Specification<Oportunidade> withFilter(OportunidadeFilter f) {
        return Specification.where(notDeleted())
                .and(hasType(f.getTipo()))
                .and(hasStatus(f.getStatus()))
                .and(inEstado(f.getEstado()))
                .and(hasTag(f.getTag()))
                .and(hasAreaTematica(f.getAreaTematica()))
                .and(searchText(f.getSearch()));
    }

    private static Specification<Oportunidade> notDeleted() {
        return (root, query, cb) -> cb.equal(root.get("deleted"), false);
    }

    private static Specification<Oportunidade> hasType(TipoOportunidade tipo) {
        if (tipo == null) return null;
        return (root, query, cb) -> cb.equal(root.get("tipoOportunidade"), tipo);
    }

    private static Specification<Oportunidade> hasStatus(StatusOportunidade status) {
        if (status == null) return null;
        return (root, query, cb) -> cb.equal(root.get("status"), status);
    }

    private static Specification<Oportunidade> hasTag(String tag) {
        if (tag == null || tag.isBlank()) return null;
        return (root, query, cb) -> cb.isMember(tag.toLowerCase(), root.get("tags"));
    }

    private static Specification<Oportunidade> hasAreaTematica(AreaTematica area) {
        if (area == null) return null;
        return (root, query, cb) -> cb.isMember(area, root.get("areasTematicas"));
    }

    private static Specification<Oportunidade> inEstado(String estado) {
        if (estado == null || estado.isBlank()) return null;
        return (root, query, cb) -> cb.equal(
                cb.lower(root.get("organizador").get("territorioEstado")), estado.toLowerCase());
    }

    private static Specification<Oportunidade> searchText(String search) {
        if (search == null || search.isBlank()) return null;
        return (root, query, cb) -> {
            String like = "%" + search.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("titulo")), like),
                    cb.like(cb.lower(root.get("resumo")), like)
            );
        };
    }
}
