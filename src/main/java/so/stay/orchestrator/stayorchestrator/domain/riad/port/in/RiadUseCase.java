package so.stay.orchestrator.stayorchestrator.domain.riad.port.in;

import so.stay.orchestrator.stayorchestrator.domain.riad.model.Riad;

import java.util.List;

public interface RiadUseCase {
    List<Riad> getAllRiads();
    Riad getRiadById(Long id);
    Riad createRiad(Riad riad);
    Riad updateRiad(Long id, Riad riad);
    void deleteRiad(Long id);
}
