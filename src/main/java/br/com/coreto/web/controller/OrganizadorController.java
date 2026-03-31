package br.com.coreto.web.controller;

import br.com.coreto.application.dto.filter.OrganizadorFilter;
import br.com.coreto.application.dto.request.OrganizadorRequest;
import br.com.coreto.application.dto.response.OrganizadorResponse;
import br.com.coreto.application.service.OrganizadorService;
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
@RequestMapping("/api/v1/organizadores")
@RequiredArgsConstructor
@Tag(name = "Organizadores", description = "CRUD de Organizadores")
public class OrganizadorController {

    private final OrganizadorService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN','ORGANIZADOR')")
    @Operation(summary = "Criar organizador")
    public OrganizadorResponse create(
            @Valid @RequestBody OrganizadorRequest request,
            @CurrentUser String userId) {
        return service.create(request, userId);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar organizador por ID")
    public OrganizadorResponse findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN') or (hasRole('ORGANIZADOR') and @ownershipChecker.ownsOrganizador(#id, authentication))")
    @Operation(summary = "Atualizar organizador")
    public OrganizadorResponse update(
            @PathVariable UUID id,
            @Valid @RequestBody OrganizadorRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN') or (hasRole('ORGANIZADOR') and @ownershipChecker.ownsOrganizador(#id, authentication))")
    @Operation(summary = "Deletar organizador (soft delete)")
    public void delete(@PathVariable UUID id) {
        service.softDelete(id);
    }

    @GetMapping
    @Operation(summary = "Listar organizadores com filtros e paginacao")
    public Page<OrganizadorResponse> findAll(
            @ModelAttribute OrganizadorFilter filter,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return service.findAll(filter, pageable);
    }
}
