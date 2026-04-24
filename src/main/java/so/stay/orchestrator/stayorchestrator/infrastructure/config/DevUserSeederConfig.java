package so.stay.orchestrator.stayorchestrator.infrastructure.config;


import lombok.Builder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import so.stay.orchestrator.stayorchestrator.domain.shared.valueobject.Email;
import so.stay.orchestrator.stayorchestrator.domain.user.model.User;
import so.stay.orchestrator.stayorchestrator.domain.user.model.UserRole;
import so.stay.orchestrator.stayorchestrator.domain.user.port.UserRepository;

@Configuration
public class DevUserSeederConfig {

    @Bean
    CommandLineRunner seedDevUser(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            String email = "admin@stay.com";

            if (userRepository.findByEmail(email).isPresent()) {
                return;
            }
            User user = new User(
                    null,
                    new Email(email),
                    passwordEncoder.encode("admin123"),
                    "hamza dafali",
                    UserRole.ADMIN,
                    true
            );

            userRepository.save(user);

            System.out.println("Dev user created: admin@test.com / admin123");
        };
    }
}
