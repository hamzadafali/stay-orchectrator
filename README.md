# Stay Orchestrator

## Prérequis

- Java 21
- Maven 3.6+
- Docker et Docker Compose

## Installation et Démarrage

### 1. Lancer les services avec Docker Compose

Démarrer PostgreSQL et Redis :

```bash
docker-compose up -d
```

Vérifier que les services sont en cours d'exécution :

```bash
docker-compose ps
```

Arrêter les services :

```bash
docker-compose down
```

### 2. Lancer l'application

Installer les dépendances et lancer l'application :

```bash
mvn spring-boot:run
```

Ou compiler et exécuter le JAR :

```bash
mvn clean package
java -jar target/stay-orchestrator-0.0.1-SNAPSHOT.jar
```

## Configuration

L'application utilise les services suivants :

- **PostgreSQL** : `localhost:5432`
  - Database: `travel`
  - User: `travel_user`
  - Password: `secret`

- **Redis** : `localhost:6379`
