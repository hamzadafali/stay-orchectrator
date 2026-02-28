package so.stay.orchestrator.stayorchestrator.domain.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import so.stay.orchestrator.stayorchestrator.domain.shared.valueobject.Email;

@Getter
@Builder
@AllArgsConstructor
public class User {
    private Long id;
    private Email email;
    private String hashedPassword;
    private String fullName;

    public void updateProfile(String fullName) {
        if (fullName != null && !fullName.isBlank()) {
            this.fullName = fullName;
        }
    }

    public void updatePassword(String newHashedPassword) {
        if (newHashedPassword == null || newHashedPassword.isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        this.hashedPassword = newHashedPassword;
    }
}
