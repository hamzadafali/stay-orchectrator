package so.stay.orchestrator.stayorchestrator.domain.user.port;

import so.stay.orchestrator.stayorchestrator.domain.shared.valueobject.Email;
import so.stay.orchestrator.stayorchestrator.domain.user.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> findAll();
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    default Optional<User> findByEmail(Email email) {
        if (email == null) {
            return Optional.empty();
        }
        return findByEmail(email.getValue());
    }
    User save(User user);
    void deleteById(Long id);
}
