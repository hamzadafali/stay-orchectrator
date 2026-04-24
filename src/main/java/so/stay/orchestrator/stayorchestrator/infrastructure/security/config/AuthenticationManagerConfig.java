package so.stay.orchestrator.stayorchestrator.infrastructure.security.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import so.stay.orchestrator.stayorchestrator.infrastructure.security.provider.ApiKeyAuthenticationProvider;

@Configuration
@RequiredArgsConstructor
public class AuthenticationManagerConfig {

    private final ApiKeyAuthenticationProvider apiKeyAuthenticationProvider;

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(apiKeyAuthenticationProvider);
    }
}
