package so.stay.orchestrator.stayorchestrator.domain.prestation.port.out;

import so.stay.orchestrator.stayorchestrator.domain.prestation.model.Prestation;
import so.stay.orchestrator.stayorchestrator.domain.prestation.model.PrestationType;

import java.util.List;
import java.util.Optional;

public interface PrestationRepository {
    List<Prestation> findAll();
    Optional<Prestation> findById(Long id);
    List<Prestation> findByType(PrestationType type);
    List<Prestation> findByCity(String city);
    Prestation save(Prestation prestation);
    void deleteById(Long id);
}
