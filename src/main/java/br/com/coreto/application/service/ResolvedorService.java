package br.com.coreto.application.service;

import br.com.coreto.application.dto.filter.ResolvedorFilter;
import br.com.coreto.application.dto.request.ResolvedorRequest;
import br.com.coreto.application.dto.response.ResolvedorResponse;
import br.com.coreto.application.mapper.ResolvedorMapper;
import br.com.coreto.domain.entity.Resolvedor;
import br.com.coreto.infrastructure.persistence.repository.ResolvedorRepository;
import br.com.coreto.infrastructure.persistence.repository.UsuarioRepository;
import br.com.coreto.infrastructure.persistence.specification.ResolvedorSpec;
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
public class ResolvedorService {

    private final ResolvedorRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final ResolvedorMapper mapper;
    private final TagService tagService;

    @Transactional
    public ResolvedorResponse create(ResolvedorRequest request, String currentUserId) {
        log.debug("action=create_resolvedor nome={} tipo={} userId={}", request.getNome(), request.getTipoIniciativa(), currentUserId);

        Resolvedor entity = mapper.toEntity(request);
        entity.setTags(tagService.generateTags(entity));

        if (currentUserId != null) {
            usuarioRepository.findById(UUID.fromString(currentUserId))
                    .ifPresent(entity::setUsuario);
        }

        Resolvedor saved = repository.save(entity);
        log.info("action=resolvedor_created id={} nome={} tipo={} tags={}", saved.getId(), saved.getNome(), saved.getTipoIniciativa(), saved.getTags());
        return mapper.toResponse(saved);
    }

    public ResolvedorResponse findById(UUID id) {
        log.debug("action=find_resolvedor id={}", id);
        Resolvedor entity = repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> {
                    log.warn("action=resolvedor_not_found id={}", id);
                    return new ResourceNotFoundException("Resolvedor", id);
                });
        return mapper.toResponse(entity);
    }

    @Transactional
    public ResolvedorResponse update(UUID id, ResolvedorRequest request) {
        log.debug("action=update_resolvedor id={}", id);
        Resolvedor entity = repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> {
                    log.warn("action=resolvedor_not_found_for_update id={}", id);
                    return new ResourceNotFoundException("Resolvedor", id);
                });
        mapper.updateEntity(request, entity);
        entity.setTags(tagService.generateTags(entity));
        Resolvedor saved = repository.save(entity);
        log.info("action=resolvedor_updated id={} tags={}", saved.getId(), saved.getTags());
        return mapper.toResponse(saved);
    }

    @Transactional
    public void softDelete(UUID id) {
        log.debug("action=delete_resolvedor id={}", id);
        Resolvedor entity = repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> {
                    log.warn("action=resolvedor_not_found_for_delete id={}", id);
                    return new ResourceNotFoundException("Resolvedor", id);
                });
        entity.setDeleted(true);
        entity.setDeletedAt(Instant.now());
        repository.save(entity);
        log.info("action=resolvedor_soft_deleted id={} nome={}", id, entity.getNome());
    }

    public Page<ResolvedorResponse> findAll(ResolvedorFilter filter, Pageable pageable) {
        log.debug("action=list_resolvedores filter={} page={} size={}", filter, pageable.getPageNumber(), pageable.getPageSize());
        Specification<Resolvedor> spec = ResolvedorSpec.withFilter(filter);
        Page<ResolvedorResponse> result = repository.findAll(spec, pageable).map(mapper::toResponse);
        log.debug("action=list_resolvedores_result totalElements={}", result.getTotalElements());
        return result;
    }
}
