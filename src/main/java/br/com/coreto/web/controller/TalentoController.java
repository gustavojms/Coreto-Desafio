package br.com.coreto.web.controller;

import br.com.coreto.application.dto.filter.TalentoFilter;
import br.com.coreto.application.dto.request.TalentoRequest;
import br.com.coreto.application.dto.response.TalentoResponse;
import br.com.coreto.application.service.TalentoService;
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
@RequestMapping("/api/v1/talentos")
@RequiredArgsConstructor
@Tag(name = "Talentos", description = "CRUD de Talentos")
public class TalentoController {

    private final TalentoService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN','TALENTO')")
    @Operation(summary = "Criar talento")
    public TalentoResponse create(
            @Valid @RequestBody TalentoRequest request,
            @CurrentUser String userId) {
        return service.create(request, userId);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar talento por ID")
    public TalentoResponse findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN') or (hasRole('TALENTO') and @ownershipChecker.ownsTalento(#id, authentication))")
    @Operation(summary = "Atualizar talento")
    public TalentoResponse update(
            @PathVariable UUID id,
            @Valid @RequestBody TalentoRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN') or (hasRole('TALENTO') and @ownershipChecker.ownsTalento(#id, authentication))")
    @Operation(summary = "Deletar talento (soft delete)")
    public void delete(@PathVariable UUID id) {
        service.softDelete(id);
    }

    @GetMapping
    @Operation(summary = "Listar talentos com filtros e paginacao")
    public Page<TalentoResponse> findAll(
            @ModelAttribute TalentoFilter filter,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return service.findAll(filter, pageable);
    }
}
