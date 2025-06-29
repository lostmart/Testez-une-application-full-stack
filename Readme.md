# Savanza - Yoga Fullstack App

This full-stack application is a booking system developed for Savasana, a local yoga studio. The app enables users to reserve yoga sessions online. The project includes a front-end built with Angular and a back-end REST API developed with Spring Boot. It is designed with maintainability and quality in mind, featuring unit, integration, and end-to-end tests to ensure over 80% code coverage.

## Technos and versions

- Java 11
- NodeJS 16
- MySQL
- Angular CLI 14

## Backend

- Framework: Spring Boot 2.6.1
- Java Version: 1.8
- Architecture: RESTful API
- Database: MySQL (via mysql-connector-java)
- Authentication: JWT (via io.jsonwebtoken)
- Validation: Hibernate Validator (spring-boot-starter-validation)
- ORM: Spring Data JPA
- Object Mapping: MapStruct 1.5.1
- Environment Management: Dotenv (via dotenv-java)
- Code Generation: Lombok

### Testing:

- Spring Boot Test
- Spring Security Test
- JaCoCo for code coverage with enforced minimum thresholds

## Frontend

- Angular 14.2
- Angular CLI 14.2.1

### Testing:

- Unit testing: Jest (configured with jest-preset-angular)
- E2E testing: Cypress 10.4.0

### Build Tools & Coverage:

- Custom Webpack (via `@angular-builders/custom-webpack`)
- Code coverage: `nyc, @cypress/code-coverage`

## Running tests

## Frontend

For the front-end, once you have your environment up and running, you have three scripts for running tests. From your project root run:

```json
    "test": "jest",
    "test:watch": "jest --watch",
    "test:coverage": "jest --coverage"
```

or simple `npm run test -- [pathToYourFile]` like: `src/app/app.component.spec.ts` if you want to run one test file only
