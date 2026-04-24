package so.stay.orchestrator.stayorchestrator.infrastructure.security.model;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class ApiKeyAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal;
    private final Object credentials;

    private ApiKeyAuthenticationToken(
            Object principal,
            Object credentials,
            Collection<? extends GrantedAuthority> authorities
    ) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(authorities != null && !authorities.isEmpty());
    }

    public static ApiKeyAuthenticationToken unauthenticated(String apiKey) {
        return new ApiKeyAuthenticationToken(null, apiKey, null);
    }

    public static ApiKeyAuthenticationToken authenticated(
            Object principal,
            String apiKey,
            Collection<? extends GrantedAuthority> authorities
    ) {
        return new ApiKeyAuthenticationToken(principal, apiKey, authorities);
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
