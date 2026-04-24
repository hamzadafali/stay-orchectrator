package so.stay.orchestrator.stayorchestrator.infrastructure.security.policy;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import so.stay.orchestrator.stayorchestrator.domain.riad.model.Riad;
import so.stay.orchestrator.stayorchestrator.domain.riad.port.in.RiadUseCase;
import so.stay.orchestrator.stayorchestrator.infrastructure.security.userdetails.StayUserDetails;

@Component("riadSecurityPolicy")
public class RiadSecurityPolicy {

    private final RiadUseCase riadUseCase;

    public RiadSecurityPolicy(RiadUseCase riadUseCase) {
        this.riadUseCase = riadUseCase;
    }

    public boolean canRead(Authentication authentication) {
        return hasAnyRole(authentication, "VIEWER", "OPERATOR", "ADMIN");
    }

    public boolean canCreate(Authentication authentication) {
        return hasAnyRole(authentication, "OPERATOR", "ADMIN");
    }

    public boolean canUpdate(Long riadId, Authentication authentication) {
        if (hasRole(authentication, "ADMIN")) {
            return true;
        }

        if (!hasRole(authentication, "OPERATOR")) {
            return false;
        }

        Riad riad = riadUseCase.getRiadById(riadId);

        Long currentUserId = getCurrentUserId(authentication);

        return riad.getOwnerId() != null
                && riad.getOwnerId().equals(currentUserId);
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
