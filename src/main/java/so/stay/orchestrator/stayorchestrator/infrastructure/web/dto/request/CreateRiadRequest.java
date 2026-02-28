package so.stay.orchestrator.stayorchestrator.infrastructure.web.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRiadRequest {
    
    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    private String name;
    
    @NotBlank(message = "City is required")
    private String city;
    
    private String address;
    
    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    private String description;
    
    @NotNull(message = "Base price per night is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal basePricePerNight;
    
    @NotBlank(message = "Currency is required")
    @Pattern(regexp = "MAD|EUR|USD", message = "Currency must be MAD, EUR, or USD")
    private String currency;
    
    private List<String> amenities;
}
