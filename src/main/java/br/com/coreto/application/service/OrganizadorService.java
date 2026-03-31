package br.com.coreto.application.service;

import br.com.coreto.application.dto.filter.OrganizadorFilter;
import br.com.coreto.application.dto.request.OrganizadorRequest;
import br.com.coreto.application.dto.response.OrganizadorResponse;
import br.com.coreto.application.mapper.OrganizadorMapper;
import br.com.coreto.domain.entity.Organizador;
import br.com.coreto.infrastructure.persistence.repository.OrganizadorRepository;
import br.com.coreto.infrastructure.persistence.repository.UsuarioRepository;
import br.com.coreto.infrastructure.persistence.specification.OrganizadorSpec;
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
public class OrganizadorService {

    private final OrganizadorRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final OrganizadorMapper mapper;
    private final TagService tagService;

    @Transactional
    public OrganizadorResponse create(OrganizadorRequest request, String currentUserId) {
        log.debug("action=create_organizador nome={} cnpj={} userId={}", request.getNome(), request.getCnpj(), currentUserId);

        Organizador entity = mapper.toEntity(request);
        entity.setTags(tagService.generateTags(entity));

        if (currentUserId != null) {
            usuarioRepository.findById(UUID.fromString(currentUserId))
                    .ifPresent(entity::setUsuario);
        }

        Organizador saved = repository.save(entity);
        log.info("action=organizador_created id={} nome={} tags={}", saved.getId(), saved.getNome(), saved.getTags());
        return mapper.toResponse(saved);
    }

    public OrganizadorResponse findById(UUID id) {
        log.debug("action=find_organizador id={}", id);
        Organizador entity = repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> {
                    log.warn("action=organizador_not_found id={}", id);
                    return new ResourceNotFoundException("Organizador", id);
                });
        return mapper.toResponse(entity);
    }

    @Transactional
    public OrganizadorResponse update(UUID id, OrganizadorRequest request) {
        log.debug("action=update_organizador id={}", id);
        Organizador entity = repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> {
                    log.warn("action=organizador_not_found_for_update id={}", id);
                    return new ResourceNotFoundException("Organizador", id);
                });
        mapper.updateEntity(request, entity);
        entity.setTags(tagService.generateTags(entity));
        Organizador saved = repository.save(entity);
        log.info("action=organizador_updated id={} tags={}", saved.getId(), saved.getTags());
        return mapper.toResponse(saved);
    }

    @Transactional
    public void softDelete(UUID id) {
        log.debug("action=delete_organizador id={}", id);
        Organizador entity = repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> {
                    log.warn("action=organizador_not_found_for_delete id={}", id);
                    return new ResourceNotFoundException("Organizador", id);
                });
        entity.setDeleted(true);
        entity.setDeletedAt(Instant.now());
        repository.save(entity);
        log.info("action=organizador_soft_deleted id={} nome={}", id, entity.getNome());
    }

    public Page<OrganizadorResponse> findAll(OrganizadorFilter filter, Pageable pageable) {
        log.debug("action=list_organizadores filter={} page={} size={}", filter, pageable.getPageNumber(), pageable.getPageSize());
        Specification<Organizador> spec = OrganizadorSpec.withFilter(filter);
        Page<OrganizadorResponse> result = repository.findAll(spec, pageable).map(mapper::toResponse);
        log.debug("action=list_organizadores_result totalElements={} totalPages={}", result.getTotalElements(), result.getTotalPages());
        return result;
    }
}
