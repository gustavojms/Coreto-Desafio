package br.com.coreto.web.exception;

import br.com.coreto.application.dto.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> fieldErrors.put(error.getField(), error.getDefaultMessage()));
        ex.getBindingResult().getGlobalErrors()
                .forEach(error -> fieldErrors.put(error.getObjectName(), error.getDefaultMessage()));

        log.warn("action=validation_error path={} fields={}", request.getRequestURI(), fieldErrors);

        return new ErrorResponse(
                Instant.now(), 400, "Erro de validacao",
                "Um ou mais campos estao invalidos",
                request.getRequestURI(), fieldErrors
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        log.warn("action=resource_not_found path={} message={}", request.getRequestURI(), ex.getMessage());

        return new ErrorResponse(
                Instant.now(), 404, "Nao encontrado",
                ex.getMessage(), request.getRequestURI(), null
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleForbidden(AccessDeniedException ex, HttpServletRequest request) {
        log.warn("action=access_denied path={} method={}", request.getRequestURI(), request.getMethod());

        return new ErrorResponse(
                Instant.now(), 403, "Acesso negado",
                "Voce nao tem permissao para acessar este recurso",
                request.getRequestURI(), null
        );
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse handleBusiness(BusinessException ex, HttpServletRequest request) {
        log.warn("action=business_error path={} message={}", request.getRequestURI(), ex.getMessage());

        return new ErrorResponse(
                Instant.now(), 422, "Erro de negocio",
                ex.getMessage(), request.getRequestURI(), null
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGeneral(Exception ex, HttpServletRequest request) {
        log.error("action=unexpected_error path={} exception={} message={}", request.getRequestURI(), ex.getClass().getSimpleName(), ex.getMessage(), ex);

        return new ErrorResponse(
                Instant.now(), 500, "Erro interno",
                "Ocorreu um erro inesperado",
                request.getRequestURI(), null
        );
    }
}
