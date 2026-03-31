package br.com.coreto.web.controller;

import br.com.coreto.application.dto.filter.ResolvedorFilter;
import br.com.coreto.application.dto.request.ResolvedorRequest;
import br.com.coreto.application.dto.response.ResolvedorResponse;
import br.com.coreto.application.service.ResolvedorService;
import br.com.coreto.infrastructure.security.CurrentUser;
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
@RequestMapping("/api/v1/resolvedores")
@RequiredArgsConstructor
@Tag(name = "Resolvedores", description = "CRUD de Resolvedores")
public class ResolvedorController {

    private final ResolvedorService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN','RESOLVEDOR')")
    @Operation(summary = "Criar resolvedor")
    public ResolvedorResponse create(
            @Valid @RequestBody ResolvedorRequest request,
            @CurrentUser String userId) {
        return service.create(request, userId);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar resolvedor por ID")
    public ResolvedorResponse findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN') or (hasRole('RESOLVEDOR') and @ownershipChecker.ownsResolvedor(#id, authentication))")
    @Operation(summary = "Atualizar resolvedor")
    public ResolvedorResponse update(
            @PathVariable UUID id,
            @Valid @RequestBody ResolvedorRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN') or (hasRole('RESOLVEDOR') and @ownershipChecker.ownsResolvedor(#id, authentication))")
    @Operation(summary = "Deletar resolvedor (soft delete)")
    public void delete(@PathVariable UUID id) {
        service.softDelete(id);
    }

    @GetMapping
    @Operation(summary = "Listar resolvedores com filtros e paginacao")
    public Page<ResolvedorResponse> findAll(
            @ModelAttribute ResolvedorFilter filter,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return service.findAll(filter, pageable);
    }
}
