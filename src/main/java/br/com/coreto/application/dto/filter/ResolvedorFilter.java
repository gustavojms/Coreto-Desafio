package br.com.coreto.application.dto.filter;

import br.com.coreto.domain.enums.AreaTematica;
import br.com.coreto.domain.enums.EstagioNegocio;
import br.com.coreto.domain.enums.TipoIniciativa;
import lombok.Data;

@Data
public class ResolvedorFilter {
    private String search;
    private TipoIniciativa tipoIniciativa;
    private EstagioNegocio estagioNegocio;
    private String estado;
    private String tag;
    private AreaTematica areaTematica;
}
