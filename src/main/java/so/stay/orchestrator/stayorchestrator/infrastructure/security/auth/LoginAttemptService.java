package so.stay.orchestrator.stayorchestrator.infrastructure.security.auth;

import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class LoginAttemptService {

    private static final int MAX_ATTEMPTS = 5;
    private static final Duration LOCK_DURATION = Duration.ofMinutes(15);

    private final ConcurrentMap<String, Attempt> attempts = new ConcurrentHashMap<>();

    public boolean isBlocked(String key) {
        if (key == null) {
            return false;
        }
        Attempt attempt = attempts.get(key);
        if (attempt == null) {
            return false;
        }
        if (attempt.isExpired()) {
            attempts.remove(key);
            return false;
        }
        return attempt.count >= MAX_ATTEMPTS;
    }

    public void registerFailure(String key) {
        if (key == null) {
            return;
        }
        attempts.compute(key, (k, current) -> {
            if (current == null || current.isExpired()) {
                return new Attempt(1, Instant.now());
            }
            return new Attempt(current.count + 1, current.firstFailure);
        });
    }

    public void registerSuccess(String key) {
        if (key != null) {
            attempts.remove(key);
        }
    }

    private record Attempt(int count, Instant firstFailure) {
        boolean isExpired() {
            return firstFailure.plus(LOCK_DURATION).isBefore(Instant.now());
        }
    }
}
