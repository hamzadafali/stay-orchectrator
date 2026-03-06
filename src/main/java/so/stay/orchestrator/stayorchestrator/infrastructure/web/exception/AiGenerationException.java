package so.stay.orchestrator.stayorchestrator.infrastructure.web.exception;

public class AiGenerationException extends RuntimeException {
    public AiGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}
