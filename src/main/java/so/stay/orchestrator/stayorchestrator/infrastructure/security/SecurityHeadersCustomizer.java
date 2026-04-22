package so.stay.orchestrator.stayorchestrator.infrastructure.security;

import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.stereotype.Component;

@Component
public class SecurityHeadersCustomizer {

    public void customize(HeadersConfigurer<?> headers) {

        headers
                // Protection XSS
                .xssProtection(xss -> xss.disable()) // moderne: CSP recommandé

                // Content Security Policy
                .contentSecurityPolicy(csp -> csp
                        .policyDirectives("default-src 'self'; script-src 'self'; object-src 'none';")
                )

                // Clickjacking
                .frameOptions(frame -> frame.deny())

                // HSTS (HTTPS only)
                .httpStrictTransportSecurity(hsts -> hsts
                        .includeSubDomains(true)
                        .maxAgeInSeconds(31536000)
                )

                // MIME sniffing
                .contentTypeOptions(cto -> {})
        ;
    }
}
