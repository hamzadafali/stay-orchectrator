package so.stay.orchestrator.stayorchestrator.application.auth.dto;

import java.util.List;

public record LoginResponse(String email, List<String> roles) {}
