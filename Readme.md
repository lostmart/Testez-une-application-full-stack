![CI](https://github.com/lostmart/Testez-une-application-full-stack/actions/workflows/ci.yml/badge.svg)

# Savanza - Yoga Fullstack App

This full-stack application is a booking system developed for Savasana, a local yoga studio. The app enables users to register and reserve yoga sessions online. It is built with a modern front-end using Angular and a RESTful back-end using Spring Boot. The codebase emphasizes modular design, maintainability, and automated testing with code coverage exceeding 80%.

## Technos and versions

- Java 11
- NodeJS 16
- MySQL
- Angular CLI 14.2.1

## Backend

- Framework: Spring Boot 2.6.1
- Architecture: RESTful API
- Database: MySQL (via mysql-connector-java)
- Authentication: JWT (via io.jsonwebtoken)
- Validation: Hibernate Validator (spring-boot-starter-validation)
- ORM: Spring Data JPA
- Object Mapping: MapStruct 1.5.1
- Environment Management: Dotenv (via dotenv-java)
- Code Generation: Lombok

### Backend Testing:

- **_Unit & Integration:_** Spring Boot Test
- **_Security Tests:_** Spring Security Test
- **_Coverage:_** JaCoCo (minimum thresholds enforced)

## Frontend

- Angular 14.2
- Angular CLI 14.2.1
- Forms: Reactive Forms
- Material UI: Angular Material

### Frontend Testing:

- Unit Test Coverage: Jest + jest --coverage
- E2E Coverage: nyc + @cypress/code-coverage

### Build Tools & Coverage:

- Custom Webpack (via `@angular-builders/custom-webpack`)
- Code coverage: `nyc, @cypress/code-coverage`

## Running tests

### Frontend Unit Tests

In the front/ directory, run:

```bash
    npm install
    npm run test        # Run all tests
    npm run test:watch  # Run in watch mode
    npm run test:coverage  # Generate coverage report
```

You can also run a specific test file:

```bash
npm run test -- src/app/app.component.spec.ts
```

### Cypress E2E Tests

E2E tests use Cypress to simulate full user flows (e.g. login, session creation, etc.).

Run in interactive mode:

```bash
npm run cypress:open
```

Run headlessly (CI-compatible):

```bash
npm run cypress:run
```

Cypress tests are located in front/cypress/e2e/. The base URL is http://localhost:4200 and must be running before executing tests.

## Backend Tests

To generate a code coverage report using JaCoCo:

```bash
./mvnw verify
```

Coverage output will be found in:

```bash
back/target/site/jacoco/index.html
```

## Setup Notes

1. Frontend

```bash
cd front
npm install
npm start
```

2. Backend

- Requires Java 11+, Maven, and a MySQL instance running
- Configure your `.env` or application properties with DB credentials and JWT secret

3. Cypress:

- Ensure the Angular app is running on port 4200
- Cypress is configured via `cypress.config.ts`
- Type definitions handled through `cypress/tsconfig.json`
