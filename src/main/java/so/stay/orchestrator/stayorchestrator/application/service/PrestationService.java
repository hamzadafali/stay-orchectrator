package so.stay.orchestrator.stayorchestrator.application.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import so.stay.orchestrator.stayorchestrator.domain.prestation.exception.PrestationNotFoundException;
import so.stay.orchestrator.stayorchestrator.domain.prestation.model.Prestation;
import so.stay.orchestrator.stayorchestrator.domain.prestation.model.PrestationType;
import so.stay.orchestrator.stayorchestrator.domain.prestation.port.in.PrestationUseCase;
import so.stay.orchestrator.stayorchestrator.domain.prestation.port.out.PrestationRepository;

import java.util.List;

@Service
@Transactional
public class PrestationService implements PrestationUseCase {

    private final PrestationRepository prestationRepository;

    public PrestationService(PrestationRepository prestationRepository) {
        this.prestationRepository = prestationRepository;
    }

    @Override
    @Cacheable(value = "prestations", key = "'all'")
    @Transactional(readOnly = true)
    public List<Prestation> getAllPrestations() {
        return prestationRepository.findAll();
    }

    @Override
    @Cacheable(value = "prestations", key = "#id")
    @Transactional(readOnly = true)
    public Prestation getPrestationById(Long id) {
        return prestationRepository.findById(id)
                .orElseThrow(() -> new PrestationNotFoundException(id));
    }

    @Override
    @Cacheable(value = "prestations", key = "'type:' + #type")
    @Transactional(readOnly = true)
    public List<Prestation> getPrestationsByType(PrestationType type) {
        return prestationRepository.findByType(type);
    }

    @Override
    @Cacheable(value = "prestations", key = "'city:' + #city")
    @Transactional(readOnly = true)
    public List<Prestation> getPrestationsByCity(String city) {
        return prestationRepository.findByCity(city);
    }

    @Override
    @CacheEvict(value = "prestations", allEntries = true)
    public Prestation createPrestation(Prestation prestation) {
        return prestationRepository.save(prestation);
    }

    @Override
    @CacheEvict(value = "prestations", allEntries = true)
    public Prestation updatePrestation(Long id, Prestation prestation) {
        Prestation existing = getPrestationById(id);
        existing.updateDetails(prestation.getName(), prestation.getDescription());
        if (prestation.getBasePrice() != null) {
            existing.updatePrice(prestation.getBasePrice());
        }
        return prestationRepository.save(existing);
    }

    @Override
    @CacheEvict(value = "prestations", allEntries = true)
    public void deletePrestation(Long id) {
        if (!prestationRepository.findById(id).isPresent()) {
            throw new PrestationNotFoundException(id);
        }
        prestationRepository.deleteById(id);
    }
}
