package so.stay.orchestrator.stayorchestrator.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import so.stay.orchestrator.stayorchestrator.infrastructure.persistence.entity.PrestationEntity;
import so.stay.orchestrator.stayorchestrator.infrastructure.persistence.entity.PrestationTypeEntity;

import java.util.List;

@Repository
public interface JpaPrestationRepository extends JpaRepository<PrestationEntity, Long> {
    
    List<PrestationEntity> findByType(PrestationTypeEntity type);
    
    List<PrestationEntity> findByCity(String city);
}
