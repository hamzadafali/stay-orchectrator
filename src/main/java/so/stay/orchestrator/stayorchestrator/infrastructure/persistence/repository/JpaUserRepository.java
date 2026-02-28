package so.stay.orchestrator.stayorchestrator.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import so.stay.orchestrator.stayorchestrator.infrastructure.persistence.entity.UserEntity;

import java.util.Optional;

@Repository
public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {
    
    Optional<UserEntity> findByEmail(String email);
}
