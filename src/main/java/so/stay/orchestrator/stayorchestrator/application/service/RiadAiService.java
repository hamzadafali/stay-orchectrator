package so.stay.orchestrator.stayorchestrator.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;
import so.stay.orchestrator.stayorchestrator.domain.riad.model.Amenity;
import so.stay.orchestrator.stayorchestrator.domain.riad.model.Riad;
import so.stay.orchestrator.stayorchestrator.infrastructure.web.exception.AiGenerationException;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RiadAiService {

    private final ChatClient chatClient;

    private static final String DESCRIPTION_PROMPT = """
            Tu es un expert en marketing touristique pour le Maroc.
            
            Génère une description attractive et authentique pour ce riad :
            
            Nom: {name}
            Ville: {city}
            Adresse: {address}
            Prix par nuit: {pricePerNight} MAD
            Commodités: {amenities}
            
            Consignes:
            - Ton professionnel mais chaleureux
            - Mets en avant l'authenticité marocaine
            - Mentionne la localisation stratégique
            - Souligne le rapport qualité/prix
            - Maximum 150 mots
            - Pas de titre, juste le texte descriptif
            
            Description:
            """;

    public String generateDescription(Riad riad) {
        log.info("Generating AI description for riad: {}", riad.getName());

        try {
            PromptTemplate promptTemplate = new PromptTemplate(DESCRIPTION_PROMPT);

            Map<String, Object> variables = Map.of(
                    "name", riad.getName(),
                    "city", riad.getCity(),
                    "address", riad.getAddress(),
                    "pricePerNight", riad.getBasePricePerNight(),
                    "amenities", String.join(", ", riad.getAmenities().stream()
                            .map(Amenity::name)
                            .collect(Collectors.joining(", ")))
            );

            String response = chatClient.prompt(promptTemplate.create(variables))
                    .call()
                    .content();

            log.info("AI description generated successfully");
            return response.trim();

        } catch (Exception e) {
            log.error("Error generating AI description for riad {}", riad.getId(), e);
            throw new AiGenerationException("Failed to generate description", e);
        }
    }
}
