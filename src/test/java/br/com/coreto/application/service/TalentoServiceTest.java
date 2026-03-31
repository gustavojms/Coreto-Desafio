package br.com.coreto.application.service;

import br.com.coreto.application.dto.request.TalentoRequest;
import br.com.coreto.application.dto.response.TalentoResponse;
import br.com.coreto.application.mapper.TalentoMapper;
import br.com.coreto.domain.entity.Talento;
import br.com.coreto.infrastructure.persistence.repository.TalentoRepository;
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
class TalentoServiceTest {

    @Mock private TalentoRepository repository;
    @Mock private UsuarioRepository usuarioRepository;
    @Mock private TalentoMapper mapper;
    @Mock private TagService tagService;

    @InjectMocks
    private TalentoService service;

    @Test
    void shouldCreateTalento() {
        TalentoRequest request = new TalentoRequest();
        Talento entity = new Talento();
        entity.setId(UUID.randomUUID());
        TalentoResponse response = new TalentoResponse();

        when(mapper.toEntity(request)).thenReturn(entity);
        when(tagService.generateTags(entity)).thenReturn(Set.of("tag"));
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(response);

        assertThat(service.create(request, null)).isEqualTo(response);
    }

    @Test
    void shouldThrowNotFoundOnMissingTalento() {
        UUID id = UUID.randomUUID();
        when(repository.findByIdAndDeletedFalse(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(id))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void shouldSoftDeleteTalento() {
        UUID id = UUID.randomUUID();
        Talento entity = new Talento();
        when(repository.findByIdAndDeletedFalse(id)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(entity);

        service.softDelete(id);

        assertThat(entity.isDeleted()).isTrue();
    }
}
