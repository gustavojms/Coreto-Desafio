package br.com.coreto.application.dto.filter;

import br.com.coreto.domain.enums.AreaTematica;
import br.com.coreto.domain.enums.StatusOportunidade;
import br.com.coreto.domain.enums.TipoOportunidade;
import lombok.Data;

@Data
public class OportunidadeFilter {
    private String search;
    private TipoOportunidade tipo;
    private StatusOportunidade status;
    private String estado;
    private String tag;
    private AreaTematica areaTematica;
}
