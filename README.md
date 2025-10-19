# Card Management Service

A Spring Boot application for managing card creation, built with Java 21, PostgreSQL, Hibernate, and Querydsl. The
service includes REST APIs, Swagger documentation, and Docker support for easy setup.

## Features

- Add new clients with attributes firstName, lastName, OIB, and cardStatus to the database.
- Fetch a client by OIB; returns client data if exists. If the client exists, automatically forward their information to
  the external RESTful API endpoint.
- Remove a client from the database by OIB.
- API documentation available via **Swagger UI**
- Docker Compose (includes PostgreSQL and Kafka)

### Observability

- Centralized logging using AOP:
    - Logs method entry and exit for all service methods.
    - Measures execution time for performance insight.
    - Captures exceptions with stack traces.
- Auditing:
    - Tracks changes in the database (created/modified by).
    - Currently uses a default system user for auditing.
- Error handling with HTTP responses and translated messages.

---

## Table of Contents

- [Getting Started](#getting-started)
- [Build and Run](#build-and-run)
- [Docker](#docker)
- [API Documentation](#api-documentation)
- [Testing](#testing)
- [Configuration](#configuration)
- [Technologies](#technologies)

---

## Getting Started

### Clone the repository

```bash
git clone https://github.com/prusinakb-ux/card-management-service.git
cd card-management-service
```

## Build and run

### Build the app

```bash
mvn clean package
```

### Run the application

```bash
mvn spring-boot:run
```

The service will start on http://localhost:8080.

## Docker

You can run the service with PostgreSQL using Docker Compose:

```bash
docker-compose up --build
```

This will start:
Card management service
PostgreSQL database
Kafka

### Environment Variables

Application :

```yaml
SPRING_DATASOURCE_URL: jdbc:postgresql://db:5432/CLIENTS_DB
SPRING_DATASOURCE_USERNAME: postgres
SPRING_DATASOURCE_PASSWORD: postgres
```

Database:

```yaml
POSTGRES_USER: postgres
POSTGRES_PASSWORD: postgres
POSTGRES_DB: CLIENTS_DB
```

Kafka:

```yaml
spring.kafka.consumer.bootstrap-servers=localhost:29092
spring.kafka.producer.bootstrap-servers=localhost:29092
spring.kafka.consumer.group-id=${spring.application.name}
kafka.consumer.topic.card-status-update=card-status-update
```

External Card API:

```yaml
card.api.url=https://api.something.com/v1
```

## API Documentation

The service exposes an OpenAPI (Swagger) specification.

### Swagger UI

Access the Swagger UI:
http://localhost:8080/swagger-ui/index.html

You can use it to:
Explore endpoints
View request/response schemas
Test API calls from the browser
OpenAPI JSON
The raw OpenAPI specification is available at:
http://localhost:8080/v3/api-docs

## Testing

The project includes unit and integration tests covering key functionality:

- ClientService – tests client creation, retrieval, deletion, and card status updates, including edge cases for
  non-existing clients and invalid status transitions.
- ClientResourceService – verifies database operations, handling of duplicate OIBs, and proper exception handling.
- ClientMapper – ensures correct mapping between ClientRequest, Client entity, and ClientResponse.
- Kafka Integration – confirms that card status messages from Kafka are properly processed and delegated to the service.
- OIB Validator – checks the validity of OIB format and checksum.
- Internationalization – verifies that messages are correctly translated based on the locale (Croatian / English).

### Run Tests

```bash
mvn test
```

## Configuration

```properties
spring.application.name=card-management-service
# PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/CLIENTS_DB
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
#--FLWAY-----
spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration
spring.flyway.baseline-on-migrate=true
#--KAFKA-----
spring.kafka.consumer.bootstrap-servers=localhost:29092
spring.kafka.producer.bootstrap-servers=localhost:29092
spring.kafka.consumer.group-id=${spring.application.name}
kafka.consumer.topic.card-status-update=card-status-update
card.api.url=https://api.something.com/v1
```

## Technologies

- Java 21
- Spring Boot 3.4.6
- Spring Data JPA (Hibernate)
- PostgreSQL
- Kafka
- Querydsl
- Swagger / OpenAPI (springdoc)
- Maven
- Docker / Docker Compose
- JUnit 5, Mockito, Testcontainers
