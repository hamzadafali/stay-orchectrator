package so.stay.orchestrator.stayorchestrator.infrastructure.security.audit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SecurityAuditService {

    public void auditRiadDeletion(Long riadId, Authentication authentication) {
        log.warn(
                "SECURITY_AUDIT action=DELETE_RIAD riadId={} user={} authorities={}",
                riadId,
                authentication.getName(),
                authentication.getAuthorities()
        );
    }

    public void auditPrestationDeletion(Long prestationId, Authentication authentication) {
        log.warn(
                "SECURITY_AUDIT action=DELETE_PRESTATION prestationId={} user={} authorities={}",
                prestationId,
                authentication.getName(),
                authentication.getAuthorities()
        );
    }
}
