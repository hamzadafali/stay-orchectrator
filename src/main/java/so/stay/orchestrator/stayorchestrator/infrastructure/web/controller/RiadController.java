package so.stay.orchestrator.stayorchestrator.infrastructure.web.controller;

import jakarta.validation.Valid;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import so.stay.orchestrator.stayorchestrator.application.service.RiadAiService;
import so.stay.orchestrator.stayorchestrator.domain.riad.model.Riad;
import so.stay.orchestrator.stayorchestrator.domain.riad.port.in.RiadUseCase;
import so.stay.orchestrator.stayorchestrator.domain.riad.port.in.SearchRiadUseCase;
import so.stay.orchestrator.stayorchestrator.domain.shared.valueobject.Money;
import so.stay.orchestrator.stayorchestrator.infrastructure.web.dto.request.CreateRiadRequest;
import so.stay.orchestrator.stayorchestrator.infrastructure.web.dto.request.UpdateRiadRequest;
import so.stay.orchestrator.stayorchestrator.infrastructure.web.dto.response.RiadResponse;
import so.stay.orchestrator.stayorchestrator.infrastructure.web.mapper.RiadWebMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/riads")
@Slf4j
public class RiadController {

    private final RiadUseCase riadUseCase;
    private final SearchRiadUseCase searchRiadUseCase;
    private final RiadAiService riadAiService;
    private final RiadWebMapper riadMapper;

    public RiadController(RiadUseCase riadUseCase, SearchRiadUseCase searchRiadUseCase, RiadAiService riadAiService, RiadWebMapper riadMapper) {
        this.riadUseCase = riadUseCase;
        this.searchRiadUseCase = searchRiadUseCase;
        this.riadAiService = riadAiService;
        this.riadMapper = riadMapper;
    }

    @GetMapping
    public ResponseEntity<List<RiadResponse>> getAllRiads() {
        List<RiadResponse> riads = riadUseCase.getAllRiads().stream().map(riadMapper::toResponse).collect(Collectors.toList());
        return ResponseEntity.ok(riads);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RiadResponse> getRiadById(@PathVariable Long id) {
        Riad riad = riadUseCase.getRiadById(id);
        return ResponseEntity.ok(riadMapper.toResponse(riad));
    }

    @GetMapping("/search")
    public ResponseEntity<List<RiadResponse>> searchRiads(@RequestParam(required = false) String city, @RequestParam(required = false) BigDecimal minPrice, @RequestParam(required = false) BigDecimal maxPrice, @RequestParam(defaultValue = "MAD") String currency) {
        List<Riad> riads;

        if (city != null && minPrice != null && maxPrice != null) {
            Money min = new Money(minPrice, currency);
            Money max = new Money(maxPrice, currency);
            riads = searchRiadUseCase.searchByCityAndPriceRange(city, min, max);
        } else if (city != null) {
            riads = searchRiadUseCase.searchByCity(city);
        } else {
            riads = riadUseCase.getAllRiads();
        }

        List<RiadResponse> responses = riads.stream().map(riadMapper::toResponse).collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<RiadResponse> createRiad(@Valid @RequestBody CreateRiadRequest request) {
        Riad riad = riadMapper.toDomain(request);
        Riad created = riadUseCase.createRiad(riad);
        return ResponseEntity.status(HttpStatus.CREATED).body(riadMapper.toResponse(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RiadResponse> updateRiad(@PathVariable Long id, @Valid @RequestBody UpdateRiadRequest request) {
        Riad riad = riadMapper.toDomain(request);
        Riad updated = riadUseCase.updateRiad(id, riad);
        return ResponseEntity.ok(riadMapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRiad(@PathVariable Long id) {
        riadUseCase.deleteRiad(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Génère ET sauvegarde automatiquement la description
     */
    @PostMapping("/{id}/auto-describe")
    public ResponseEntity<Riad> autoDescribe(@PathVariable Long id) {

        log.info("Request to auto-describe riad {}", id);

        Riad riad = riadUseCase.getRiadById(id);
        String aiDescription = riadAiService.generateDescription(riad);

        // Mise à jour de la description
        riad.setDescription(aiDescription);
        Riad updated = riadUseCase.updateRiad(id, riad);

        return ResponseEntity.ok(updated);
    }

    @Data
    @Builder
    static class DescriptionResponse {
           private Long riadId;
           private String generatedDescription;
           private String message;
    }
}
