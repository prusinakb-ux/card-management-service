# Card Management Service

A Spring Boot 3.4.6 application for managing card creation, built with Java 21, PostgreSQL, Hibernate, and Querydsl. The
service includes REST APIs, Swagger documentation, and Docker support for easy setup.

## Features

- Add new clients with attributes firstName, lastName, OIB, and cardStatus to the database.
- Fetch a client by OIB; returns client data if exists. If the client exists, automatically forward their information to
  the external RESTful API endpoint.
- Remove a client from the database by OIB.
- API documentation available via **Swagger UI**
- Docker Compose for (includes PostgreSQL)

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

This will start two services:
app: Spring Boot application on port 8080
db: PostgreSQL database on port 5432

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
- Querydsl
- Swagger / OpenAPI (springdoc)
- Maven
- Docker / Docker Compose
- JUnit 5, Mockito, Testcontainers
