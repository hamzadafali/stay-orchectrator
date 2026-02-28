package so.stay.orchestrator.stayorchestrator.infrastructure.web.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import so.stay.orchestrator.stayorchestrator.domain.user.model.User;
import so.stay.orchestrator.stayorchestrator.domain.user.port.in.UserUseCase;
import so.stay.orchestrator.stayorchestrator.infrastructure.web.dto.request.CreateUserRequest;
import so.stay.orchestrator.stayorchestrator.infrastructure.web.dto.request.UpdateUserRequest;
import so.stay.orchestrator.stayorchestrator.infrastructure.web.dto.response.UserResponse;
import so.stay.orchestrator.stayorchestrator.infrastructure.web.mapper.UserWebMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserUseCase userUseCase;
    private final UserWebMapper userMapper;

    public UserController(UserUseCase userUseCase, UserWebMapper userMapper) {
        this.userUseCase = userUseCase;
        this.userMapper = userMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userUseCase.getAllUsers().stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        User user = userUseCase.getUserById(id);
        return ResponseEntity.ok(userMapper.toResponse(user));
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        // TODO: In a real app, hash the password using BCrypt or similar
        String hashedPassword = hashPassword(request.getPassword());
        User user = userMapper.toDomain(request, hashedPassword);
        User created = userUseCase.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toResponse(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest request
    ) {
        String hashedPassword = request.getPassword() != null ? hashPassword(request.getPassword()) : null;
        User user = userMapper.toDomain(request, hashedPassword);
        User updated = userUseCase.updateUser(id, user);
        return ResponseEntity.ok(userMapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userUseCase.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // TODO: Replace with proper password hashing (BCrypt)
    private String hashPassword(String password) {
        return "hashed_" + password;
    }
}
