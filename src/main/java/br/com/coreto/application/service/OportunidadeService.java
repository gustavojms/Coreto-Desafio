package br.com.coreto.application.service;

import br.com.coreto.application.dto.filter.OportunidadeFilter;
import br.com.coreto.application.dto.request.OportunidadeRequest;
import br.com.coreto.application.dto.response.OportunidadeResponse;
import br.com.coreto.application.mapper.OportunidadeMapper;
import br.com.coreto.domain.entity.Oportunidade;
import br.com.coreto.domain.entity.Organizador;
import br.com.coreto.domain.enums.StatusOportunidade;
import br.com.coreto.infrastructure.persistence.repository.OportunidadeRepository;
import br.com.coreto.infrastructure.persistence.repository.OrganizadorRepository;
import br.com.coreto.infrastructure.persistence.specification.OportunidadeSpec;
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
public class OportunidadeService {

    private final OportunidadeRepository repository;
    private final OrganizadorRepository organizadorRepository;
    private final OportunidadeMapper mapper;
    private final TagService tagService;

    @Transactional
    public OportunidadeResponse create(OportunidadeRequest request) {
        log.debug("action=create_oportunidade titulo={} organizadorId={} tipo={}", request.getTitulo(), request.getOrganizadorId(), request.getTipoOportunidade());

        Organizador organizador = organizadorRepository.findByIdAndDeletedFalse(request.getOrganizadorId())
                .orElseThrow(() -> {
                    log.error("action=organizador_not_found_for_oportunidade organizadorId={}", request.getOrganizadorId());
                    return new ResourceNotFoundException("Organizador", request.getOrganizadorId());
                });

        Oportunidade entity = mapper.toEntity(request);
        entity.setOrganizador(organizador);
        entity.setStatus(StatusOportunidade.RASCUNHO);
        entity.setTags(tagService.generateTags(entity));

        Oportunidade saved = repository.save(entity);
        log.info("action=oportunidade_created id={} titulo={} tipo={} organizador={} tags={}", saved.getId(), saved.getTitulo(), saved.getTipoOportunidade(), organizador.getNome(), saved.getTags());
        return mapper.toResponse(saved);
    }

    public OportunidadeResponse findById(UUID id) {
        log.debug("action=find_oportunidade id={}", id);
        Oportunidade entity = repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> {
                    log.warn("action=oportunidade_not_found id={}", id);
                    return new ResourceNotFoundException("Oportunidade", id);
                });
        return mapper.toResponse(entity);
    }

    @Transactional
    public OportunidadeResponse update(UUID id, OportunidadeRequest request) {
        log.debug("action=update_oportunidade id={}", id);
        Oportunidade entity = repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> {
                    log.warn("action=oportunidade_not_found_for_update id={}", id);
                    return new ResourceNotFoundException("Oportunidade", id);
                });
        mapper.updateEntity(request, entity);
        entity.setTags(tagService.generateTags(entity));
        Oportunidade saved = repository.save(entity);
        log.info("action=oportunidade_updated id={} titulo={} tags={}", saved.getId(), saved.getTitulo(), saved.getTags());
        return mapper.toResponse(saved);
    }

    @Transactional
    public void softDelete(UUID id) {
        log.debug("action=delete_oportunidade id={}", id);
        Oportunidade entity = repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> {
                    log.warn("action=oportunidade_not_found_for_delete id={}", id);
                    return new ResourceNotFoundException("Oportunidade", id);
                });
        entity.setDeleted(true);
        entity.setDeletedAt(Instant.now());
        repository.save(entity);
        log.info("action=oportunidade_soft_deleted id={} titulo={}", id, entity.getTitulo());
    }

    public Page<OportunidadeResponse> findAll(OportunidadeFilter filter, Pageable pageable) {
        log.debug("action=list_oportunidades filter={} page={} size={}", filter, pageable.getPageNumber(), pageable.getPageSize());
        Specification<Oportunidade> spec = OportunidadeSpec.withFilter(filter);
        Page<OportunidadeResponse> result = repository.findAll(spec, pageable).map(mapper::toResponse);
        log.debug("action=list_oportunidades_result totalElements={} totalPages={}", result.getTotalElements(), result.getTotalPages());
        return result;
    }
}
