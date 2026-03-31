package br.com.coreto.application.service;

import br.com.coreto.application.dto.request.OrganizadorRequest;
import br.com.coreto.application.dto.response.OrganizadorResponse;
import br.com.coreto.application.mapper.OrganizadorMapper;
import br.com.coreto.domain.entity.Organizador;
import br.com.coreto.domain.enums.*;
import br.com.coreto.infrastructure.persistence.repository.OrganizadorRepository;
import br.com.coreto.infrastructure.persistence.repository.UsuarioRepository;
import br.com.coreto.web.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrganizadorServiceTest {

    @Mock
    private OrganizadorRepository repository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private OrganizadorMapper mapper;

    @Mock
    private TagService tagService;

    @InjectMocks
    private OrganizadorService service;

    @Test
    void shouldCreateOrganizador() {
        OrganizadorRequest request = new OrganizadorRequest();
        Organizador entity = new Organizador();
        entity.setId(UUID.randomUUID());
        OrganizadorResponse response = new OrganizadorResponse();

        when(mapper.toEntity(request)).thenReturn(entity);
        when(tagService.generateTags(entity)).thenReturn(Set.of("tag1"));
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(response);

        OrganizadorResponse result = service.create(request, null);

        assertThat(result).isEqualTo(response);
        verify(repository).save(entity);
        verify(tagService).generateTags(entity);
    }

    @Test
    void shouldFindOrganizadorById() {
        UUID id = UUID.randomUUID();
        Organizador entity = new Organizador();
        OrganizadorResponse response = new OrganizadorResponse();

        when(repository.findByIdAndDeletedFalse(id)).thenReturn(Optional.of(entity));
        when(mapper.toResponse(entity)).thenReturn(response);

        OrganizadorResponse result = service.findById(id);

        assertThat(result).isEqualTo(response);
    }

    @Test
    void shouldThrowNotFoundForMissingOrganizador() {
        UUID id = UUID.randomUUID();
        when(repository.findByIdAndDeletedFalse(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(id))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void shouldSoftDeleteOrganizador() {
        UUID id = UUID.randomUUID();
        Organizador entity = new Organizador();
        entity.setDeleted(false);

        when(repository.findByIdAndDeletedFalse(id)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(entity);

        service.softDelete(id);

        assertThat(entity.isDeleted()).isTrue();
        assertThat(entity.getDeletedAt()).isNotNull();
        verify(repository).save(entity);
    }

    @Test
    void shouldUpdateOrganizadorAndRecalculateTags() {
        UUID id = UUID.randomUUID();
        OrganizadorRequest request = new OrganizadorRequest();
        Organizador entity = new Organizador();
        OrganizadorResponse response = new OrganizadorResponse();

        when(repository.findByIdAndDeletedFalse(id)).thenReturn(Optional.of(entity));
        when(tagService.generateTags(entity)).thenReturn(Set.of("new-tag"));
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(response);

        OrganizadorResponse result = service.update(id, request);

        assertThat(result).isEqualTo(response);
        verify(mapper).updateEntity(request, entity);
        verify(tagService).generateTags(entity);
    }
}
