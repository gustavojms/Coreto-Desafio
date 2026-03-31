package br.com.coreto.application.dto.request;

import br.com.coreto.application.validation.MaxCollectionSize;
import br.com.coreto.domain.enums.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Set;

@Data
public class TalentoRequest {

    @NotBlank(message = "Nome completo e obrigatorio")
    private String nomeCompleto;

    private String nomeSocial;

    @NotBlank(message = "Email e obrigatorio")
    @Email(message = "Email invalido")
    private String email;

    @NotBlank(message = "Telefone/WhatsApp e obrigatorio")
    private String telefoneWhatsapp;

    @NotBlank(message = "Mini bio e obrigatoria")
    @Size(max = 500, message = "Mini bio deve ter no maximo 500 caracteres")
    private String miniBio;

    @NotBlank(message = "Pais e obrigatorio")
    private String pais;

    @NotBlank(message = "Estado e obrigatorio")
    private String estado;

    @NotBlank(message = "Cidade e obrigatoria")
    private String cidade;

    private String linkedin;
    private String github;
    private String portfolio;

    @NotEmpty(message = "Skills sao obrigatorias")
    private Set<String> skills;

    @NotNull(message = "Senioridade e obrigatoria")
    private Senioridade senioridade;

    @NotEmpty(message = "Areas tematicas sao obrigatorias")
    @MaxCollectionSize(value = 3, message = "Maximo de 3 areas tematicas")
    private Set<AreaTematica> areasTematicas;

    @NotNull(message = "Horas por semana e obrigatorio")
    @Min(value = 1, message = "Minimo de 1 hora por semana")
    @Max(value = 168, message = "Maximo de 168 horas por semana")
    private Integer horasSemana;

    @NotNull(message = "Tipo de atuacao e obrigatorio")
    private TipoAtuacao tipoAtuacao;

    @NotNull(message = "Formato e obrigatorio")
    private Formato formato;
}
