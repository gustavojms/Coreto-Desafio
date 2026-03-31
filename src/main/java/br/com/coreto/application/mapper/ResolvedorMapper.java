package br.com.coreto.application.mapper;

import br.com.coreto.application.dto.request.ResolvedorRequest;
import br.com.coreto.application.dto.response.ResolvedorResponse;
import br.com.coreto.domain.entity.Resolvedor;
import org.mapstruct.*;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ResolvedorMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    Resolvedor toEntity(ResolvedorRequest request);

    ResolvedorResponse toResponse(Resolvedor entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    void updateEntity(ResolvedorRequest request, @MappingTarget Resolvedor entity);
}
