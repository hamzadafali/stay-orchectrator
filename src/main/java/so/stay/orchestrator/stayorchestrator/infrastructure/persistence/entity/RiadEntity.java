package so.stay.orchestrator.stayorchestrator.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "riads")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RiadEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String city;
    private String address;
    
    @Column(length = 2000)
    private String description;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal basePricePerNight;
    
    private String currency;
    
    @Column(length = 500)
    private String amenities; // Stored as comma-separated values
}
