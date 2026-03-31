package br.com.coreto.infrastructure.persistence.specification;

import br.com.coreto.application.dto.filter.TalentoFilter;
import br.com.coreto.domain.entity.Talento;
import br.com.coreto.domain.enums.*;
import org.springframework.data.jpa.domain.Specification;

public final class TalentoSpec {

    private TalentoSpec() {}

    public static Specification<Talento> withFilter(TalentoFilter f) {
        return Specification.where(notDeleted())
                .and(hasSenioridade(f.getSenioridade()))
                .and(hasTipoAtuacao(f.getTipoAtuacao()))
                .and(hasFormato(f.getFormato()))
                .and(inEstado(f.getEstado()))
                .and(hasTag(f.getTag()))
                .and(hasAreaTematica(f.getAreaTematica()))
                .and(searchText(f.getSearch()));
    }

    private static Specification<Talento> notDeleted() {
        return (root, query, cb) -> cb.equal(root.get("deleted"), false);
    }

    private static Specification<Talento> hasSenioridade(Senioridade senioridade) {
        if (senioridade == null) return null;
        return (root, query, cb) -> cb.equal(root.get("senioridade"), senioridade);
    }

    private static Specification<Talento> hasTipoAtuacao(TipoAtuacao tipo) {
        if (tipo == null) return null;
        return (root, query, cb) -> cb.equal(root.get("tipoAtuacao"), tipo);
    }

    private static Specification<Talento> hasFormato(Formato formato) {
        if (formato == null) return null;
        return (root, query, cb) -> cb.equal(root.get("formato"), formato);
    }

    private static Specification<Talento> inEstado(String estado) {
        if (estado == null || estado.isBlank()) return null;
        return (root, query, cb) -> cb.equal(cb.lower(root.get("estado")), estado.toLowerCase());
    }

    private static Specification<Talento> hasTag(String tag) {
        if (tag == null || tag.isBlank()) return null;
        return (root, query, cb) -> cb.isMember(tag.toLowerCase(), root.get("tags"));
    }

    private static Specification<Talento> hasAreaTematica(AreaTematica area) {
        if (area == null) return null;
        return (root, query, cb) -> cb.isMember(area, root.get("areasTematicas"));
    }

    private static Specification<Talento> searchText(String search) {
        if (search == null || search.isBlank()) return null;
        return (root, query, cb) -> {
            String like = "%" + search.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("nomeCompleto")), like),
                    cb.like(cb.lower(root.get("miniBio")), like)
            );
        };
    }
}
