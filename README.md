# Stay Orchestrator

Backend Spring Boot pour l'orchestration d'une application "Stay".
Ce dépôt contient actuellement le **socle technique** (Spring Boot, JPA, PostgreSQL, Redis, OpenAPI) et la configuration Docker des services. Les endpoints métiers ne sont **pas encore implémentés**.

## Stack
- **Java 17**
- **Spring Boot 4.0.1** (d'après `pom.xml`)
- **Spring Web MVC**, **Spring Data JPA**, **Validation**
- **PostgreSQL 15** (docker-compose)
- **Redis 7** (docker-compose)
- **OpenAPI/Swagger** (springdoc)
- **Lombok**

## Prérequis
- JDK **17**
- Maven **3.6+**
- Docker + Docker Compose

## Démarrage rapide

1) Démarrer PostgreSQL et Redis :

```bash
docker-compose up -d
```

2) Lancer l'application :

```bash
mvn clean spring-boot:run
```

L'application démarre sur : `http://localhost:8080`

## Configuration
La configuration par défaut est dans `src/main/resources/application.properties`.

### Base de données PostgreSQL
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/travel
spring.datasource.username=travel_user
spring.datasource.password=secret
```

### Redis
```properties
spring.cache.type=redis
spring.data.redis.host=localhost
spring.data.redis.port=6379
```

### Swagger / OpenAPI
```properties
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
```

Accès :
- Swagger UI : `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON : `http://localhost:8080/api-docs`

## Structure du projet (actuelle)

```
src/main/java/so/stay/orchestrator/stayorchestrator/
└── StayOrchestratorApplication.java
```

## Tests
```bash
mvn test
```

## Services Docker
Le fichier `docker-compose.yml` démarre :
- PostgreSQL 15 (DB: `travel`, user: `travel_user`, password: `secret`)
- Redis 7

## Notes
- Une dépendance H2 est présente dans `pom.xml`, mais aucune configuration H2 n'est fournie par défaut.
- Les endpoints métiers (riads, prestations, utilisateurs, etc.) ne sont pas encore codés dans ce dépôt.

## Roadmap suggérée
- Définir le modèle métier et les entités JPA
- Ajouter les controllers REST + DTOs
- Mettre en place la validation et la gestion d'erreurs
- Ajouter tests unitaires et d'intégration

## Licence
MIT
