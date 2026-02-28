package so.stay.orchestrator.stayorchestrator.infrastructure.web.mapper;

import org.springframework.stereotype.Component;
import so.stay.orchestrator.stayorchestrator.domain.prestation.model.Prestation;
import so.stay.orchestrator.stayorchestrator.domain.prestation.model.PrestationType;
import so.stay.orchestrator.stayorchestrator.domain.shared.valueobject.Money;
import so.stay.orchestrator.stayorchestrator.infrastructure.web.dto.request.CreatePrestationRequest;
import so.stay.orchestrator.stayorchestrator.infrastructure.web.dto.request.UpdatePrestationRequest;
import so.stay.orchestrator.stayorchestrator.infrastructure.web.dto.response.PrestationResponse;

@Component
public class PrestationWebMapper {

    public PrestationResponse toResponse(Prestation prestation) {
        return PrestationResponse.builder()
                .id(prestation.getId())
                .type(prestation.getType().name())
                .name(prestation.getName())
                .description(prestation.getDescription())
                .basePrice(prestation.getBasePrice().getAmount())
                .currency(prestation.getBasePrice().getCurrency())
                .city(prestation.getCity())
                .build();
    }

    public Prestation toDomain(CreatePrestationRequest request) {
        return Prestation.builder()
                .type(PrestationType.valueOf(request.getType()))
                .name(request.getName())
                .description(request.getDescription())
                .basePrice(new Money(request.getBasePrice(), request.getCurrency()))
                .city(request.getCity())
                .build();
    }

    public Prestation toDomain(UpdatePrestationRequest request) {
        Money price = request.getBasePrice() != null && request.getCurrency() != null
                ? new Money(request.getBasePrice(), request.getCurrency())
                : null;

        return Prestation.builder()
                .type(request.getType() != null ? PrestationType.valueOf(request.getType()) : null)
                .name(request.getName())
                .description(request.getDescription())
                .basePrice(price)
                .city(request.getCity())
                .build();
    }
}
