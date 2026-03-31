package br.com.coreto.web.controller;

import br.com.coreto.application.dto.filter.OportunidadeFilter;
import br.com.coreto.application.dto.request.OportunidadeRequest;
import br.com.coreto.application.dto.response.OportunidadeResponse;
import br.com.coreto.application.service.OportunidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/oportunidades")
@RequiredArgsConstructor
@Tag(name = "Oportunidades", description = "CRUD de Oportunidades")
public class OportunidadeController {

    private final OportunidadeService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN','ORGANIZADOR')")
    @Operation(summary = "Criar oportunidade")
    public OportunidadeResponse create(@Valid @RequestBody OportunidadeRequest request) {
        return service.create(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar oportunidade por ID")
    public OportunidadeResponse findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN') or (hasRole('ORGANIZADOR') and @ownershipChecker.ownsOportunidade(#id, authentication))")
    @Operation(summary = "Atualizar oportunidade")
    public OportunidadeResponse update(
            @PathVariable UUID id,
            @Valid @RequestBody OportunidadeRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN') or (hasRole('ORGANIZADOR') and @ownershipChecker.ownsOportunidade(#id, authentication))")
    @Operation(summary = "Deletar oportunidade (soft delete)")
    public void delete(@PathVariable UUID id) {
        service.softDelete(id);
    }

    @GetMapping
    @Operation(summary = "Listar oportunidades com filtros e paginacao")
    public Page<OportunidadeResponse> findAll(
            @ModelAttribute OportunidadeFilter filter,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return service.findAll(filter, pageable);
    }
}
