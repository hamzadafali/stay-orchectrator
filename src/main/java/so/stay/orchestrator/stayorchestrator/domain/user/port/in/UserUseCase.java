package so.stay.orchestrator.stayorchestrator.domain.user.port.in;

import so.stay.orchestrator.stayorchestrator.domain.shared.valueobject.Email;
import so.stay.orchestrator.stayorchestrator.domain.user.model.User;

import java.util.List;

public interface UserUseCase {
    List<User> getAllUsers();
    User getUserById(Long id);
    User getUserByEmail(Email email);
    User createUser(User user);
    User updateUser(Long id, User user);
    void deleteUser(Long id);
}
