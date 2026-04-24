package so.stay.orchestrator.stayorchestrator.infrastructure.web.mapper;

import org.springframework.stereotype.Component;
import so.stay.orchestrator.stayorchestrator.domain.riad.model.Amenity;
import so.stay.orchestrator.stayorchestrator.domain.riad.model.Riad;
import so.stay.orchestrator.stayorchestrator.domain.shared.valueobject.Money;
import so.stay.orchestrator.stayorchestrator.infrastructure.web.dto.request.CreateRiadRequest;
import so.stay.orchestrator.stayorchestrator.infrastructure.web.dto.request.UpdateRiadRequest;
import so.stay.orchestrator.stayorchestrator.infrastructure.web.dto.response.RiadResponse;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RiadWebMapper {

    public RiadResponse toResponse(Riad riad) {
        List<String> amenityStrings = riad.getAmenities() != null
                ? riad.getAmenities().stream()
                        .map(Enum::name)
                        .collect(Collectors.toList())
                : Collections.emptyList();

        return RiadResponse.builder()
                .id(riad.getId())
                .ownerId(riad.getOwnerId())
                .name(riad.getName())
                .city(riad.getCity())
                .address(riad.getAddress())
                .description(riad.getDescription())
                .basePricePerNight(riad.getBasePricePerNight().getAmount())
                .currency(riad.getBasePricePerNight().getCurrency())
                .amenities(amenityStrings)
                .build();
    }

    public Riad toDomain(CreateRiadRequest request) {
        List<Amenity> amenities = request.getAmenities() != null
                ? request.getAmenities().stream()
                        .map(Amenity::valueOf)
                        .collect(Collectors.toList())
                : Collections.emptyList();

        return Riad.builder()
                .ownerId(request.getOwnerId())
                .name(request.getName())
                .city(request.getCity())
                .address(request.getAddress())
                .description(request.getDescription())
                .basePricePerNight(new Money(request.getBasePricePerNight(), request.getCurrency()))
                .amenities(amenities)
                .build();
    }

    public Riad toDomain(UpdateRiadRequest request) {
        List<Amenity> amenities = request.getAmenities() != null
                ? request.getAmenities().stream()
                        .map(Amenity::valueOf)
                        .collect(Collectors.toList())
                : Collections.emptyList();

        Money price = request.getBasePricePerNight() != null && request.getCurrency() != null
                ? new Money(request.getBasePricePerNight(), request.getCurrency())
                : null;

        return Riad.builder()
                .ownerId(request.getOwnerId())
                .name(request.getName())
                .city(request.getCity())
                .address(request.getAddress())
                .description(request.getDescription())
                .basePricePerNight(price)
                .amenities(amenities)
                .build();
    }
}
