package so.stay.orchestrator.stayorchestrator.infrastructure.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import so.stay.orchestrator.stayorchestrator.infrastructure.security.provider.ApiKeyAuthenticationProvider;
import so.stay.orchestrator.stayorchestrator.infrastructure.security.userdetails.StayUserDetailsService;

@Configuration
public class AuthenticationManagerConfig {

    private final ApiKeyAuthenticationProvider apiKeyAuthenticationProvider;
    private final StayUserDetailsService stayUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationManagerConfig(ApiKeyAuthenticationProvider apiKeyAuthenticationProvider,
                                       StayUserDetailsService stayUserDetailsService,
                                       PasswordEncoder passwordEncoder) {
        this.apiKeyAuthenticationProvider = apiKeyAuthenticationProvider;
        this.stayUserDetailsService = stayUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
        daoProvider.setUserDetailsService(stayUserDetailsService);
        daoProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(daoProvider, apiKeyAuthenticationProvider);
    }
}
