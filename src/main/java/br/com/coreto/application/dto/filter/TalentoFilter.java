package br.com.coreto.application.dto.filter;

import br.com.coreto.domain.enums.AreaTematica;
import br.com.coreto.domain.enums.Formato;
import br.com.coreto.domain.enums.Senioridade;
import br.com.coreto.domain.enums.TipoAtuacao;
import lombok.Data;

@Data
public class TalentoFilter {
    private String search;
    private Senioridade senioridade;
    private TipoAtuacao tipoAtuacao;
    private Formato formato;
    private String estado;
    private String tag;
    private AreaTematica areaTematica;
}
