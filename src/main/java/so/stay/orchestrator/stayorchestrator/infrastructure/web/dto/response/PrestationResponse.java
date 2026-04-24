package so.stay.orchestrator.stayorchestrator.infrastructure.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrestationResponse {
    private Long id;
    private Long ownerId;
    private String type;
    private String name;
    private String description;
    private BigDecimal basePrice;
    private String currency;
    private String city;
}
