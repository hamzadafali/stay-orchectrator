package so.stay.orchestrator.stayorchestrator.infrastructure.security.policy;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import so.stay.orchestrator.stayorchestrator.domain.prestation.model.Prestation;
import so.stay.orchestrator.stayorchestrator.domain.prestation.port.in.PrestationUseCase;
import so.stay.orchestrator.stayorchestrator.infrastructure.security.userdetails.StayUserDetails;

@Component("prestationSecurityPolicy")
public class PrestationSecurityPolicy {

    private final PrestationUseCase prestationUseCase;

    public PrestationSecurityPolicy(PrestationUseCase prestationUseCase) {
        this.prestationUseCase = prestationUseCase;
    }

    public boolean canRead(Authentication authentication) {
        return hasAnyRole(authentication, "VIEWER", "OPERATOR", "ADMIN");
    }

    public boolean canCreate(Authentication authentication) {
        return hasAnyRole(authentication, "OPERATOR", "ADMIN");
    }

    public boolean canUpdate(Long prestationId, Authentication authentication) {
        if (hasRole(authentication, "ADMIN")) {
            return true;
        }

        if (!hasRole(authentication, "OPERATOR")) {
            return false;
        }

        Prestation prestation = prestationUseCase.getPrestationById(prestationId);

        Long currentUserId = getCurrentUserId(authentication);

        return prestation.getOwnerId() != null
                && prestation.getOwnerId().equals(currentUserId);
    }

    public boolean canDelete(Authentication authentication) {
        return hasRole(authentication, "ADMIN");
    }

    private Long getCurrentUserId(Authentication authentication) {
        Object principal = authentication.getPrincipal();

        if (principal instanceof StayUserDetails userDetails) {
            return userDetails.getId();
        }

        return null;
    }

    private boolean hasRole(Authentication authentication, String role) {
        return authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_" + role));
    }

    private boolean hasAnyRole(Authentication authentication, String... roles) {
        for (String role : roles) {
            if (hasRole(authentication, role)) {
                return true;
            }
        }
        return false;
    }
}
