# Stay Orchestrator

Backend Spring Boot pour piloter une offre de séjour autour de 3 domaines métier:
1. Gestion des **riads** (hébergements).
2. Gestion des **prestations** (transport, guide, activité, etc.).
3. Gestion des **utilisateurs**.

L’objectif est de fournir une API REST qui permet de créer le catalogue, le consulter et filtrer les offres pour construire une expérience de réservation.

## Objectif métier
Une personne qui opère la plateforme doit pouvoir:
1. Créer et maintenir des riads avec prix/nuit.
2. Créer et maintenir des prestations annexes par ville.
3. Gérer les utilisateurs de la plateforme.
4. Rechercher rapidement les offres selon la ville et le budget.

## Use cases métier (actuels)
1. **Publier un nouveau riad**
   L’opérateur crée un riad avec nom, ville, adresse, description, prix par nuit, devise et amenities.
2. **Trouver un riad à Marrakech dans une fourchette de prix**
   L’opérateur ou un service front appelle la recherche par ville + min/max pour récupérer uniquement les riads dans le budget.
3. **Lister les prestations d’une ville**
   L’opérateur filtre les prestations par ville pour proposer des services contextuels au séjour.
4. **Filtrer les prestations par type**
   Exemples: `GUIDE`, `TRANSPORT`, `SPA` pour afficher une catégorie précise.
5. **Créer un utilisateur**
   L’opérateur crée un compte avec email, mot de passe, nom complet.
6. **Mettre à jour profil ou mot de passe**
   Le profil utilisateur peut être modifié via `PUT /api/users/{id}`.
7. **Superviser la disponibilité API**
   Vérification via endpoint santé `GET /api/health`.

## Comment l’app fonctionne
Le projet suit une structure proche hexagonale:
1. `infrastructure/web/controller`: expose les endpoints REST.
2. `application/service`: implémente les cas d’usage métier.
3. `domain/*`: modèle métier (Riad, Prestation, User, Value Objects).
4. `infrastructure/persistence/*`: adaptation JPA vers PostgreSQL/H2.

Flux type:
1. Requête HTTP reçue par un controller.
2. Validation des DTOs (`@Valid` + contraintes).
3. Appel d’un use case/service.
4. Lecture/écriture via repository (JPA).
5. Mapping vers DTO de réponse.
6. Gestion centralisée des erreurs (`GlobalExceptionHandler`).

## Endpoints API

### Santé
- `GET /api/health`

### Riads
- `GET /api/riads`
- `GET /api/riads/{id}`
- `GET /api/riads/search?city=...&minPrice=...&maxPrice=...&currency=MAD`
- `POST /api/riads`
- `PUT /api/riads/{id}`
- `DELETE /api/riads/{id}`

### Prestations
- `GET /api/prestations`
- `GET /api/prestations?type=GUIDE`
- `GET /api/prestations?city=Marrakech`
- `GET /api/prestations/{id}`
- `POST /api/prestations`
- `PUT /api/prestations/{id}`
- `DELETE /api/prestations/{id}`

### Utilisateurs
- `GET /api/users`
- `GET /api/users/{id}`
- `POST /api/users`
- `PUT /api/users/{id}`
- `DELETE /api/users/{id}`

Swagger:
- `http://localhost:8080/swagger-ui.html`
- `http://localhost:8080/api-docs`

## Règles métier et validation
1. Prix strictement positifs.
2. Devises autorisées: `MAD`, `EUR`, `USD`.
3. Email au format valide.
4. Mot de passe minimum 8 caractères.
5. Type prestation autorisé: `TRANSPORT`, `GUIDE`, `RESTAURANT`, `ACTIVITY`, `SPA`.

En cas d’erreur:
1. `404` si ressource non trouvée.
2. `400` si validation invalide ou argument métier invalide.
3. `500` pour erreur inattendue.

## Cache et performance
1. Cache Redis actif par défaut pour riads et prestations.
2. Invalidation du cache lors des opérations create/update/delete.
3. Profil local possible sans Redis (`spring.cache.type=none`).

## Stack technique
- Java 17
- Spring Boot 4.0.1
- Spring Web MVC
- Spring Data JPA
- PostgreSQL 15
- Redis 7
- OpenAPI/Swagger (springdoc)
- Lombok

## Démarrage

### Option A: mode standard (PostgreSQL + Redis)
1. Lancer les services:
```bash
docker-compose up -d
```
2. Lancer l’application:
```bash
mvn clean spring-boot:run
```
3. API disponible sur `http://localhost:8080`

### Option B: mode local léger (H2 en mémoire, sans cache Redis)
```bash
mvn clean spring-boot:run -Dspring-boot.run.profiles=local
```

## Configuration principale
Fichier: `src/main/resources/application.properties`

- DB PostgreSQL: `travel` / `travel_user` / `secret`
- Redis: `localhost:6379`
- `spring.jpa.hibernate.ddl-auto=update`

Profil local: `src/main/resources/application-local.properties` (H2 + cache désactivé).

## Limitations connues
1. Le hash du mot de passe est un placeholder (`"hashed_" + password`) et doit être remplacé par BCrypt.
2. `ChatController` est présent mais non implémenté.
3. Les tests sont encore minimaux.

## Commandes utiles
```bash
mvn test
```

## Licence
MIT

