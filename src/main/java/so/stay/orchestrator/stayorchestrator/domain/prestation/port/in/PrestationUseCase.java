package so.stay.orchestrator.stayorchestrator.domain.prestation.port.in;

import so.stay.orchestrator.stayorchestrator.domain.prestation.model.Prestation;
import so.stay.orchestrator.stayorchestrator.domain.prestation.model.PrestationType;

import java.util.List;

public interface PrestationUseCase {
    List<Prestation> getAllPrestations();
    Prestation getPrestationById(Long id);
    List<Prestation> getPrestationsByType(PrestationType type);
    List<Prestation> getPrestationsByCity(String city);
    Prestation createPrestation(Prestation prestation);
    Prestation updatePrestation(Long id, Prestation prestation);
    void deletePrestation(Long id);
}
