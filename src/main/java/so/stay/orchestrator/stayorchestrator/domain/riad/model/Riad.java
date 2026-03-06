package so.stay.orchestrator.stayorchestrator.domain.riad.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import so.stay.orchestrator.stayorchestrator.domain.shared.valueobject.Money;

import java.util.List;

@Getter
@Builder
@Setter
@AllArgsConstructor
public class Riad {
    private Long id;
    private String name;
    private String city;
    private String address;
    private String description;
    private Money basePricePerNight;
    private List<Amenity> amenities;

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
        this.basePricePerNight = newPrice;
    }
}
