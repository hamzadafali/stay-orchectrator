package so.stay.orchestrator.stayorchestrator.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "prestations")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrestationEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "owner_id")
    private UserEntity owner;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private PrestationTypeEntity type;

    @Column(nullable = false)
    private String name;
    
    @Column(length = 2000)
    private String description;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal basePrice;
    
    private String currency;
    private String city;
}
