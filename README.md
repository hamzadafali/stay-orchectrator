# Stay Orchestrator - Clean Architecture

API REST professionnelle pour la gestion de réservations de riads, prestations et utilisateurs au Maroc.

## 📋 Architecture

Ce projet suit les principes de **Clean Architecture** (Architecture Hexagonale) :

```
src/main/java/so/stay/orchestrator/stayorchestrator/
├── domain/                          # Couche métier (aucune dépendance externe)
│   ├── riad/
│   │   ├── model/                  # Entités métier pures
│   │   ├── port/
│   │   │   ├── in/                 # Interfaces use cases
│   │   │   └── out/                # Interfaces repositories
│   │   └── exception/              # Exceptions métier
│   ├── prestation/
│   ├── user/
│   └── shared/
│       └── valueobject/            # Value Objects (Email, Money)
│
├── application/                     # Orchestration des use cases
│   └── service/                    # Implémentation des use cases
│
└── infrastructure/                  # Détails techniques
    ├── persistence/                # Couche données
    │   ├── entity/                 # Entités JPA
    │   ├── repository/             # JPA Repositories
    │   └── adapter/                # Adaptateurs domain ↔ persistence
    ├── web/                        # Couche présentation
    │   ├── controller/             # Controllers REST
    │   ├── dto/                    # DTOs request/response
    │   ├── mapper/                 # Mappers domain ↔ DTO
    │   └── exception/              # Gestion globale des erreurs
    └── config/                     # Configuration Spring
```

## 🚀 Prérequis

- **Java 17** ou supérieur
- **Maven 3.6+**
- **Docker** et **Docker Compose**
- **PostgreSQL 15**
- **Redis 7**

## ⚙️ Installation et Démarrage

### 1. Démarrer les services (PostgreSQL + Redis)

```bash
docker-compose up -d
```

Vérifier que les services sont actifs :
```bash
docker-compose ps
```

### 2. Lancer l'application

```bash
# Compiler et lancer
mvn clean spring-boot:run

# Ou compiler et exécuter le JAR
mvn clean package
java -jar target/stay-orchestrator-0.0.1-SNAPSHOT.jar
```

L'application démarre sur **http://localhost:8080**

### 3. Accéder à la documentation Swagger

- **Swagger UI** : http://localhost:8080/swagger-ui.html
- **API Docs (JSON)** : http://localhost:8080/api-docs

## 📡 API Endpoints

### 🏠 Riads

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| GET | `/api/riads` | Liste tous les riads |
| GET | `/api/riads/{id}` | Récupère un riad par ID |
| GET | `/api/riads/search?city=Marrakech&minPrice=100&maxPrice=500&currency=MAD` | Recherche de riads |
| POST | `/api/riads` | Créer un nouveau riad |
| PUT | `/api/riads/{id}` | Mettre à jour un riad |
| DELETE | `/api/riads/{id}` | Supprimer un riad |

#### Exemple : Créer un riad

```bash
curl -X POST http://localhost:8080/api/riads \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Riad Dar Zahra",
    "city": "Marrakech",
    "address": "Medina, Derb Sidi Bouamar",
    "description": "Magnifique riad traditionnel au cœur de la médina",
    "basePricePerNight": 150.00,
    "currency": "MAD",
    "amenities": ["WIFI", "POOL", "HAMMAM", "TERRACE"]
  }'
```

#### Exemple : Rechercher des riads

```bash
curl "http://localhost:8080/api/riads/search?city=Marrakech&minPrice=100&maxPrice=300&currency=MAD"
```

### 🎯 Prestations

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| GET | `/api/prestations` | Liste toutes les prestations |
| GET | `/api/prestations?type=TRANSPORT` | Filtrer par type |
| GET | `/api/prestations?city=Marrakech` | Filtrer par ville |
| GET | `/api/prestations/{id}` | Récupère une prestation |
| POST | `/api/prestations` | Créer une prestation |
| PUT | `/api/prestations/{id}` | Mettre à jour |
| DELETE | `/api/prestations/{id}` | Supprimer |

**Types de prestations** : `TRANSPORT`, `GUIDE`, `RESTAURANT`, `ACTIVITY`, `SPA`

#### Exemple : Créer une prestation

```bash
curl -X POST http://localhost:8080/api/prestations \
  -H "Content-Type: application/json" \
  -d '{
    "type": "GUIDE",
    "name": "Visite guidée de la Médina",
    "description": "Tour complet de 3h dans la médina historique",
    "basePrice": 250.00,
    "currency": "MAD",
    "city": "Marrakech"
  }'
```

### 👤 Utilisateurs

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| GET | `/api/users` | Liste tous les utilisateurs |
| GET | `/api/users/{id}` | Récupère un utilisateur |
| POST | `/api/users` | Créer un utilisateur |
| PUT | `/api/users/{id}` | Mettre à jour |
| DELETE | `/api/users/{id}` | Supprimer |

#### Exemple : Créer un utilisateur

```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "securePassword123",
    "fullName": "Ahmed El Mansouri"
  }'
```

## 🔧 Configuration

### Base de données (PostgreSQL)

```properties
# application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/travel
spring.datasource.username=travel_user
spring.datasource.password=secret
```

### Cache (Redis)

```properties
spring.cache.type=redis
spring.data.redis.host=localhost
spring.data.redis.port=6379
```

Les méthodes suivantes utilisent le cache Redis :
- `GET /api/riads` → Cache key: `riads::all`
- `GET /api/riads/{id}` → Cache key: `riads::{id}`
- `GET /api/riads/search?city=X` → Cache key: `riads::city:X`
- `GET /api/prestations` → Cache key: `prestations::all`

## 🧪 Tests

```bash
# Exécuter tous les tests
mvn test

# Avec coverage
mvn test jacoco:report
```

## 📦 Build

```bash
# Build sans tests
mvn clean package -DskipTests

# Build complet
mvn clean package
```

## 🎯 Bonnes pratiques implémentées

✅ **Séparation stricte des couches** (Domain, Application, Infrastructure)
✅ **Dépendances inversées** (Domain ne dépend de rien)
✅ **Value Objects** (Email, Money) pour la validation
✅ **Mapping explicite** à chaque frontière
✅ **Cache Redis** avec @Cacheable
✅ **Validation Bean** avec @Valid
✅ **Gestion centralisée des erreurs** (@ControllerAdvice)
✅ **Documentation Swagger/OpenAPI**
✅ **Transactions** avec @Transactional
✅ **DTOs séparés** (Request/Response)

## 📊 Gestion des erreurs

Toutes les erreurs retournent un JSON standardisé :

```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Riad not found with id: 123",
  "path": "/api/riads/123"
}
```

Codes HTTP utilisés :
- **200** : Succès
- **201** : Créé
- **204** : Supprimé (No Content)
- **400** : Erreur de validation
- **404** : Ressource non trouvée
- **500** : Erreur serveur

## 🛑 Arrêter les services

```bash
# Arrêter l'application Spring Boot
Ctrl + C

# Arrêter Docker Compose
docker-compose down
```

## 📝 Logs

Les logs sont configurés dans `application.properties` :

```properties
logging.level.so.stay.orchestrator=DEBUG
logging.level.org.hibernate.SQL=DEBUG
```

## 🤝 Contribution

Ce projet suit les principes SOLID et Clean Architecture. Toute contribution doit respecter cette structure.

## 📄 Licence

MIT License
