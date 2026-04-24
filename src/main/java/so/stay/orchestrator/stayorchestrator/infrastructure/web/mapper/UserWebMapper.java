package so.stay.orchestrator.stayorchestrator.infrastructure.web.mapper;

import org.springframework.stereotype.Component;
import so.stay.orchestrator.stayorchestrator.domain.shared.valueobject.Email;
import so.stay.orchestrator.stayorchestrator.domain.user.model.User;
import so.stay.orchestrator.stayorchestrator.domain.user.model.UserRole;
import so.stay.orchestrator.stayorchestrator.infrastructure.web.dto.request.CreateUserRequest;
import so.stay.orchestrator.stayorchestrator.infrastructure.web.dto.request.UpdateUserRequest;
import so.stay.orchestrator.stayorchestrator.infrastructure.web.dto.response.UserResponse;

@Component
public class UserWebMapper {

    public UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail().getValue())
                .fullName(user.getFullName())
                .build();
    }

    public User toDomain(CreateUserRequest request, String password, UserRole role) {
        return User.builder()
                .email(new Email(request.getEmail()))
                .password(password)
                .fullName(request.getFullName())
                .role(role)
                .build();
    }

    public User toDomain(UpdateUserRequest request, String password) {
        return User.builder()
                .fullName(request.getFullName())
                .password(password)
                .build();
    }
}
