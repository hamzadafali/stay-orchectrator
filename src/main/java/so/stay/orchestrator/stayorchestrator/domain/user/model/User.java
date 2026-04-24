package so.stay.orchestrator.stayorchestrator.domain.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import so.stay.orchestrator.stayorchestrator.domain.shared.valueobject.Email;
import so.stay.orchestrator.stayorchestrator.domain.user.model.UserRole;

@Getter
@Builder
@AllArgsConstructor
public class User {
    private Long id;
    private Email email;
    private String password;
    private String fullName;
    @Builder.Default
    private UserRole role = UserRole.OPERATOR;
    @Builder.Default
    private boolean enabled = true;

    public void updateProfile(String fullName) {
        if (fullName != null && !fullName.isBlank()) {
            this.fullName = fullName;
        }
    }

    public void updatePassword(String newPassword) {
        if (newPassword == null || newPassword.isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        this.password = newPassword;
    }
}
