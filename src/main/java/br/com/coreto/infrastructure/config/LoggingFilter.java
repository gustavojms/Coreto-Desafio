package br.com.coreto.infrastructure.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
@Order(1)
@Slf4j
public class LoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        long startTime = System.currentTimeMillis();

        try {
            String requestId = UUID.randomUUID().toString().substring(0, 8);
            MDC.put("requestId", requestId);
            MDC.put("method", httpRequest.getMethod());
            MDC.put("uri", httpRequest.getRequestURI());
            MDC.put("remoteAddr", httpRequest.getRemoteAddr());

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.getPrincipal() instanceof Jwt jwt) {
                MDC.put("userId", jwt.getSubject());
                MDC.put("userRole", jwt.getClaimAsString("role"));
            } else {
                MDC.put("userId", "anonymous");
            }

            log.debug(">>> {} {} from={}", httpRequest.getMethod(), httpRequest.getRequestURI(), httpRequest.getRemoteAddr());

            chain.doFilter(request, response);

        } finally {
            long duration = System.currentTimeMillis() - startTime;
            MDC.put("durationMs", String.valueOf(duration));
            MDC.put("status", String.valueOf(httpResponse.getStatus()));

            int status = httpResponse.getStatus();
            if (status >= 500) {
                log.error("<<< {} {} status={} duration={}ms", httpRequest.getMethod(), httpRequest.getRequestURI(), status, duration);
            } else if (status >= 400) {
                log.warn("<<< {} {} status={} duration={}ms", httpRequest.getMethod(), httpRequest.getRequestURI(), status, duration);
            } else {
                log.info("<<< {} {} status={} duration={}ms", httpRequest.getMethod(), httpRequest.getRequestURI(), status, duration);
            }

            MDC.clear();
        }
    }
}
