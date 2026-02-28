package so.stay.orchestrator.stayorchestrator.infrastructure.web.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePrestationRequest {
    
    @Pattern(regexp = "TRANSPORT|GUIDE|RESTAURANT|ACTIVITY|SPA", message = "Invalid prestation type")
    private String type;
    
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    private String name;
    
    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    private String description;
    
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal basePrice;
    
    @Pattern(regexp = "MAD|EUR|USD", message = "Currency must be MAD, EUR, or USD")
    private String currency;
    
    private String city;
}
