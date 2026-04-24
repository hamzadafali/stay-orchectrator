package so.stay.orchestrator.stayorchestrator.infrastructure.persistence.adapter;

import org.springframework.stereotype.Component;
import so.stay.orchestrator.stayorchestrator.domain.riad.model.Amenity;
import so.stay.orchestrator.stayorchestrator.domain.riad.model.Riad;
import so.stay.orchestrator.stayorchestrator.domain.riad.port.out.RiadRepository;
import so.stay.orchestrator.stayorchestrator.domain.shared.valueobject.Money;
import so.stay.orchestrator.stayorchestrator.infrastructure.persistence.entity.RiadEntity;
import so.stay.orchestrator.stayorchestrator.infrastructure.persistence.entity.UserEntity;
import so.stay.orchestrator.stayorchestrator.infrastructure.persistence.repository.JpaRiadRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class RiadRepositoryAdapter implements RiadRepository {

    private final JpaRiadRepository jpaRiadRepository;

    public RiadRepositoryAdapter(JpaRiadRepository jpaRiadRepository) {
        this.jpaRiadRepository = jpaRiadRepository;
    }

    @Override
    public List<Riad> findAll() {
        return jpaRiadRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Riad> findById(Long id) {
        return jpaRiadRepository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public List<Riad> findByCity(String city) {
        return jpaRiadRepository.findByCity(city).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Riad> searchByCityAndPriceRange(String city, Money minPrice, Money maxPrice) {
        return jpaRiadRepository.searchByCityAndPriceRange(city, minPrice.getAmount(), maxPrice.getAmount())
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Riad save(Riad riad) {
        RiadEntity entity = toEntity(riad);
        RiadEntity saved = jpaRiadRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public void deleteById(Long id) {
        jpaRiadRepository.deleteById(id);
    }

    private Riad toDomain(RiadEntity entity) {
        List<Amenity> amenities = entity.getAmenities() != null && !entity.getAmenities().isEmpty()
                ? Arrays.stream(entity.getAmenities().split(","))
                        .map(String::trim)
                        .map(String::toUpperCase)
                        .map(Amenity::valueOf)
                        .collect(Collectors.toList())
                : Collections.emptyList();

        return Riad.builder()
                .id(entity.getId())
                .ownerId(entity.getOwner() != null ? entity.getOwner().getId() : null)
                .name(entity.getName())
                .city(entity.getCity())
                .address(entity.getAddress())
                .description(entity.getDescription())
                .basePricePerNight(new Money(entity.getBasePricePerNight(), entity.getCurrency() != null ? entity.getCurrency() : "MAD"))
                .amenities(amenities)
                .build();
    }

    private RiadEntity toEntity(Riad riad) {
        String amenitiesStr = riad.getAmenities() != null
                ? riad.getAmenities().stream()
                        .map(Enum::name)
                        .collect(Collectors.joining(","))
                : "";

        return RiadEntity.builder()
                .id(riad.getId())
                .owner(riad.getOwnerId() != null ? UserEntity.builder().id(riad.getOwnerId()).build() : null)
                .name(riad.getName())
                .city(riad.getCity())
                .address(riad.getAddress())
                .description(riad.getDescription())
                .basePricePerNight(riad.getBasePricePerNight().getAmount())
                .currency(riad.getBasePricePerNight().getCurrency())
                .amenities(amenitiesStr)
                .build();
    }
}
