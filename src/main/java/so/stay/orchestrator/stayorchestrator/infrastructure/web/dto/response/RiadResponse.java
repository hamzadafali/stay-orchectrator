package so.stay.orchestrator.stayorchestrator.infrastructure.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RiadResponse {
    private Long id;
    private String name;
    private String city;
    private String address;
    private String description;
    private BigDecimal basePricePerNight;
    private String currency;
    private List<String> amenities;
}
