package so.stay.orchestrator.stayorchestrator.domain.riad.port.out;

import so.stay.orchestrator.stayorchestrator.domain.riad.model.Riad;
import so.stay.orchestrator.stayorchestrator.domain.shared.valueobject.Money;

import java.util.List;
import java.util.Optional;

public interface RiadRepository {
    List<Riad> findAll();
    Optional<Riad> findById(Long id);
    List<Riad> findByCity(String city);
    List<Riad> searchByCityAndPriceRange(String city, Money minPrice, Money maxPrice);
    Riad save(Riad riad);
    void deleteById(Long id);
}
