package so.stay.orchestrator.stayorchestrator.infrastructure.web.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import so.stay.orchestrator.stayorchestrator.domain.prestation.model.Prestation;
import so.stay.orchestrator.stayorchestrator.domain.prestation.model.PrestationType;
import so.stay.orchestrator.stayorchestrator.domain.prestation.port.in.PrestationUseCase;
import so.stay.orchestrator.stayorchestrator.infrastructure.web.dto.request.CreatePrestationRequest;
import so.stay.orchestrator.stayorchestrator.infrastructure.web.dto.request.UpdatePrestationRequest;
import so.stay.orchestrator.stayorchestrator.infrastructure.web.dto.response.PrestationResponse;
import so.stay.orchestrator.stayorchestrator.infrastructure.web.mapper.PrestationWebMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/prestations")
public class PrestationController {

    private final PrestationUseCase prestationUseCase;
    private final PrestationWebMapper prestationMapper;

    public PrestationController(PrestationUseCase prestationUseCase, PrestationWebMapper prestationMapper) {
        this.prestationUseCase = prestationUseCase;
        this.prestationMapper = prestationMapper;
    }

    @GetMapping
    public ResponseEntity<List<PrestationResponse>> getAllPrestations(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String city
    ) {
        List<Prestation> prestations;
        
        if (type != null) {
            prestations = prestationUseCase.getPrestationsByType(PrestationType.valueOf(type));
        } else if (city != null) {
            prestations = prestationUseCase.getPrestationsByCity(city);
        } else {
            prestations = prestationUseCase.getAllPrestations();
        }

        List<PrestationResponse> responses = prestations.stream()
                .map(prestationMapper::toResponse)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrestationResponse> getPrestationById(@PathVariable Long id) {
        Prestation prestation = prestationUseCase.getPrestationById(id);
        return ResponseEntity.ok(prestationMapper.toResponse(prestation));
    }

    @PostMapping
    public ResponseEntity<PrestationResponse> createPrestation(@Valid @RequestBody CreatePrestationRequest request) {
        Prestation prestation = prestationMapper.toDomain(request);
        Prestation created = prestationUseCase.createPrestation(prestation);
        return ResponseEntity.status(HttpStatus.CREATED).body(prestationMapper.toResponse(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrestationResponse> updatePrestation(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePrestationRequest request
    ) {
        Prestation prestation = prestationMapper.toDomain(request);
        Prestation updated = prestationUseCase.updatePrestation(id, prestation);
        return ResponseEntity.ok(prestationMapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrestation(@PathVariable Long id) {
        prestationUseCase.deletePrestation(id);
        return ResponseEntity.noContent().build();
    }
}
