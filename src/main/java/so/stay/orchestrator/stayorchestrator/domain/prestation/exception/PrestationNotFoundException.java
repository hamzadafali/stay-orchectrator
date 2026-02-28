package so.stay.orchestrator.stayorchestrator.domain.prestation.exception;

public class PrestationNotFoundException extends RuntimeException {
    public PrestationNotFoundException(Long id) {
        super("Prestation not found with id: " + id);
    }
}
