package so.stay.orchestrator.stayorchestrator.application.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import so.stay.orchestrator.stayorchestrator.domain.riad.exception.RiadNotFoundException;
import so.stay.orchestrator.stayorchestrator.domain.riad.model.Riad;
import so.stay.orchestrator.stayorchestrator.domain.riad.port.in.RiadUseCase;
import so.stay.orchestrator.stayorchestrator.domain.riad.port.in.SearchRiadUseCase;
import so.stay.orchestrator.stayorchestrator.domain.riad.port.out.RiadRepository;
import so.stay.orchestrator.stayorchestrator.domain.shared.valueobject.Money;

import java.util.List;

@Service
@Transactional
public class RiadService implements RiadUseCase, SearchRiadUseCase {

    private final RiadRepository riadRepository;

    public RiadService(RiadRepository riadRepository) {
        this.riadRepository = riadRepository;
    }

    @Override
    @Cacheable(value = "riads", key = "'all'")
    @Transactional(readOnly = true)
    public List<Riad> getAllRiads() {
        return riadRepository.findAll();
    }

    @Override
    @Cacheable(value = "riads", key = "#id")
    @Transactional(readOnly = true)
    public Riad getRiadById(Long id) {
        return riadRepository.findById(id)
                .orElseThrow(() -> new RiadNotFoundException(id));
    }

    @Override
    @CacheEvict(value = "riads", allEntries = true)
    public Riad createRiad(Riad riad) {
        return riadRepository.save(riad);
    }

    @Override
    @CacheEvict(value = "riads", allEntries = true)
    public Riad updateRiad(Long id, Riad riad) {
        Riad existing = getRiadById(id);
        existing.updateDetails(riad.getName(), riad.getDescription());
        if (riad.getBasePricePerNight() != null) {
            existing.updatePrice(riad.getBasePricePerNight());
        }
        return riadRepository.save(existing);
    }

    @Override
    @CacheEvict(value = "riads", allEntries = true)
    public void deleteRiad(Long id) {
        if (!riadRepository.findById(id).isPresent()) {
            throw new RiadNotFoundException(id);
        }
        riadRepository.deleteById(id);
    }

    @Override
    @Cacheable(value = "riads", key = "'city:' + #city")
    @Transactional(readOnly = true)
    public List<Riad> searchByCity(String city) {
        return riadRepository.findByCity(city);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Riad> searchByCityAndPriceRange(String city, Money minPrice, Money maxPrice) {
        return riadRepository.searchByCityAndPriceRange(city, minPrice, maxPrice);
    }
}
