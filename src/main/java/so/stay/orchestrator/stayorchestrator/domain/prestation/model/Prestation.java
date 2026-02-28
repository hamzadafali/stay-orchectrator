package so.stay.orchestrator.stayorchestrator.domain.prestation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import so.stay.orchestrator.stayorchestrator.domain.shared.valueobject.Money;

@Getter
@Builder
@AllArgsConstructor
public class Prestation {
    private Long id;
    private PrestationType type;
    private String name;
    private String description;
    private Money basePrice;
    private String city;

    public void updateDetails(String name, String description) {
        if (name != null && !name.isBlank()) {
            this.name = name;
        }
        if (description != null) {
            this.description = description;
        }
    }

    public void updatePrice(Money newPrice) {
        if (newPrice == null) {
            throw new IllegalArgumentException("Price cannot be null");
        }
        this.basePrice = newPrice;
    }
}
