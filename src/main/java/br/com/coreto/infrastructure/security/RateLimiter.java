package br.com.coreto.infrastructure.security;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimiter {

    private static final int MAX_ATTEMPTS = 5;
    private static final long WINDOW_SECONDS = 300; // 5 minutes

    private final ConcurrentHashMap<String, AttemptInfo> attempts = new ConcurrentHashMap<>();

    public boolean isBlocked(String key) {
        AttemptInfo info = attempts.get(key);
        if (info == null) {
            return false;
        }
        if (info.windowStart.plusSeconds(WINDOW_SECONDS).isBefore(Instant.now())) {
            attempts.remove(key);
            return false;
        }
        return info.count >= MAX_ATTEMPTS;
    }

    public void recordFailedAttempt(String key) {
        attempts.compute(key, (k, info) -> {
            if (info == null || info.windowStart.plusSeconds(WINDOW_SECONDS).isBefore(Instant.now())) {
                return new AttemptInfo(Instant.now(), 1);
            }
            return new AttemptInfo(info.windowStart, info.count + 1);
        });
    }

    public void resetAttempts(String key) {
        attempts.remove(key);
    }

    private record AttemptInfo(Instant windowStart, int count) {}
}
