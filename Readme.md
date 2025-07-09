# Savasana - Yoga Fullstack App

This full-stack application is a booking system developed for **Savasana**, a local yoga studio. The app enables users to register and reserve yoga sessions online. It is built with a modern front-end using Angular and a RESTful back-end using Spring Boot. The codebase emphasizes modular design, maintainability, and automated testing with code coverage exceeding 80%.

---

## ⚙️ Technos and Versions

- Java 17 (Temurin 17.0.15+6)
- Node.js 16
- MySQL
- Angular CLI 14.2.1
- Maven 3.9.10

---

## 🔧 Backend

- **Framework:** Spring Boot 2.6.1
- **Architecture:** RESTful API
- **Database:** MySQL (via `mysql-connector-java`)
- **Authentication:** JWT (`io.jsonwebtoken`)
- **Validation:** Hibernate Validator (`spring-boot-starter-validation`)
- **ORM:** Spring Data JPA
- **Object Mapping:** MapStruct 1.5.1
- **Environment Management:** dotenv-java
- **Code Generation:** Lombok

### 🔬 Backend Testing

- Unit & Integration: Spring Boot Test, JUnit 5, Mockito
- Security: Spring Security Test
- Code Coverage: JaCoCo (enforced thresholds)

### 🔍 Commands to Run Tests and View Coverage

`cd back`
`./mvnw clean verify` or `mvn test`

This runs all unit and integration tests and generates a coverage report.
To open the JaCoCo HTML report:
`open back/target/site/jacoco/index.html   # or manually open in browser`

### IMPORTANT ❗

If you need a clean test tun:

`mvn clean verify`

`clean`

- Deletes the `target/` directory (the previous build output).
- Ensures that you're starting with a clean slate.

`verify`

- Runs the full build lifecycle up to the `verify` phase
- This includes:
  - `compile:` Compile the Java source code.
  - `test:` Run unit tests.
  - `package:` Package the code into a JAR/WAR (this includes code coverage checks from JaCoCo)

---

## 🎨 Frontend

- Angular 14.2
- Angular CLI 14.2.1
- Forms: Reactive Forms
- UI: Angular Material

### ✅ Frontend Testing

- **Unit Testing:** Jest (`jest-preset-angular`)
- **E2E Testing:** Cypress 10.4.0
- **Coverage:**
  - Unit: `jest --coverage`
  - E2E: `nyc` + `@cypress/code-coverage`

### 🔧 Build Tools

- Custom Webpack: `@angular-builders/custom-webpack`

---

## ✅ Running Tests

### Frontend Unit Tests

In the `front/` directory:

```bash
npm install
npm run test            # Run all Jest tests
npm run test:watch      # Run tests in watch mode
npm run test:coverage   # Generate unit test coverage report
```

### Run a specific test file:

```bash
npm run test -- src/app/app.component.spec.ts
```

## Cypress E2E Tests

E2E tests simulate real user behavior (e.g. login, session creation).

Run interactively:

```bash
npm run cypress:open
```

Run headlessly (e.g. in CI):

```bash
npm run cypress:run
```

Cypress tests are located in `front/cypress/e2e/`
Ensure the Angular app is running on `http://localhost:4200`

## E2E Test Coverage Report

Make sure the full application is running ❗ Front and back !
Front: `localhost:4200`, back : `localhost:8080`
To generate and view end-to-end coverage:

### - Run the instrumented test build:

```bash
npm run e2e:ci
npm run cypress:run
```

### - Generate coverage report:

```bash
npm run e2e:coverage
```

### - View the report in your browser:

```bash
npm run e2e:coverage:open
```

Then visit: `http://localhost:4300`

## ☕ Backend Tests

From the back/ directory:

`./mvnw verify`

View the JaCoCo HTML report:

`back/target/site/jacoco/index.html`

### 🧪 UserController Unit Test Example

The UserController includes unit tests for the `GET /api/user/{id}` endpoint. These tests cover:

- ✅ Valid user ID returns user data
- ❌ Non-existent user returns 404 Not Found
- 🚫 Invalid ID format returns 400 Bad Request

Tests are written with JUnit 5 and Mockito, isolated from the Spring context for fast execution.

Run them with:

```bash
./mvnw test -Dtest=UserControllerTest
```

Test class location:

```bash
back/src/test/java/com/openclassrooms/starterjwt/controllers/UserControllerTest.java
```

## 🧩 Setup Notes

### 1. Frontend

```bash
cd front
npm install
npm start
```

### 2. Backend

- Requires Java 11+, Maven, and a running MySQL instance
- Configure `.env` or `application.properties` with:
  - MySQL credentials
  - JWT secret

### 3. Cypress

- App must run on `http://localhost:4200`
- Cypress config: `front/cypress.config.ts`
- TypeScript support: `front/cypress/tsconfig.json`

## 🧪 Testing Strategy

### ✅ Unit Tests

- Services, mappers, and security logic are tested with JUnit and Mockito.
- Critical logic like authentication, user loading, and session creation is covered.
- DTOs are excluded from unit tests as per project guidelines.

### ✅ End-to-End (E2E) Tests

- E2E tests are implemented using Cypress.
- They simulate real user scenarios like login and protected route access.

### ✅ Coverage Summary

- 80%+ coverage on all backend packages except DTOs.
- JaCoCo is used for instrumentation.
- See `/target/site/jacoco/index.html` for full report.

### 🔍 Testing Alignment

- Tests reflect the documented test plan, ensuring that:
  - Authentication logic is robust
  - Services behave consistently
  - Input/output mapping is verified via mappers
