package so.stay.orchestrator.stayorchestrator.infrastructure.security.auth;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEventListener {

    private final LoginAttemptService loginAttemptService;

    public AuthenticationEventListener(LoginAttemptService loginAttemptService) {
        this.loginAttemptService = loginAttemptService;
    }

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent event) {
        updateAttempts(event.getAuthentication(), true);
    }

    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent event) {
        updateAttempts(event.getAuthentication(), false);
    }

    private void updateAttempts(Authentication authentication, boolean success) {
        if (authentication == null || authentication.getName() == null) {
            return;
        }
        String userKey = buildUserKey(authentication.getName());
        String ipKey = buildIpKey(extractDetail(authentication));
        if (success) {
            loginAttemptService.registerSuccess(userKey);
            loginAttemptService.registerSuccess(ipKey);
        } else {
            loginAttemptService.registerFailure(userKey);
            loginAttemptService.registerFailure(ipKey);
        }
    }

    private String extractDetail(Authentication authentication) {
        Object details = authentication.getDetails();
        if (details instanceof String stringDetail && !stringDetail.isBlank()) {
            return stringDetail;
        }
        return null;
    }

    private static String buildUserKey(String email) {
        if (email == null) {
            return null;
        }
        return "user:" + email.toLowerCase();
    }

    private static String buildIpKey(String ip) {
        if (ip == null) {
            return null;
        }
        return "ip:" + ip;
    }
}
