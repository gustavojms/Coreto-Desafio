package br.com.coreto.application.service;

import br.com.coreto.application.dto.request.ResolvedorRequest;
import br.com.coreto.application.dto.response.ResolvedorResponse;
import br.com.coreto.application.mapper.ResolvedorMapper;
import br.com.coreto.domain.entity.Resolvedor;
import br.com.coreto.infrastructure.persistence.repository.ResolvedorRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResolvedorServiceTest {

    @Mock private ResolvedorRepository repository;
    @Mock private UsuarioRepository usuarioRepository;
    @Mock private ResolvedorMapper mapper;
    @Mock private TagService tagService;

    @InjectMocks
    private ResolvedorService service;

    @Test
    void shouldCreateResolvedor() {
        ResolvedorRequest request = new ResolvedorRequest();
        Resolvedor entity = new Resolvedor();
        entity.setId(UUID.randomUUID());
        ResolvedorResponse response = new ResolvedorResponse();

        when(mapper.toEntity(request)).thenReturn(entity);
        when(tagService.generateTags(entity)).thenReturn(Set.of("tag"));
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(response);

        assertThat(service.create(request, null)).isEqualTo(response);
    }

    @Test
    void shouldThrowNotFoundOnMissingResolvedor() {
        UUID id = UUID.randomUUID();
        when(repository.findByIdAndDeletedFalse(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(id))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void shouldSoftDeleteResolvedor() {
        UUID id = UUID.randomUUID();
        Resolvedor entity = new Resolvedor();
        when(repository.findByIdAndDeletedFalse(id)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(entity);

        service.softDelete(id);

        assertThat(entity.isDeleted()).isTrue();
    }
}
