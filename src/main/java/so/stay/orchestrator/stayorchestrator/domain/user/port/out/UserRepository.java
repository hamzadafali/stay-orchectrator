package so.stay.orchestrator.stayorchestrator.domain.user.port.out;

import so.stay.orchestrator.stayorchestrator.domain.shared.valueobject.Email;
import so.stay.orchestrator.stayorchestrator.domain.user.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> findAll();
    Optional<User> findById(Long id);
    Optional<User> findByEmail(Email email);
    User save(User user);
    void deleteById(Long id);
}
