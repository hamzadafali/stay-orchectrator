package so.stay.orchestrator.stayorchestrator.infrastructure.security.provider;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import so.stay.orchestrator.stayorchestrator.infrastructure.security.model.ApiKeyAuthenticationToken;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ApiKeyAuthenticationProvider implements AuthenticationProvider {

    @Value("${security.partner.api-key}")
    private String expectedApiKey;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        String providedApiKey = (String) authentication.getCredentials();

        if (providedApiKey == null || !providedApiKey.equals(expectedApiKey)) {
            throw new BadCredentialsException("Invalid API key");
        }

        return ApiKeyAuthenticationToken.authenticated(
                "partner-api-client",
                providedApiKey,
                List.of(new SimpleGrantedAuthority("ROLE_PARTNER"))
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return ApiKeyAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
