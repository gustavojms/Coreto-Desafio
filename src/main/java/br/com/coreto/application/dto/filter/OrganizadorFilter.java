package br.com.coreto.application.dto.filter;

import br.com.coreto.domain.enums.AreaTematica;
import br.com.coreto.domain.enums.TipoOrganizacao;
import lombok.Data;

@Data
public class OrganizadorFilter {
    private String search;
    private TipoOrganizacao tipoOrganizacao;
    private String estado;
    private String tag;
    private AreaTematica areaTematica;
}
