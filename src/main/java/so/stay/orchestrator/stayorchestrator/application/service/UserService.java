package so.stay.orchestrator.stayorchestrator.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import so.stay.orchestrator.stayorchestrator.domain.shared.valueobject.Email;
import so.stay.orchestrator.stayorchestrator.domain.user.exception.UserNotFoundException;
import so.stay.orchestrator.stayorchestrator.domain.user.model.User;
import so.stay.orchestrator.stayorchestrator.domain.user.port.in.UserUseCase;
import so.stay.orchestrator.stayorchestrator.domain.user.port.UserRepository;

import java.util.List;

@Service
@Transactional
public class UserService implements UserUseCase {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByEmail(Email email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email.getValue()));
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User user) {
        User existing = getUserById(id);
        existing.updateProfile(user.getFullName());
        if (user.getPassword() != null) {
            existing.updatePassword(user.getPassword());
        }
        return userRepository.save(existing);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.findById(id).isPresent()) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }
}
