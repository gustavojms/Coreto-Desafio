package br.com.coreto.application.service;

import br.com.coreto.application.dto.request.OportunidadeRequest;
import br.com.coreto.application.dto.response.OportunidadeResponse;
import br.com.coreto.application.mapper.OportunidadeMapper;
import br.com.coreto.domain.entity.Oportunidade;
import br.com.coreto.domain.entity.Organizador;
import br.com.coreto.domain.enums.StatusOportunidade;
import br.com.coreto.infrastructure.persistence.repository.OportunidadeRepository;
import br.com.coreto.infrastructure.persistence.repository.OrganizadorRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OportunidadeServiceTest {

    @Mock
    private OportunidadeRepository repository;

    @Mock
    private OrganizadorRepository organizadorRepository;

    @Mock
    private OportunidadeMapper mapper;

    @Mock
    private TagService tagService;

    @InjectMocks
    private OportunidadeService service;

    @Test
    void shouldCreateOportunidade() {
        UUID orgId = UUID.randomUUID();
        OportunidadeRequest request = new OportunidadeRequest();
        request.setOrganizadorId(orgId);

        Organizador organizador = new Organizador();
        Oportunidade entity = new Oportunidade();
        entity.setId(UUID.randomUUID());
        OportunidadeResponse response = new OportunidadeResponse();

        when(organizadorRepository.findByIdAndDeletedFalse(orgId)).thenReturn(Optional.of(organizador));
        when(mapper.toEntity(request)).thenReturn(entity);
        when(tagService.generateTags(entity)).thenReturn(Set.of("tag1"));
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(response);

        OportunidadeResponse result = service.create(request);

        assertThat(result).isEqualTo(response);
        assertThat(entity.getStatus()).isEqualTo(StatusOportunidade.RASCUNHO);
        verify(repository).save(entity);
    }

    @Test
    void shouldThrowWhenOrganizadorNotFound() {
        UUID orgId = UUID.randomUUID();
        OportunidadeRequest request = new OportunidadeRequest();
        request.setOrganizadorId(orgId);

        when(organizadorRepository.findByIdAndDeletedFalse(orgId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.create(request))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void shouldSoftDeleteOportunidade() {
        UUID id = UUID.randomUUID();
        Oportunidade entity = new Oportunidade();

        when(repository.findByIdAndDeletedFalse(id)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(entity);

        service.softDelete(id);

        assertThat(entity.isDeleted()).isTrue();
        assertThat(entity.getDeletedAt()).isNotNull();
    }
}
