package so.stay.orchestrator.stayorchestrator.infrastructure.web.mapper;

import org.springframework.stereotype.Component;
import so.stay.orchestrator.stayorchestrator.domain.shared.valueobject.Email;
import so.stay.orchestrator.stayorchestrator.domain.user.model.User;
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

    public User toDomain(CreateUserRequest request, String hashedPassword) {
        return User.builder()
                .email(new Email(request.getEmail()))
                .hashedPassword(hashedPassword)
                .fullName(request.getFullName())
                .build();
    }

    public User toDomain(UpdateUserRequest request, String hashedPassword) {
        return User.builder()
                .fullName(request.getFullName())
                .hashedPassword(hashedPassword)
                .build();
    }
}
