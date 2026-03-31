package br.com.coreto.application.service;

import br.com.coreto.application.dto.filter.TalentoFilter;
import br.com.coreto.application.dto.request.TalentoRequest;
import br.com.coreto.application.dto.response.TalentoResponse;
import br.com.coreto.application.mapper.TalentoMapper;
import br.com.coreto.domain.entity.Talento;
import br.com.coreto.infrastructure.persistence.repository.TalentoRepository;
import br.com.coreto.infrastructure.persistence.repository.UsuarioRepository;
import br.com.coreto.infrastructure.persistence.specification.TalentoSpec;
import br.com.coreto.web.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class TalentoService {

    private final TalentoRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final TalentoMapper mapper;
    private final TagService tagService;

    @Transactional
    public TalentoResponse create(TalentoRequest request, String currentUserId) {
        log.debug("action=create_talento nome={} senioridade={} userId={}", request.getNomeCompleto(), request.getSenioridade(), currentUserId);

        Talento entity = mapper.toEntity(request);
        entity.setTags(tagService.generateTags(entity));

        if (currentUserId != null) {
            usuarioRepository.findById(UUID.fromString(currentUserId))
                    .ifPresent(entity::setUsuario);
        }

        Talento saved = repository.save(entity);
        log.info("action=talento_created id={} nome={} senioridade={} tags={}", saved.getId(), saved.getNomeCompleto(), saved.getSenioridade(), saved.getTags());
        return mapper.toResponse(saved);
    }

    public TalentoResponse findById(UUID id) {
        log.debug("action=find_talento id={}", id);
        Talento entity = repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> {
                    log.warn("action=talento_not_found id={}", id);
                    return new ResourceNotFoundException("Talento", id);
                });
        return mapper.toResponse(entity);
    }

    @Transactional
    public TalentoResponse update(UUID id, TalentoRequest request) {
        log.debug("action=update_talento id={}", id);
        Talento entity = repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> {
                    log.warn("action=talento_not_found_for_update id={}", id);
                    return new ResourceNotFoundException("Talento", id);
                });
        mapper.updateEntity(request, entity);
        entity.setTags(tagService.generateTags(entity));
        Talento saved = repository.save(entity);
        log.info("action=talento_updated id={} tags={}", saved.getId(), saved.getTags());
        return mapper.toResponse(saved);
    }

    @Transactional
    public void softDelete(UUID id) {
        log.debug("action=delete_talento id={}", id);
        Talento entity = repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> {
                    log.warn("action=talento_not_found_for_delete id={}", id);
                    return new ResourceNotFoundException("Talento", id);
                });
        entity.setDeleted(true);
        entity.setDeletedAt(Instant.now());
        repository.save(entity);
        log.info("action=talento_soft_deleted id={} nome={}", id, entity.getNomeCompleto());
    }

    public Page<TalentoResponse> findAll(TalentoFilter filter, Pageable pageable) {
        log.debug("action=list_talentos filter={} page={} size={}", filter, pageable.getPageNumber(), pageable.getPageSize());
        Specification<Talento> spec = TalentoSpec.withFilter(filter);
        Page<TalentoResponse> result = repository.findAll(spec, pageable).map(mapper::toResponse);
        log.debug("action=list_talentos_result totalElements={}", result.getTotalElements());
        return result;
    }
}
