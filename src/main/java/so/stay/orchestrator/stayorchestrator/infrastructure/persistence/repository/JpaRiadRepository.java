package so.stay.orchestrator.stayorchestrator.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import so.stay.orchestrator.stayorchestrator.infrastructure.persistence.entity.RiadEntity;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface JpaRiadRepository extends JpaRepository<RiadEntity, Long> {
    
    List<RiadEntity> findByCity(String city);
    
    @Query("SELECT r FROM RiadEntity r WHERE r.city = :city AND r.basePricePerNight BETWEEN :minPrice AND :maxPrice")
    List<RiadEntity> searchByCityAndPriceRange(
            @Param("city") String city,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice
    );
}
