package so.stay.orchestrator.stayorchestrator.application.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import so.stay.orchestrator.stayorchestrator.application.auth.dto.LoginRequest;
import so.stay.orchestrator.stayorchestrator.application.auth.dto.LoginResponse;
import so.stay.orchestrator.stayorchestrator.infrastructure.security.auth.LoginAttemptService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final LoginAttemptService loginAttemptService;

    public AuthenticationService(AuthenticationManager authenticationManager,
                                 LoginAttemptService loginAttemptService) {
        this.authenticationManager = authenticationManager;
        this.loginAttemptService = loginAttemptService;
    }

    public LoginResponse authenticate(LoginRequest request, String remoteAddress) {
        String emailKey = buildUserKey(request.email());
        if (loginAttemptService.isBlocked(emailKey) || loginAttemptService.isBlocked(buildIpKey(remoteAddress))) {
            throw new IllegalStateException("Account temporarily locked");
        }
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(request.email(), request.password(), null);
        token.setDetails(remoteAddress);
        Authentication authentication = authenticationManager.authenticate(token);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return new LoginResponse(userDetails.getUsername(), roles);
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
