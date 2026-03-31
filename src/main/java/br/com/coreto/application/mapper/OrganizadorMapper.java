package br.com.coreto.application.mapper;

import br.com.coreto.application.dto.request.OrganizadorRequest;
import br.com.coreto.application.dto.response.OrganizadorResponse;
import br.com.coreto.domain.entity.Organizador;
import org.mapstruct.*;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OrganizadorMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    Organizador toEntity(OrganizadorRequest request);

    OrganizadorResponse toResponse(Organizador entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    void updateEntity(OrganizadorRequest request, @MappingTarget Organizador entity);
}
