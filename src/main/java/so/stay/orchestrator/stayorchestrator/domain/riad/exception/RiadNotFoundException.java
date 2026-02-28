package so.stay.orchestrator.stayorchestrator.domain.riad.exception;

public class RiadNotFoundException extends RuntimeException {
    public RiadNotFoundException(Long id) {
        super("Riad not found with id: " + id);
    }
}
