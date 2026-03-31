package br.com.coreto.application.mapper;

import br.com.coreto.application.dto.request.TalentoRequest;
import br.com.coreto.application.dto.response.TalentoResponse;
import br.com.coreto.domain.entity.Talento;
import org.mapstruct.*;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TalentoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    Talento toEntity(TalentoRequest request);

    TalentoResponse toResponse(Talento entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    void updateEntity(TalentoRequest request, @MappingTarget Talento entity);
}
