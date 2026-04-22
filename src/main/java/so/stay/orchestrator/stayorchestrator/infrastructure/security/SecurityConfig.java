package so.stay.orchestrator.stayorchestrator.infrastructure.security;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CorsProperties corsProperties;
    private final SecurityHeadersCustomizer securityHeadersCustomizer;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // API REST → pas de CSRF
                .csrf(csrf -> csrf.disable())

                // CORS
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(corsProperties.getAllowedOrigins());
                    config.setAllowedMethods(corsProperties.getAllowedMethods());
                    config.setAllowedHeaders(corsProperties.getAllowedHeaders());
                    config.setAllowCredentials(corsProperties.isAllowCredentials());
                    return config;
                }))

                // Stateless (API)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                org.springframework.security.config.http.SessionCreationPolicy.STATELESS
                        )
                )

                // Routes publiques / protégées
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/health").permitAll()
                        .requestMatchers("/swagger-ui/**", "/api-docs/**").permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/riads/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/prestations/**").permitAll()

                        .anyRequest().authenticated()
                )

                // Headers sécurité
                .headers(headers -> securityHeadersCustomizer.customize(headers))

                // Auth basique (à remplacer par JWT plus tard)
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
