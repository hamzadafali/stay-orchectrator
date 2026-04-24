package so.stay.orchestrator.stayorchestrator.infrastructure.persistence.adapter;

import org.springframework.stereotype.Component;
import so.stay.orchestrator.stayorchestrator.domain.shared.valueobject.Email;
import so.stay.orchestrator.stayorchestrator.domain.user.model.User;
import so.stay.orchestrator.stayorchestrator.domain.user.port.UserRepository;
import so.stay.orchestrator.stayorchestrator.infrastructure.persistence.entity.UserEntity;
import so.stay.orchestrator.stayorchestrator.infrastructure.persistence.repository.JpaUserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserRepositoryAdapter implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    public UserRepositoryAdapter(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public List<User> findAll() {
        return jpaUserRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpaUserRepository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaUserRepository.findByEmail(email)
                .map(this::toDomain);
    }

    @Override
    public User save(User user) {
        UserEntity entity = toEntity(user);
        UserEntity saved = jpaUserRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public void deleteById(Long id) {
        jpaUserRepository.deleteById(id);
    }

    private User toDomain(UserEntity entity) {
        return User.builder()
                .id(entity.getId())
                .email(new Email(entity.getEmail()))
                .password(entity.getPassword())
                .fullName(entity.getFullName())
                .role(entity.getRole())
                .enabled(entity.isEnabled())
                .build();
    }

    private UserEntity toEntity(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .email(user.getEmail().getValue())
                .password(user.getPassword())
                .fullName(user.getFullName())
                .role(user.getRole())
                .enabled(user.isEnabled())
                .build();
    }
}
