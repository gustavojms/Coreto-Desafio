package br.com.coreto.application.mapper;

import br.com.coreto.application.dto.request.OportunidadeRequest;
import br.com.coreto.application.dto.response.OportunidadeResponse;
import br.com.coreto.domain.entity.Oportunidade;
import org.mapstruct.*;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OportunidadeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "organizador", ignore = true)
    @Mapping(target = "status", ignore = true)
    Oportunidade toEntity(OportunidadeRequest request);

    @Mapping(source = "organizador.id", target = "organizadorId")
    OportunidadeResponse toResponse(Oportunidade entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "organizador", ignore = true)
    @Mapping(target = "status", ignore = true)
    void updateEntity(OportunidadeRequest request, @MappingTarget Oportunidade entity);
}
