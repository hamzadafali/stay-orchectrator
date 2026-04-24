package so.stay.orchestrator.stayorchestrator.infrastructure.persistence.adapter;

import org.springframework.stereotype.Component;
import so.stay.orchestrator.stayorchestrator.domain.prestation.model.Prestation;
import so.stay.orchestrator.stayorchestrator.domain.prestation.model.PrestationType;
import so.stay.orchestrator.stayorchestrator.domain.prestation.port.out.PrestationRepository;
import so.stay.orchestrator.stayorchestrator.domain.shared.valueobject.Money;
import so.stay.orchestrator.stayorchestrator.infrastructure.persistence.entity.PrestationEntity;
import so.stay.orchestrator.stayorchestrator.infrastructure.persistence.entity.PrestationTypeEntity;
import so.stay.orchestrator.stayorchestrator.infrastructure.persistence.entity.UserEntity;
import so.stay.orchestrator.stayorchestrator.infrastructure.persistence.repository.JpaPrestationRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PrestationRepositoryAdapter implements PrestationRepository {

    private final JpaPrestationRepository jpaPrestationRepository;

    public PrestationRepositoryAdapter(JpaPrestationRepository jpaPrestationRepository) {
        this.jpaPrestationRepository = jpaPrestationRepository;
    }

    @Override
    public List<Prestation> findAll() {
        return jpaPrestationRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Prestation> findById(Long id) {
        return jpaPrestationRepository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public List<Prestation> findByType(PrestationType type) {
        PrestationTypeEntity entityType = PrestationTypeEntity.valueOf(type.name());
        return jpaPrestationRepository.findByType(entityType).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Prestation> findByCity(String city) {
        return jpaPrestationRepository.findByCity(city).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Prestation save(Prestation prestation) {
        PrestationEntity entity = toEntity(prestation);
        PrestationEntity saved = jpaPrestationRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public void deleteById(Long id) {
        jpaPrestationRepository.deleteById(id);
    }

    private Prestation toDomain(PrestationEntity entity) {
        return Prestation.builder()
                .id(entity.getId())
                .ownerId(entity.getOwner() != null ? entity.getOwner().getId() : null)
                .type(PrestationType.valueOf(entity.getType().name()))
                .name(entity.getName())
                .description(entity.getDescription())
                .basePrice(new Money(entity.getBasePrice(), entity.getCurrency() != null ? entity.getCurrency() : "MAD"))
                .city(entity.getCity())
                .build();
    }

    private PrestationEntity toEntity(Prestation prestation) {
        return PrestationEntity.builder()
                .id(prestation.getId())
                .owner(prestation.getOwnerId() != null ? UserEntity.builder().id(prestation.getOwnerId()).build() : null)
                .type(PrestationTypeEntity.valueOf(prestation.getType().name()))
                .name(prestation.getName())
                .description(prestation.getDescription())
                .basePrice(prestation.getBasePrice().getAmount())
                .currency(prestation.getBasePrice().getCurrency())
                .city(prestation.getCity())
                .build();
    }
}
