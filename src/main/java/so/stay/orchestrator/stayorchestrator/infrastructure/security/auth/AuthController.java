package so.stay.orchestrator.stayorchestrator.infrastructure.security.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import so.stay.orchestrator.stayorchestrator.application.auth.AuthenticationService;
import so.stay.orchestrator.stayorchestrator.application.auth.dto.LoginRequest;
import so.stay.orchestrator.stayorchestrator.application.auth.dto.LoginResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request,
                                               HttpServletRequest servletRequest) {
        String remoteAddress = servletRequest.getRemoteAddr();
        try {
            LoginResponse response = authenticationService.authenticate(request, remoteAddress);
            return ResponseEntity.ok(response);
        } catch (IllegalStateException exception) {
            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, exception.getMessage());
        }
    }
}
