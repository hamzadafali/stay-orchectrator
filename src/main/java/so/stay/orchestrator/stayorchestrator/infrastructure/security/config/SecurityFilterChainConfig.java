package so.stay.orchestrator.stayorchestrator.infrastructure.security.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.web.cors.CorsConfigurationSource;
import so.stay.orchestrator.stayorchestrator.infrastructure.security.filter.ApiKeyAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
//todo name security config
public class SecurityFilterChainConfig {

    private final CorsConfigurationSource corsConfigurationSource;

    /**
     * Chain #1 — Lecture publique du catalogue
     * GET /api/riads, GET /api/prestations, /api/health
     */
    @Bean
    @Order(1)
    public SecurityFilterChain publicChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher(
                        "/api/health",
                        "/swagger-ui/**", "/api-docs/**"   // Swagger en dev
                )
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .sessionManagement(s -> s.sessionCreationPolicy(STATELESS))
                .csrf(csrf -> csrf.disable());
        return http.build();
    }

    /**
     * Chain #2 — API partenaires (agences OTA via API Key)
     * Accès lecture seule au catalogue pour intégration externe
     */
    @Bean
    @Order(2)
    public SecurityFilterChain partnerChain(HttpSecurity http
        ,AuthenticationManager authenticationManager) throws Exception {
        http
                .securityMatcher("/api/partner/**")
                .addFilterBefore(new ApiKeyAuthenticationFilter(authenticationManager),
                                 AuthorizationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().hasRole("PARTNER")
                )
                .sessionManagement(s -> s.sessionCreationPolicy(STATELESS))
                .csrf(csrf -> csrf.disable());
        return http.build();
    }

    /**
     * Chain #3 — API privée (JWT requis)
     * Gestion des riads, prestations, utilisateurs
     */
    @Bean
    @Order(3)
    public SecurityFilterChain privateApiChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/**")
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .authorizeHttpRequests(auth -> auth
                        // Lecture : VIEWER, OPERATOR, ADMIN
                        .requestMatchers(HttpMethod.GET, "/api/riads/**")
                        .hasAnyRole("VIEWER", "OPERATOR", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/prestations/**")
                        .hasAnyRole("VIEWER", "OPERATOR", "ADMIN")
                        // Écriture riads et prestations : OPERATOR, ADMIN
                        .requestMatchers(HttpMethod.POST, "/api/riads/**")
                        .hasAnyRole("OPERATOR", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/riads/**")
                        .hasAnyRole("OPERATOR", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/riads/**")
                        .hasRole("ADMIN")                            // Suppression : ADMIN uniquement
                        // Gestion utilisateurs : ADMIN uniquement
                        .requestMatchers("/api/users/**")
                        .hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .sessionManagement(s -> s.sessionCreationPolicy(STATELESS))
                .csrf(csrf -> csrf.disable());
        return http.build();
    }

    /**
     * Chain #4 — Actuator (monitoring interne)
     */
    @Bean
    @Order(4)
    public SecurityFilterChain actuatorChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/actuator/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/actuator/health").permitAll()
                        .anyRequest().hasRole("MONITORING")
                );
        return http.build();
    }
}
