package so.stay.orchestrator.stayorchestrator.domain.riad.port.in;

import so.stay.orchestrator.stayorchestrator.domain.riad.model.Riad;
import so.stay.orchestrator.stayorchestrator.domain.shared.valueobject.Money;

import java.util.List;

public interface SearchRiadUseCase {
    List<Riad> searchByCity(String city);
    List<Riad> searchByCityAndPriceRange(String city, Money minPrice, Money maxPrice);
}
